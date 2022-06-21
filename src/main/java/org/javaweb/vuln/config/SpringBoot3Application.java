package org.javaweb.vuln.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "org.javaweb.vuln.*")
public class SpringBoot3Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBoot3Application.class);
	}

}
