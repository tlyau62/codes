package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

@SpringBootApplication
public class DemoApplication {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            // should use "spring data redis" instead of jedis
            Jedis jedis = new Jedis(Protocol.DEFAULT_HOST);
            jedis.set("foo", "bar");
            String value = jedis.get("foo");
            log.info(value);
        };
    }
}
