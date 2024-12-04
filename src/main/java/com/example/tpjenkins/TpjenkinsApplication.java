package com.example.tpjenkins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TpjenkinsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpjenkinsApplication.class, args);
	}

}
