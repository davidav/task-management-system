package org.example.taskmanagementsystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class SecurityConfiguration {

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    public SecurityConfiguration(){

    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, baseUrl + "/task/**").permitAll()
                        .requestMatchers(HttpMethod.GET, baseUrl + "/user/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, baseUrl + "/user").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, baseUrl + "/user/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, baseUrl + "/user/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions().disable())// for H-2 browser console access
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

}
