package org.javaweb.vuln.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.javaweb.vuln.*")
public class SpringBoot2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot2Application.class, args);
	}

}
