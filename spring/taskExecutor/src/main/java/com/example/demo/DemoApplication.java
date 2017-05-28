package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/* ref:
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html
 * https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/concurrent/ThreadPoolTaskExecutor.html
 */

@SpringBootApplication
public class DemoApplication {

    static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("taskExe-");
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setQueueCapacity(1);
//
//        taskExecutor.setAllowCoreThreadTimeOut();
//        taskExecutor.setKeepAliveSeconds();
//        taskExecutor.setTaskDecorator();
        return taskExecutor;
    }

    @Bean
    public CommandLineRunner runner(ThreadPoolTaskExecutor taskExecutor) {
        log.info("current thread: {}", Thread.currentThread());

        return args -> {
            for (int i = 0; i < 5; i++) {
                taskExecutor.execute(task(i));
            }
            /* err:
             * threads in pool: 0, 1 => core pool; 3 => queue(full); 4, 5 => max pool; 6 => throws TaskRejectedException
             *
             * note:
             * - TaskReject -> full pool + full queue
             * - if current thread no > corePoolSize, < maximumPoolSize, a new thread will be created only if the queue is full
             * - threads in (core + max) pool are active (running)
             */
        };
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Runnable task(int id) {
        return () -> {
            log.info("task {}: {}", id, Thread.currentThread());
            sleep(5);
            log.info("task {}: finish", id);
        };
    }
}
