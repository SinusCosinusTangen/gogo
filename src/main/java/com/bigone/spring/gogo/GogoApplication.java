package com.bigone.spring.gogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories("com.bigone.spring.gogo.repository")
public class GogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GogoApplication.class, args);
	}

}
