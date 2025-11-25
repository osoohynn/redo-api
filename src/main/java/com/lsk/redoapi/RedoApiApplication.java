package com.lsk.redoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RedoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedoApiApplication.class, args);
	}

}
