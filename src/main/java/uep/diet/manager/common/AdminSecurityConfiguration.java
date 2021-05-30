package uep.diet.manager.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author akazmierczak
 * @date 08.04.2021
 */
@Configuration
@Order(99) //this means that Admin sec configuration
// will be evaluated first (before security config) because default is 100
public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.mvcMatcher("/panel/admin")
                .authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN")
                .and()
                .httpBasic();
    }
}
