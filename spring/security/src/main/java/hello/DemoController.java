package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by tlyau on 6/3/17.
 * - https://spring.io/blog/2013/07/04/spring-security-java-config-preview-method-security/
 * - https://stackoverflow.com/questions/3785706/whats-the-difference-between-secured-and-preauthorize-in-spring-security-3
 */

@RestController
public class DemoController {

    static final Logger log = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/string")
    @PreAuthorize("hasRole('ADMIN')") // require @EnableGlobalMethodSecurity(prePostEnabled = true)
    public String getString(Principal principal) {
        log.info("principle: {}", principal);
        return "a string";
    }

    @GetMapping("/string2")
    @Secured("ADMIN") // require @EnableGlobalMethodSecurity(securedEnabled = true)
    public String getString2(Principal principal) {
        log.info("principle: {}", principal);
        return "a string2";
    }

}
