package org.javaweb.vuls.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.javaweb.vuls.*")
public class RASPTestSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(RASPTestSpringBootApplication.class, args);
	}

}
