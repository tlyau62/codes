package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

/**
 * ref:
 * - https://spring.io/guides/topicals/spring-security-architecture/
 * - https://stackoverflow.com/questions/2323377/spring-security-authenticationmanager-vs-authenticationprovider
 *
 * keys:
 * - authentication (who are you?) vs authorization (what are you allowed to do?)
 *   - authentication is a set of (authorities, principle, credential, details)
 *   - e.g. for authentication(UsernamePasswordAuthenticationToken) => authorities(e.g. arraylist [ROLE_ADMIN]), principle(e.g. username), credential(e.g. password)
 * - AuthenticationManager(interface) vs AuthenticationProvider(implementation)
 *   - AuthenticationManager: rather than handling the authentication request itself,
 *     it delegates to a list of configured AuthenticationProvider s
 *   - AuthenticationProvider: e.g. DaoAuthenticationProvider, JaasAuthenticationProvider, LdapAuthenticationProvider, OpenIDAuthenticationProvider,
 *     each of which is queried in turn to see if it can perform the authentication.
 *     Each provider will either throw an exception or return a fully populated Authentication object.
 *
 * csrf:
 * - https://zh.wikipedia.org/wiki/%E8%B7%A8%E7%AB%99%E8%AF%B7%E6%B1%82%E4%BC%AA%E9%80%A0
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
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
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("admin").roles("ADMIN");

    }


    /* an example of using UsernamePasswordAuthenticationToken as AuthenticationProvider
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(new AuthenticationProvider() {

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

                *//*System.out.println(token.getCredentials());
                System.out.println(token.getPrincipal());*//*

                String SECURE_ADMIN_PASSWORD = "adminPW";

                List<GrantedAuthority> authorities = SECURE_ADMIN_PASSWORD.equals(token.getCredentials()) ?
                        AuthorityUtils.createAuthorityList("ROLE_ADMIN") : null;

                // System.out.println(authorities);

                return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
            }
        });
    }*/
}
