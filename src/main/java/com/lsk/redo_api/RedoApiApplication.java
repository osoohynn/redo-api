package com.lsk.redo_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {"com.lsk.redo_api", "com.lsk.redoapi"})
@EnableJpaAuditing
public class RedoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedoApiApplication.class, args);
	}

}
