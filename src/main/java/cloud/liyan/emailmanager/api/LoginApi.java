package cloud.liyan.emailmanager.api;/**
 * @description
 * @author liyan
 * @date 2019-09-10 18:37
 */

import cloud.liyan.emailmanager.annotation.LoginCheck;
import cloud.liyan.emailmanager.dao.model.UserInfo;
import cloud.liyan.emailmanager.dao.model.constant.HttpConstants;
import cloud.liyan.emailmanager.dao.model.constant.ServiceResult;
import cloud.liyan.emailmanager.util.ConstantUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description
 * @author liyan
 * @date 2019-09-10 18:37
 */
@RestController
@Api(value = "LoginApi", description = "登录接口")
@RequestMapping(value = "/api")
public class LoginApi {

    @LoginCheck(needLogin = false)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServiceResult login(@RequestBody JSONObject json, HttpServletRequest request, HttpServletResponse response) {
        ServiceResult serviceResult = null;
        try {
            if (json != null) {
                String username = json.getString("username");
                String password = json.getString("password");
                String remeber = json.getString("remeber");
                boolean rememberMe = true;
                if (StringUtils.isBlank(remeber)) {
                    rememberMe = false;
                }
                UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
                Subject currentSubject= SecurityUtils.getSubject();
                currentSubject.login(token);
                currentSubject.getSession().setAttribute("name", username);
                serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "登录成功");
            }
        } catch (Exception e) {
            serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, "账号或密码不正确");
        }
        return serviceResult;
    }
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ServiceResult logout(HttpServletResponse response) {
        ServiceResult serviceResult = null;
        try {
            // 退出
            SecurityUtils.getSubject().logout();
            response.sendRedirect(ConstantUtil.LOGIN_URI);
            serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "退出成功");
        } catch (Exception e) {
            serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, "退出异常");
        }
        return serviceResult;
    }

    @LoginCheck(needLogin = true)
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        String userName=String.valueOf(SecurityUtils.getSubject().getSession().getAttribute("name"));
        if (!"null".equals(userName)) {
            userInfo.setName(userName);
        } else {
            userInfo.setName(null);
        }
        return userInfo;
    }
}
