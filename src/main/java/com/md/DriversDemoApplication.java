package com.md;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication

//@ComponentScan(basePackages={"com.md"})
public class DriversDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriversDemoApplication.class, args);
	}
	
}
