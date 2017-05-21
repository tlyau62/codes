package com.example.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.*;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tl on 5/20/17.
 * some utils classes provided by spring - https://www.youtube.com/watch?v=Zo9e3i8HxX4&t=1381s
 * - Assert
 * - BeanUtils
 * - ClassUtils
 * - SystemPropertyUtils
 * - FileCopyUtils
 * - ReflectionUtils
 */

@SpringBootApplication
public class Main {

    private static final Log log = LogFactory.getLog(Main.class);

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DemoClass {

        private List<Map<String, Object>> list = new ArrayList<>();

    }

    @Bean // produces a bean to be managed by the Spring container, a bean can be injected
    DemoClass demoClass() {
        return new DemoClass();
    }

    @Bean
    CommandLineRunner demo(DemoClass demo) { // inject a demo bean
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

//                demo.setList(null); throw IllegalStateException
                Assert.notNull(demo.getList(), "the list can't be null");

                beans(demo);
                classUtils();
                systemPropertyUtils();
                fileCopyUtils();
                reflection();
            }
        };
    }

    private void reflection() {
        ReflectionUtils.doWithFields(DemoClass.class, field -> {
            log.info(field.toString());
        });
        Field list = ReflectionUtils.findField(DemoClass.class, "list");
        log.info(list.toString());
    }


    private void fileCopyUtils() throws Exception {
        File file = new ClassPathResource("text.txt").getFile();
        try (FileReader reader = new FileReader(file)) {
            log.info("contents of file" + FileCopyUtils.copyToString(reader));
        }

//        http://stackoverflow.com/questions/12165381/file-constructors-explanation
    }

    private void systemPropertyUtils() {
        String resolveText = SystemPropertyUtils.resolvePlaceholders("home directory: ${user.home}");
        log.info("resolved text: " + resolveText);
    }

    private void classUtils() {
        Constructor<DemoClass> demoClassConstructor = ClassUtils.getConstructorIfAvailable(DemoClass.class);
        log.info("demoClassConstructor: " + demoClassConstructor);
        try {
            DemoClass demoClass = demoClassConstructor.newInstance();
            log.info("newInstance'd demoClass: " + demoClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void beans(DemoClass demo) {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(demo.getClass());
        for (PropertyDescriptor pd : propertyDescriptors) {
            log.info("pd: " + pd.getName());
            log.info("pd.readMethod: " + pd.getReadMethod().getName());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
