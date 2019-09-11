package cloud.liyan.emailmanager.service;

import cloud.liyan.emailmanager.dao.model.EmailTask;
import cloud.liyan.emailmanager.dao.model.MailTaskForWeb;

import java.io.InputStream;
import java.util.List;

/**
 * @author liyan
 * @description
 * @date 2019-09-07 14:35
 */
public interface MailService {

    /**
     * 获取邮件任务
     *
     * @return
     */
    List<MailTaskForWeb> getEmailTask();

    /**
     * 创建邮件任务
     *
     * @param mailTask
     * @param content
     * @return
     */
    int createUpdateEmailTask(EmailTask mailTask, String content);

    /**
     * 发送测试邮件
     *
     * @param id
     * @param address
     * @return
     */
    int testEmailTask(String id, String address);

    /**
     * 上传邮件地址
     *
     * @param id
     * @param addresses
     * @return
     */
    int uploadEmailAdress(String id, byte[] addresses, InputStream streamFile, boolean isXlsx);

    /**
     * 开始发送邮件
     *
     * @param id
     * @return
     */
    int startSendEmail(String id);

    /**
     * 暂停发送邮件
     *
     * @param id
     * @return
     */
    int suspendSendEmail(String id);

    /**
     * 重新开始发送邮件
     *
     * @param id
     * @return
     */
    int restartSendEmail(String id);
    /**
     * 停止发送邮件
     *
     * @param id
     * @return
     */
    int stopSendEmail(String id);

    /**
     * 更新邮件数量
     *
     * @param id
     * @param count
     */
    void updateMailCount(String id, int count);

    /**
     * 更新任务状态
     *
     * @param id
     * @param status
     */
    void updateMailStatus(String id, int status);

    /**
     * 删除任务
     *
     * @param id
     * @return
     */
    int deleteEmail(String id);

    /**
     * 邮件内容变量替换,variable为已逗号隔开的字符串,第一个为邮箱地址
     *
     * @param content
     * @param variable
     * @return
     */
    String replaceContent(String content, String variable);
}
