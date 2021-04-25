package uep.diet.manager.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author akazmierczak
 * @date 08.04.2021
 */
@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                .and()

                .authorizeRequests()
                .anyRequest()
                    .permitAll()

                .and()

                .formLogin()
                .loginProcessingUrl("/login")

                .and()

                .httpBasic();

      log.info("no security set up");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("steve")
                .password(encoder.encode("password")).roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/css/**", "/webjars/**", "/meal/**",
                "/ingredient/**", "/h2-console/**", "**/**", "/users/**", "/user/**");
    }


}
