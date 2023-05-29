package com.uniovi.sdi2223entrega182;

import com.uniovi.sdi2223entrega182.validators.LoginError;
import com.uniovi.sdi2223entrega182.validators.LoginHandler;
import com.uniovi.sdi2223entrega182.validators.Logout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/script/**", "/", "/signup", "/login/**").permitAll()
                .antMatchers("/user/**", "/log/**").hasAuthority("ROLE_ADMIN").antMatchers("/offer/**")
                .hasAuthority("ROLE_USER").antMatchers("/Conversation/**").hasAuthority("ROLE_USER").anyRequest()
                .authenticated().and().formLogin().defaultSuccessUrl("/home")
                .loginPage("/login").permitAll().
                successHandler(login()).failureHandler(loginErr()).
                and().logout().permitAll().
                logoutSuccessHandler(logoutHandler());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }
    @Bean
    public LoginHandler login(){
        return new LoginHandler();
    }
    @Bean
    public LoginError loginErr(){
        return new LoginError();
    }
    @Bean
    public Logout logoutHandler(){
        return new Logout();
    }
}