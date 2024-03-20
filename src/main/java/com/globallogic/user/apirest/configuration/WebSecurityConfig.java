package com.globallogic.user.apirest.configuration;

import com.globallogic.user.apirest.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig {

    @Value("${login.url}")
    private String loginUrl;
    @Value("${swagger.ui.url}")
    private String swaggerUIUrl;
    @Value("${api.docs.url}")
    private String apiDocsUrl;
    @Value("${api.docs.urls}")
    private String apiDocsUrls;
    @Value("${h2.console.url}")
    private String h2ConsoleUrl;
    @Value("${h2.console.urls}")
    private String h2ConsoleUrls;
    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, swaggerUIUrl, apiDocsUrl, apiDocsUrls).permitAll()
                        .requestMatchers(HttpMethod.POST).permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .headers(headers -> headers
                        .frameOptions(options -> options.disable()))
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
