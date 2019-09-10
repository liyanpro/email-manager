package cloud.liyan.emailmanager.service.Impl;
import cloud.liyan.emailmanager.dao.model.EmailTask;
import cloud.liyan.emailmanager.dao.model.EmailTaskExample;
import cloud.liyan.emailmanager.dao.model.MailTaskForWeb;
import cloud.liyan.emailmanager.dao.persistence.EmailTaskMapper;
import cloud.liyan.emailmanager.service.MailService;
import cloud.liyan.emailmanager.util.InitUtil;
import cloud.liyan.emailmanager.util.LoggerUtil;
import cloud.liyan.emailmanager.util.ReadExcelUtil;
import cloud.liyan.emailmanager.util.StringUtil;
import cloud.liyan.emailmanager.util.redis.RedisClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author liyan
 * @date 2019-09-07 14:36
 */
@Service
public class MailServiceImpl extends LoggerUtil implements MailService {

    @Autowired
    private EmailTaskMapper mailTaskMapper;

    @Override
    public List<MailTaskForWeb> getEmailTask() {
        List<MailTaskForWeb> mailTaskForWebList = new ArrayList<MailTaskForWeb>();
        EmailTaskExample mailTaskExample = new EmailTaskExample();
        // 按状态升序排 0：未开始，1：进行中，2：暂停，3：结束
        mailTaskExample.setOrderByClause(" status asc, createtime desc");
        List<EmailTask> mailTaskList = mailTaskMapper.selectByExample(mailTaskExample);
        if (mailTaskList != null) {
            Jedis jedis = RedisClient.getInstance().getJedis();
            if (StringUtils.isBlank(InitUtil.HOST)) {
                InitUtil.mailInit();
            }

            Iterator<EmailTask> it = mailTaskList.iterator();
            while (it.hasNext()) {
                MailTaskForWeb mailTaskForWeb = new MailTaskForWeb();
                EmailTask mail = it.next();
                String domain = jedis.hget(mail.getId(), InitUtil.TASK_DOMAIN);
                // 未发送邮件包括队列中剩余的和发送异常的
                Integer nCount = jedis.llen(domain + mail.getId()).intValue()
                        + jedis.llen(domain + InitUtil.TASK_SUSPEND + mail.getId()).intValue();
                // 已发送邮件
                Integer yCount = mail.getCount() - nCount;
                mailTaskForWeb.setnCount(nCount);
                mailTaskForWeb.setyCount(yCount);
                mailTaskForWeb.setMailTask(mail);
                mailTaskForWebList.add(mailTaskForWeb);
            }
            mailTaskList = null;
        }
        return mailTaskForWebList;
    }

    @Override
    public int createUpdateEmailTask(EmailTask mailTask, String content) {
        Jedis jedis = RedisClient.getInstance().getJedis();
        try {
            // 将content、domain和title加载到缓存中
            jedis.hset(mailTask.getId(), InitUtil.TASK_ID, mailTask.getId());
            jedis.hset(mailTask.getId(), InitUtil.TASK_CONTENT, content);
            jedis.hset(mailTask.getId(), InitUtil.TASK_TITLE, mailTask.getTitle());
            jedis.hset(mailTask.getId(), InitUtil.TASK_DOMAIN, mailTask.getDomain());
            // 检查ID是否有重复
            EmailTask task = mailTaskMapper.selectByPrimaryKey(mailTask.getId());
            if (task == null) {
                InitUtil.mailInit();
                mailTaskMapper.insert(mailTask);
                jedis.hset(mailTask.getId(), InitUtil.TASK_STATUS, "3");
                return 1;
            } else {
                String status = jedis.hget(mailTask.getId(), InitUtil.TASK_STATUS);
                jedis.hset(mailTask.getId(), InitUtil.TASK_STATUS, status);
                mailTaskMapper.updateByPrimaryKey(mailTask);
                return 2;
            }

        } catch (Exception e) {
            this.logger.error("保存/更新异常", e);
        } finally {
            jedis.close();
        }
        return 0;
    }

    @Override
    public int testEmailTask(String id, String address) {
        EmailTaskExample mailTaskExample = new EmailTaskExample();
        EmailTaskExample.Criteria mc = mailTaskExample.createCriteria();
        mc.andIdEqualTo(id);
        Jedis jedis = RedisClient.getInstance().getJedis();
        try {
            List<EmailTask> mailTask = mailTaskMapper.selectByExample(mailTaskExample);
            if (mailTask == null || mailTask.size() == 0) {
                return 0;
            } else {
                if (StringUtils.isBlank(InitUtil.HOST)) {
                    InitUtil.mailInit();
                }
                // 从redis中取出要发送的内容
                String content = jedis.hget(mailTask.get(0).getId(), InitUtil.TASK_CONTENT);
                String domain = jedis.hget(mailTask.get(0).getId(), InitUtil.TASK_DOMAIN);
                content = URLEncoder.encode(content, "UTF-8");
                if (address.indexOf(";") > -1) {
                    String[] addresses = address.split(";");
                    for (String add : addresses) {
                        sendTestMail(mailTask.get(0), add, content, domain);
                    }
                } else {
                    sendTestMail(mailTask.get(0), address, content, domain);
                }
                return 1;
            }
        } catch (Exception e) {
            this.logger.error("邮件发送异常，ID=" + id, e);
        }finally{
            jedis.close();
        }
        return -1;
    }

