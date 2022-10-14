package com.example.campos.security.config;

import com.example.campos.security.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final UserSecurityService userService;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SuccessUserHandler successUserHandler;
    @Autowired
    public SecurityConfig(SuccessUserHandler successUserHandler, UserSecurityService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .antMatchers("/camps").hasAnyRole( "ADMIN", "CREATOR", "EMPLOYER")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .successHandler(successUserHandler)
                        .permitAll()
                .and()
                    .logout()
                        .permitAll();
    }

    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
