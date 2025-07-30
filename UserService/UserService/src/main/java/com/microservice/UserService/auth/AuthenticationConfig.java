package com.microservice.UserService.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(secure->secure.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req->req.requestMatchers("/main/**")
                        .authenticated().anyRequest().permitAll())
                .addFilterBefore(new validateUserByJWT(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable());
//                .cors(cors->cors.configurationSource(corsConfigurationSource()))
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults());
        return  httpSecurity.build();
    }
//    private CorsConfigurationSource corsConfigurationSource() {
//        return request -> {
//            CorsConfiguration c=new CorsConfiguration();
//            c.setAllowedOrigins(Arrays.asList("http://localhost:8005/","http://localhost:5173/"));
//            c.setAllowedHeaders(Collections.singletonList("*"));
//            c.setAllowCredentials(true);
//            c.setAllowedHeaders(Collections.singletonList("*"));
//            c.setExposedHeaders(Arrays.asList("Authorization"));
//            c.setMaxAge(3600L);
//            return c;
//        };
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