    @Override
    public int uploadEmailAdress(String id, byte[] address, InputStream streamFile, boolean isXlsx) {
        EmailTaskExample mailTaskExample = new EmailTaskExample();
        EmailTaskExample.Criteria mc = mailTaskExample.createCriteria();
        mc.andIdEqualTo(id);
        try {
            List<EmailTask> mailTask = mailTaskMapper.selectByExample(mailTaskExample);
            if (mailTask == null || mailTask.size() == 0) {
                return 0;
            } else {
                if (address != null) {
                    initMailTask(id, address);// 初始化txt文件的邮件任务
                } else if (streamFile != null) {
                    initExcelMailTask(id, streamFile, isXlsx);// 初始化Excel文件的邮件任务
                }
                return 1;
            }
        } catch (Exception e) {
            this.logger.error("上传文件失败", e);
        }
        return -1;
    }

    @SuppressWarnings("finally")
    @Override
    public int startSendEmail(String id) {
        int result = 0;
        Jedis jedis = RedisClient.getInstance().getJedis();
        // 更新任务状态
        EmailTaskExample mailTaskExample = new EmailTaskExample();
        EmailTaskExample.Criteria mc = mailTaskExample.createCriteria();
        EmailTask emailTask = new EmailTask();
        mc.andIdEqualTo(id);
        emailTask.setStatus(InitUtil.STATUS_JXZ);// 任务状态设置为：1-执行中
        try {
            // 将任务添加到队列
            jedis.hset(id, InitUtil.TASK_ID, id);
            jedis.rpush(InitUtil.TASK_KEY, id);// 将任务添加到队列，多线程执行
            mailTaskMapper.updateByExampleSelective(emailTask, mailTaskExample);
        } catch (Exception e) {
            this.logger.error("邮件发送任务执行异常，邮件ID=" + id, e);
            result = -1;
        } finally {
            jedis.close();
            return result;
        }
    }

    @SuppressWarnings("finally")
    @Override
    public int suspendSendEmail(String id) {
        int result = 0;
        Jedis jedis = RedisClient.getInstance().getJedis();
        try {
            // 从任务队列中删除当前任务
            jedis.hdel(id, InitUtil.TASK_ID);
            // 更新任务状态
            updateMailStatus(id, InitUtil.STATUS_ZT);
        } catch (Exception e) {
            this.logger.error("暂停任务出错", e);
            result = -1;
        } finally {
            jedis.close();
            return result;
        }
    }

    @SuppressWarnings("finally")
    @Override
    public int restartSendEmail(String id) {
        int result = 0;
        Jedis jedis = RedisClient.getInstance().getJedis();
        try {
            // 更新任务状态
            updateMailStatus(id, InitUtil.STATUS_JXZ);
            jedis.hset(id, InitUtil.TASK_STATUS, InitUtil.STR_STATUS_JS);
            // 将任务添加到队列
            jedis.hset(id, InitUtil.TASK_ID, id);
            jedis.rpush(InitUtil.TASK_KEY, id);// 将任务添加到队列，多线程执行
        } catch (Exception e) {
            this.logger.error("重新开始出现异常，邮件id=" + id, e);
            result = -1;
        } finally {
            jedis.close();
            return result;
        }
    }

    @SuppressWarnings("finally")
    @Override
    public int stopSendEmail(String id) {
        int result = 0;
        Jedis jedis = RedisClient.getInstance().getJedis();
        try {
            String domain = jedis.hget(id, InitUtil.TASK_DOMAIN);
            Integer count = jedis.llen(domain + id).intValue()
                    + jedis.llen(domain + InitUtil.TASK_SUSPEND + id).intValue();
            // 从任务队列中删除当前任务
            jedis.hdel(id, InitUtil.TASK_ID);
            jedis.hdel(id, InitUtil.TASK_CONTENT);
            jedis.hdel(id, InitUtil.TASK_TITLE);
            jedis.del(domain + InitUtil.TASK_SUSPEND + id);
            jedis.del(domain + id);
            EmailTask mailTask = mailTaskMapper.selectByPrimaryKey(id);
            updateMailStatus(id, InitUtil.STATUS_JS);
            updateMailCount(id, mailTask.getCount() - count);
        } catch (Exception e) {
            this.logger.error("暂停任务出错", e);
            result = -1;
        } finally {
            jedis.close();
            return result;
        }
    }

    public void sendTestMail(EmailTask emailTask, String address, String content, String domain) {
        OkHttpClient client = new OkHttpClient();
        StringBuffer mailsUrl = new StringBuffer();
        mailsUrl.append("http://").append(InitUtil.HOST).append("/send/").append(domain).append("/");
        mailsUrl.append(emailTask.getId()).append("/").append(address).append("/").append(emailTask.getTitle());
        mailsUrl.append("/").append(content);
        try {
            Request request = new Request.Builder().url(mailsUrl.toString()).build();
            Response response = client.newCall(request).execute();
            if (response.code() != HttpStatus.OK.value()) {
                this.logger.error("邮件发送失败，地址：" + address);
            } else {
                this.logger.info(address + "的邮件发送成功");
            }
            response.close();
        } catch (Exception e) {
            this.logger.error("邮件发送出现异常，地址：" + address, e);
        }
        mailsUrl = null;
        client = null;
    }

