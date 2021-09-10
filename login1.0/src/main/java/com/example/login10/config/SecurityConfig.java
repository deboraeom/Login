package com.example.login10.config;

import com.example.login10.Service.UserSecurityDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserSecurityDetailsService userSecurityDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}", passwordEncoder.encode("123"));

        builder
                .inMemoryAuthentication()
                .withUser("Débora").password(passwordEncoder.encode("123"))
                .roles("ADMIN")
                .and()
                .withUser("Lucia").password(passwordEncoder.encode("123"))
                .roles("CAT");


        builder.userDetailsService(userSecurityDetailsService)
                .passwordEncoder(passwordEncoder);
    }

}
