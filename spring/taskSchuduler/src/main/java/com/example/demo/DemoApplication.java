package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class DemoApplication {

    static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    static AtomicInteger integer = new AtomicInteger();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public CommandLineRunner runner(ThreadPoolTaskScheduler taskScheduler) {
        return strings -> taskScheduler.scheduleAtFixedRate(
                () -> log.info("{}", integer.getAndIncrement()), 1000);
    }

}