    public void initMailTask(String id, byte[] address) {
        String str = null;
        int count = 0;
        BufferedReader buffer = null;
        Jedis jedis = RedisClient.getInstance().getJedis();
        try {
            String domain = jedis.hget(id, InitUtil.TASK_DOMAIN);
            // 初始化邮件地址到redis
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(address);
            buffer = new BufferedReader(new InputStreamReader(byteArrayInputStream));
            while ((str = buffer.readLine()) != null) {
                if (StringUtil.isEmail(str) && StringUtils.isNotBlank(str)) {
                    jedis.rpush(domain + id, str.trim());
                    count++;
                    if (count > 0 && count % 100 == 0) {
                        this.logger.info("已加载邮件：" + count);
                    }
                }
            }
            this.logger.info("总加载邮件数量：" + count);
            // 将邮件数量更新到数据库
            updateMailCount(id, count);
        } catch (IOException e) {
            this.logger.error("初始化邮件地址出错,邮件ID=" + id, e);
        } finally {
            try {
                buffer.close();
                jedis.close();
            } catch (IOException e) {
                this.logger.error("关闭缓冲区异常", e);
            }
        }
    }

    public void initExcelMailTask(String id, InputStream fileStream, boolean isXlsx) {
        int count = 0;
        Jedis jedis = RedisClient.getInstance().getJedis();
        try {
            String domain = jedis.hget(id, InitUtil.TASK_DOMAIN);
            List<Map<String, String>> lists = ReadExcelUtil.analysisExcel(fileStream, isXlsx);
            for (Map<String, String> map : lists) {
                String str = "";
                boolean add = true;
                for (int i = 0; i < map.size(); i++) {
                    if (map.containsKey(String.valueOf(i))) {
                        // 第一列为email地址时才允许添加
                        if (i == 0 && !StringUtil.isEmail(map.get(String.valueOf(i)))) {
                            add = false;
                        }
                        if (add) {
                            str = str + map.get(String.valueOf(i)) + ",";
                        }
                    }
                }
                if (StringUtils.isNotBlank(str)) {
                    str = str.substring(0, str.lastIndexOf(",") - 1).trim();
                    this.logger.info("add email info:" + str);
                    jedis.rpush(domain + id, str);
                    count++;
                    if (count > 0 && count % 100 == 0) {
                        this.logger.info("已加载可替换内容邮件数量：" + count);
                    }
                }
            }
            this.logger.info("总加载邮件数量：" + count);
            // 将邮件数量更新到数据库
            updateMailCount(id, count);

        } catch (Exception e) {
            this.logger.error("初始化邮件地址出错,邮件ID=" + id, e);
        } finally {
            jedis.close();
        }
    }



    @Override
    public void updateMailCount(String id, int count) {
        // 将邮件数量更新到数据库
        EmailTaskExample mailTaskExample = new EmailTaskExample();
        EmailTaskExample.Criteria mc = mailTaskExample.createCriteria();
        mc.andIdEqualTo(id);
        EmailTask emailTask = new EmailTask();
        emailTask.setCount(count);
        mailTaskMapper.updateByExampleSelective(emailTask, mailTaskExample);
    }

    @Override
    public void updateMailStatus(String id, int status) {
        // 更新任务状态
        EmailTaskExample mailTaskExample = new EmailTaskExample();
        EmailTaskExample.Criteria mc = mailTaskExample.createCriteria();
        EmailTask emailTask = new EmailTask();
        mc.andIdEqualTo(id);
        emailTask.setStatus(status);// 更新任务状态
        mailTaskMapper.updateByExampleSelective(emailTask, mailTaskExample);
    }

    @SuppressWarnings("finally")
    @Override
    public int deleteEmail(String id) {
        int result = 0;
        Jedis jedis = RedisClient.getInstance().getJedis();
        try {
            String domain = jedis.hget(id, InitUtil.TASK_DOMAIN);
            // 从任务队列中删除当前任务
            jedis.hdel(id, InitUtil.TASK_ID);
            jedis.hdel(id, InitUtil.TASK_CONTENT);
            jedis.hdel(id, InitUtil.TASK_TITLE);
            jedis.del(domain + InitUtil.TASK_SUSPEND + id);
            jedis.del(domain + id);
            logger.info("邮件ID=" + id + "，标题、内容删除成功！");
            mailTaskMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            this.logger.error("删除任务出错", e);
            result = -1;
        } finally {
            jedis.close();
            return result;
        }
    }

    @Override
    public String replaceContent(String content, String variable) {
        String con=content;
        String[] values = variable.split(",");
        for (int i = 1; i < values.length; i++) {
            con = con.replace("${name" + i + "}", values[i]);
        }
        return con;
    }
}
