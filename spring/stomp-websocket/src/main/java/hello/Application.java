package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by tlyau on 6/3/17.
 * - https://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html
 * - https://spring.io/guides/gs/messaging-stomp-websocket/
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
