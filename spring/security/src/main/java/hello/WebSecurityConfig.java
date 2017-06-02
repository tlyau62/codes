package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * ref:
 * - https://spring.io/guides/topicals/spring-security-architecture/
 * - https://stackoverflow.com/questions/2323377/spring-security-authenticationmanager-vs-authenticationprovider
 *
 * keys:
 * - authentication (who are you?) vs authorization (what are you allowed to do?)
 *   - authentication: authorities, credential, details, principle
 * - AuthenticationManager(interface) vs AuthenticationProvider(implementation)
 *   - AuthenticationManager: rather than handling the authentication request itself,
 *     it delegates to a list of configured AuthenticationProvider s
 *   - AuthenticationProvider: e.g. DaoAuthenticationProvider, JaasAuthenticationProvider, LdapAuthenticationProvider, OpenIDAuthenticationProvider,
 *     each of which is queried in turn to see if it can perform the authentication.
 *     Each provider will either throw an exception or return a fully populated Authentication object.
 *
 * csrf
 * - https://zh.wikipedia.org/wiki/%E8%B7%A8%E7%AB%99%E8%AF%B7%E6%B1%82%E4%BC%AA%E9%80%A0
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // override - local; autowire - global
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication() // provider
                .withUser("user").password("password").roles("USER");

    }
}
