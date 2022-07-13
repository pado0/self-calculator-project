package com.pado.calculator.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // todo: disable 하지말고 postman에서 토큰 처리하는 법 확인하기
                .authorizeRequests()
                // 그냥 허용할 응답들
                .mvcMatchers("/", "/index", "/login", "/sign-up", "/operation", "/operation/history").permitAll()
                .anyRequest().authenticated(); // 나머지는 로그인을 해야 쓸 수 있다.

        http.formLogin()
                .loginPage("/login") // 커스텀 로그인 폼 경로
                .defaultSuccessUrl("/",true)
                .permitAll(); // 로그인 폼에 접근할 수 있는 사용자는 전체

        http.logout() // 기본적으로 로그아웃이 켜져있다.
                .logoutSuccessUrl("/"); // 로그아웃하고 어디로 갈지만 지정.


    }

    // 스프링 시큐리티로 인해 뷰에서 로고 이미지가 불러와 지지 않는 문제 해결
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
