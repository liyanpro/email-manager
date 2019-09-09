package cloud.liyan.emailmanager.thread;

import cloud.liyan.emailmanager.service.MailService;
import cloud.liyan.emailmanager.util.InitUtil;
import cloud.liyan.emailmanager.util.LoggerUtil;
import cloud.liyan.emailmanager.util.StringUtil;
import cloud.liyan.emailmanager.util.redis.RedisClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import redis.clients.jedis.Jedis;

import java.net.URLEncoder;

/**
 * @author liyan
 * @description
 * @date 2019-09-09 下午6:25
 */
public class TaskExcutorService extends LoggerUtil{
    @Autowired
    private MailService mailService;
    @Async
    public void executorTask(){
        Jedis jedis = RedisClient.getJedis();
        while (true) {
            String taskKey = jedis.lpop(InitUtil.TASK_KEY);
            if (StringUtils.isNotBlank(taskKey)) {
                String id = jedis.hget(taskKey, InitUtil.TASK_ID);
                if (StringUtils.isNotBlank(id)) {
                    String content = jedis.hget(id, InitUtil.TASK_CONTENT);
                    String title = jedis.hget(id, InitUtil.TASK_TITLE);
                    String domain = jedis.hget(id, InitUtil.TASK_DOMAIN);
                    String key = domain + id;
                    String mailInfo = jedis.lpop(key);
                    String mail = mailInfo;
                    logger.info("取到的mail信息：" + mail);
                    if (StringUtils.isNotBlank(mail) && mailInfo.split(",").length > 1) {
                        mail = mailInfo.split(",")[0]; // 第一个为邮箱地址，其余为邮件内容替换变量
                        // 替换content
                        content = mailService.replaceContent(content, mailInfo);
                    }
                    StringBuffer mailsUrl = new StringBuffer();
                    try {
                        if (StringUtils.isNotBlank(mail)) {
                            jedis.rpush(InitUtil.TASK_KEY, taskKey);// 将任务添加到队列，多线程执行
                            if (StringUtil.isEmail(mail)) {
                               //todo 发送邮件操作

                            }
                            Thread.sleep(3000);
                        }
                        // 邮件任务结束更新状态
                        else {
                            mailService.updateMailStatus(id, InitUtil.STATUS_JS);
                            this.logger.info("线程" + Thread.currentThread().getName() + "的邮件ID为 " + id + " 的任务已结束");
                        }
                    } catch (Exception e) {
                        logger.error("邮件发送出错", e);
                    } finally {
                        mailsUrl = null;
                        content = null;
                    }
                }
            }
            else {
                try {
                    this.logger.info("没有邮件任务，线程休息2分钟");
                    Thread.sleep(1000 * 60 * 2);// 没有任务时线程休眠10分钟
                } catch (InterruptedException e) {
                    this.logger.error("线程出错", e);
                }
            }
        }
    }
}
