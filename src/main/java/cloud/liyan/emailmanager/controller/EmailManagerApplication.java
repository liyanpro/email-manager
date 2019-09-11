package cloud.liyan.emailmanager.controller;

import cloud.liyan.emailmanager.service.MailService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@ComponentScan({"cloud.liyan.emailmanager"})
@MapperScan("cloud.liyan.emailmanager.dao.persistence")
public class EmailManagerApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(EmailManagerApplication.class);


    @Override
    public void run(String... args) {
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EmailManagerApplication.class);
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 允许上传的文件最大值
        factory.setMaxFileSize("100MB"); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1000MB");
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        logger.debug("服务启动");
        SpringApplication.run(EmailManagerApplication.class, args);
    }

}
