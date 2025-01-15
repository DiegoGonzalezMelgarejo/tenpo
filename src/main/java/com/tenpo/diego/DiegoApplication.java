package com.tenpo.diego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
@SpringBootApplication
@EnableR2dbcRepositories
@EnableAspectJAutoProxy
public class DiegoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiegoApplication.class, args);
	}

}
