package cloud.liyan.emailmanager.service.Impl;
import cloud.liyan.emailmanager.dao.model.EmailTask;
import cloud.liyan.emailmanager.dao.model.MailTaskForWeb;
import cloud.liyan.emailmanager.dao.persistence.EmailTaskMapper;
import cloud.liyan.emailmanager.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class MailServiceImpl implements MailService {

    @Autowired
    private EmailTaskMapper mailTaskMapper;

    @Override
    public List<MailTaskForWeb> getEmailTask() {
        return null;
    }

    @Override
    public int createUpdateEmailTask(EmailTask mailTask, String content) {

        return 0;
    }

    @Override
    public int testEmailTask(String id, String address) {

        return -1;
    }

    @Override
    public int uploadEmailAdress(String id, byte[] address, InputStream streamFile, boolean isXlsx) {

        return -1;
    }

    @SuppressWarnings("finally")
    @Override
    public int startSendEmail(String id) {
       return 0;
    }

    @SuppressWarnings("finally")
    @Override
    public int suspendSendEmail(String id) {
       return 0;
    }

    @SuppressWarnings("finally")
    @Override
    public int restartSendEmail(String id) {
        return 0;
    }

    @SuppressWarnings("finally")
    @Override
    public int stopSendEmail(String id) {
        return 0;
    }

    public void sendTestMail(EmailTask emailTask, String address, String content, String domain) {

    }

    public void initMailTask(String id, byte[] address) {

    }

    public void initExcelMailTask(String id, InputStream fileStream, boolean isXlsx) {

    }



    @Override
    public void updateMailCount(String id, int count) {

    }

    @Override
    public void updateMailStatus(String id, int status) {

    }

    @SuppressWarnings("finally")
    @Override
    public int deleteEmail(String id) {
       return 0;
    }

    @Override
    public String replaceContent(String content, String variable) {
       return null;
    }
}
