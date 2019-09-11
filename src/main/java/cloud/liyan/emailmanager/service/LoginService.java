package cloud.liyan.emailmanager.service;

import cloud.liyan.emailmanager.dao.model.UserInfo;
import com.alibaba.fastjson.JSONObject;

/**
 * @author liyan
 * @description
 * @date 2019-09-10 15:53
 */
public interface LoginService {

    boolean login(JSONObject json);

    /**
     * 获取用户登录信息
     *
     * @param userName
     * @param password
     * @return
     */
    UserInfo getUserInfo(String userName, String password);

    /**
     * 更新用户登录时间
     *
     * @param userInfo
     * @return
     */
    void updateLoginTime(UserInfo userInfo);
}
