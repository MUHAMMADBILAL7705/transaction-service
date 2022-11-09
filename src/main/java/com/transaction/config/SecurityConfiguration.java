package com.transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends ResourceServerConfigurerAdapter {

    @Bean
    public Authentication authentication() {
        return null;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().
                loginPage("/login").and().authorizeRequests()
                .antMatchers(new String[]{
                        "/api/balances/*","/api/transactions/*"
                }).permitAll()
                .anyRequest().authenticated().and()
                .csrf().disable();
    }

}
