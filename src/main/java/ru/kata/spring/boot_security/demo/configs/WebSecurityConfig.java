package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import ru.kata.spring.boot_security.demo.services.UserDetailsServ;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity //уже является @Configuration можно не прописывать
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServ userDetailsServ;
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public WebSecurityConfig(UserDetailsServ userDetailsServ, SuccessUserHandler successUserHandler) {
        this.userDetailsServ = userDetailsServ;
        this.successUserHandler = successUserHandler;
    }


    @Override //конфигурируем spring security
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//защита от csrf угроз
                .authorizeRequests() //начинает конфигурацию, которая определяет, какие запросы к приложению требуют аутентификации.
                .antMatchers("/").permitAll()//запросы к корневому URL (/) и URL /index могут быть выполнены без аутентификации
                .antMatchers("/index").permitAll()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin", "/userCreate", "/userUpdate").hasRole("ADMIN")
                .anyRequest().authenticated() //все остальные запросы к приложению требуют аутентификации. Если пользователь не аутентифицирован, он будет перенаправлен на страницу входа.
                .and()
                .formLogin().successHandler(successUserHandler) //или defaultSuccessUrl("/", true)     //после успешной аутентификации будет использоваться преднастройка для пользователя
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")//настраивает процесс выхода из системы
                .permitAll();
    }

        @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsServ);

        return authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource);
//    }
}

//@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsServ).passwordEncoder(passwordEncoder());
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsServ)
//                .passwordEncoder(passwordEncoder());
//    }

    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("user"))
//                .roles("ADMIN", "USER")
//                .build();
//
//        UserDetails user = User.builder().username("user")
//                .password("{bcrypt}$2a$12$Yl2995NhjtxtQOc.zIov3OGO8G1d3y6NogrPVtXLv6l3Hvov0EIvy")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
