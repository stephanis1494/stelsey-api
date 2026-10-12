package com.stelsey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class StelseyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StelseyApplication.class, args);
	}

}
