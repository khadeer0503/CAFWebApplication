package com.CAF.WebApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CAFWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CAFWebApplication.class, args);
		System.out.print("Program Running on :");
		System.out.println("http://localhost:8081");
	}

}

