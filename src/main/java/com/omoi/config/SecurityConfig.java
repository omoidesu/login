package com.omoi.config;

import com.omoi.filter.JwtAuthenticationTokenFilter;
import com.omoi.handler.AccessDeniedHandlerImpl;
import com.omoi.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author omoi
 * @date 2023/7/2
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, AccessDeniedHandlerImpl accessDeniedHandler, AuthenticationEntryPointImpl authenticationEntryPoint) {
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/user/*").anonymous()
                .anyRequest().authenticated();

        // 将自定义过滤器加入过滤器链
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 自定义处理器
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
