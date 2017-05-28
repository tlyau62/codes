package com.example.demo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * bean vs pojo, init beans vs init proj, method afterPropertiesSet()
 *
 * ref:
 * - http://sexycoding.iteye.com/blog/1046993
 * - https://stackoverflow.com/questions/14723984/how-can-i-understand-whether-a-bean-exists-in-runtime
 */

@SpringBootApplication
public class DemoApplication {

    static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Data
    public static class TestBean implements InitializingBean {

        private String myName;

        // called 1st if it's a bean
        public TestBean(String myName) {
            log.info("TestBean: constructor");
            this.myName = myName;
        }

        // call 2nd if it's a bean; no call if it's a POJO
        // can't be called via TestBean type
        @PostConstruct
        public void postConstruct() {
            log.info("TestBean: postConstructor");
        }

        // call 3rd if it's a bean; no call if it's a POJO
        // can be called via TestBean type (pojo can manually init its properties with this method)
        @Override
        public void afterPropertiesSet() throws Exception {
            log.info("TestBean: afterPropertiesSet");
        }
    }

//    TestBean is created as a bean (all 3 initialized method is called)
//    - a bean can be injected everywhere (without bean, a singleton class is needed to hold the object)
//	  @Bean
//    public TestBean testBean() {
//	    return new TestBean("name: TestBean"); //
//    }

    @Bean
    public CommandLineRunner lineRunner(ApplicationContext appContext) {
        return strings -> {
            // TestBean can be created as a POJO
            TestBean testBean = new TestBean("my bean"); // only constructor is called
            testBean.afterPropertiesSet(); // initialize fields

            System.out.println(appContext.getBean(TestBean.class)); // bean not found
        };
    }
}
