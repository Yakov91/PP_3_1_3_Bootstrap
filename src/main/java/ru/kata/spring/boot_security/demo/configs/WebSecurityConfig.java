package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity //уже является @Configuration можно не прописывать
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService,
                             SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Override //конфигурируем spring security
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //начинает конфигурацию, которая определяет, какие запросы к приложению требуют аутентификации.
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/", "index").permitAll()//запросы к корневому URL (/) и URL /index могут быть выполнены без аутентификации
                .antMatchers("/login").permitAll()
                .and()
                .formLogin()
                .successHandler(successUserHandler) //или defaultSuccessUrl("/", true)     //после успешной аутентификации будет использоваться преднастройка для пользователя
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .csrf().disable();//защита от csrf угроз
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
//        return new HiddenHttpMethodFilter();
//    }
}
