package org.javaweb.vuln.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.javaweb.vuln.*")
public class SpringBoot3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3Application.class, args);
	}

}
