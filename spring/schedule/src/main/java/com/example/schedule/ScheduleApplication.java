package com.example.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ScheduleApplication {

	private static final Logger log = LoggerFactory.getLogger(ScheduleApplication.class);

	private static long counter = 0;

	@Scheduled(fixedRate = 1000)
	public void task() {
		log.info("counter: {}", counter++);
	}

	public static void main(String[] args) {
		SpringApplication.run(ScheduleApplication.class, args);
	}
}
