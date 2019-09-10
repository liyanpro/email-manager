package cloud.liyan.emailmanager.service.Impl;/**
 * @description
 * @author liyan
 * @date 2019-09-10 15:54
 */

import cloud.liyan.emailmanager.dao.model.UserInfo;
import cloud.liyan.emailmanager.dao.model.UserInfoExample;
import cloud.liyan.emailmanager.dao.persistence.UserInfoMapper;
import cloud.liyan.emailmanager.service.LoginService;
import cloud.liyan.emailmanager.util.LoggerUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @description
 * @author liyan
 * @date 2019-09-10 15:54
 */
@Service
public class LoginServiceImpl extends LoggerUtil implements LoginService {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean login(JSONObject json) {
        String username = json.getString("username");
        String password = json.getString("password");
        if (password == null) {
            return false;
        }
        try {
            username = URLDecoder.decode(username, "UTF-8");
            password = URLDecoder.decode(password, "utf-8");
        } catch (UnsupportedEncodingException e) {
           logger.error("UnsupportedEncodingException error",e);
        }
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria uc = userInfoExample.createCriteria();
        uc.andNameEqualTo(username);
        List<UserInfo> user = userInfoMapper.selectByExample(userInfoExample);
        if (user == null || user.size() == 0) {
            return false;
        }
        else {
            if (password.equals(user.get(0).getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    @Override
    public UserInfo getUserInfo(String userName, String password) {
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria uc = userInfoExample.createCriteria();
        uc.andNameEqualTo(userName);
        uc.andPasswordEqualTo(password);
        List<UserInfo> userInfo = userInfoMapper.selectByExample(userInfoExample);
        if (userInfo != null && userInfo.size() > 0) {
            return userInfo.get(0);
        } else {
            return null;
        }
    }
    @Override
    public void updateLoginTime(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKey(userInfo);
    }
}
