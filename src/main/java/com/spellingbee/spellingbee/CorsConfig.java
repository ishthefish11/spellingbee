package com.spellingbee.spellingbee;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://192.168.1.183:8080", "http://127.0.0.1:8080") // Allow requests from this origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Allow credentials (cookies, authorization headers)
    }
}