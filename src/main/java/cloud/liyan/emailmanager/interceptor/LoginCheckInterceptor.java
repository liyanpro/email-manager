package cloud.liyan.emailmanager.interceptor;

import cloud.liyan.emailmanager.annotation.LoginCheck;
import cloud.liyan.emailmanager.util.ConstantUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description
 * @author liyan
 * @date 2019-09-11 14:46
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("请求的接口：{}",request.getRequestURI());
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        LoginCheck loginCheck=handlerMethod.getMethodAnnotation(LoginCheck.class);
        if(loginCheck==null){
            Class clazz=handlerMethod.getMethod().getDeclaringClass();
            loginCheck=(LoginCheck)clazz.getAnnotation(LoginCheck.class);
        }
        if(loginCheck!=null&&loginCheck.needLogin()&&!SecurityUtils.getSubject().isAuthenticated()){
            response.sendRedirect(ConstantUtil.LOGIN_URI);
        }
        return true;
    }
}
