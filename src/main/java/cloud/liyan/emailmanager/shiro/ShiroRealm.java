package cloud.liyan.emailmanager.shiro;

import cloud.liyan.emailmanager.dao.model.UserInfo;
import cloud.liyan.emailmanager.service.LoginService;
import cloud.liyan.emailmanager.util.DesEncrypt;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @author liyan
 * @date 2019-09-11 15:29
 */
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger logger= LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    private LoginService loginService;
    /**
     * 登录验证: Authentication 是用来验证用户身份
     *
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());
        // 密码进行加密处理 明文为 name+password
        String paw =  name+password;
        String pawDES = DesEncrypt.encryptBasedDes(paw);
        // 从数据库获取对应用户名密码的用户
        UserInfo userInfo = loginService.getUserInfo(name, pawDES);
        if (null == userInfo) {
             throw new AuthenticationException("帐号或密码错误");
        } else {
            // 更新登录时间 last login time
            userInfo.setLastLoginTime(new Date());
            loginService.updateLoginTime(userInfo);
        }
        logger.info("身份认证成功，登录用户：" + name);
        return new SimpleAuthenticationInfo(String.valueOf(userInfo.getUserId()), password, getName());
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
}
