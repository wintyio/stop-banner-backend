package com.stopbanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class StopbannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StopbannerApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")
						.allowedOrigins("http://localhost:3000", "http://localhost:8000", "https://api.bannerhunter.kr", "https://bannerhunter.kr", "https://www.bannerhunter.kr")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}
}
