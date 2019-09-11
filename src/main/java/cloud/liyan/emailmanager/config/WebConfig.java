package cloud.liyan.emailmanager.config;

import cloud.liyan.emailmanager.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @description
 * @author liyan
 * @date 2019-09-11 18:20
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/api/**");
    }
}
