package com.example.demo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * bean vs pojo, init beans vs init proj, method afterPropertiesSet()
 * ways to create beans (@Configuration, lite mode, xml):
 * - https://stackoverflow.com/questions/40256702/spring-bean-can-still-work-without-configuration
 * - http://codippa.com/how-to-create-bean-in-spring/
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

    /**
     * Implements BeanDefinitionRegistryPostProcessor to ensure this Bean is
     * initialized before any other Beans.
     */
    @Data
    public static class LiteBean implements InitializingBean, DisposableBean, BeanDefinitionRegistryPostProcessor {

        private String myName;

        /**
         * Initializing Bean
         * @param myName
         */
        // called 1st if it's a bean
        public LiteBean(String myName) {
            log.info("LiteBean: constructor");
            this.myName = myName;
        }

        // call 2nd if it's a bean; no call if it's a POJO
        // can't be called via LiteBean type
        @PostConstruct
        public void postConstruct() {
            log.info("LiteBean: postConstructor");
        }

        // call 3rd if it's a bean; no call if it's a POJO
        // can be called via LiteBean type (pojo can manually init its properties with this method)
        @Override
        public void afterPropertiesSet() throws Exception {
            log.info("LiteBean: afterPropertiesSet");
        }

        /**
         * Disposable Bean
         * @throws Exception
         */
        // call at end of program
        @Override
        public void destroy() throws Exception {
            log.info("LiteBean: destroy");
        }

        /**
         * BeanDefinitionRegistryPostProcessor
         * @param beanDefinitionRegistry
         * @throws BeansException
         */
        // call 4th if it's a bean
        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
            log.info("LiteBean: postProcessBeanDefinitionRegistry");
            // modify bean definition (e.g. scope, bean class name, ...)
        }

        // call 5th if it's a bean
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
            log.info("LiteBean: postProcessBeanFactory");
        }
    }

    @Bean
    public LiteBean liteBean() {
	    return new LiteBean("name: LiteBean"); // return to the spring container
    }

    @Bean
    public CommandLineRunner lineRunner(ApplicationContext appContext, BeanFactory beanFactory) {
        return strings -> {
            LiteBean bean = (LiteBean) appContext.getBean("liteBean");
            log.info(bean.getMyName());

            bean = (LiteBean) beanFactory.getBean("liteBean");
            log.info(bean.getMyName());

            // LiteBean can be created as a POJO (not a bean)
            /*LiteBean testBean = new LiteBean("my bean"); // only constructor is called
            testBean.afterPropertiesSet(); // initialize fields*/
        };
    }
}
