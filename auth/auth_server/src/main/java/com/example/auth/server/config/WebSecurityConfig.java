package com.example.auth.server.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

//    @Bean
//    @ConditionalOnMissingBean(PasswordEncoder.class)
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


    /**
     * Spring Security的过滤器链，用于Spring Security的身份认证。
     * 不拦截拦截静态资源
     * 如果您不想要警告消息并且需要性能优化，则可以为静态资源引入第二个过滤器链
     * https://github.com/spring-projects/spring-security/issues/10938
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests().requestMatchers(
                        "/v3/api-docs/**"
                        , "/swagger-ui.html"
                        , "/swagger-ui/**"
                        , "/oauth2/captcha"
                        , "/oauth2/logout"
                        , "/oauth2/tenant"
                        , "/oauth2/public_key"
                        , "/actuator/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                // 自定义登录页面
                // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html
                // 登录页面 -> DefaultLoginPageGeneratingFilter
                .formLogin(Customizer.withDefaults())
                .logout()
                // 清除session
                .invalidateHttpSession(true)
                .and()
                .build();
    }

}
