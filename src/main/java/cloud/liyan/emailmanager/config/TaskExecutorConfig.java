package cloud.liyan.emailmanager.config;

import cloud.liyan.emailmanager.util.InitUtil;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author liyan
 * @description
 * @date 2019-09-09 下午6:22
 */
@Configuration
@ComponentScan("cloud.liyan")
@EnableAsync
public class TaskExecutorConfig  implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(InitUtil.THREAD_NUM);
        taskExecutor.setMaxPoolSize(8);
        taskExecutor.setQueueCapacity(25);
        // 配置线程池中的线程的名称前缀
        taskExecutor.setThreadNamePrefix("liyan-async-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        // taskExecutor.setRejectedExecutionHandler(new
        // TaskExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
