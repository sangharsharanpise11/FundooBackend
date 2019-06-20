package com.bridgeIt.fundoo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
//@ComponentScan("com.bridgeIt.fundoo")
public class FundooAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooAppApplication.class, args);
	}

}
