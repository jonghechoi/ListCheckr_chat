package com.example.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080", "http://192.168.30.93:8080");
                registry.addMapping("/app/**").allowedOrigins("http://localhost:3000")
                        .allowedMethods("*")
                        .allowedHeaders("Origin", "Content-Type", "Accept")
                        .allowCredentials(false);
            }
        };
    }
}
