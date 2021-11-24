package com.example.otpgen.otpGen;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@SpringBootApplication
public class OtpGenApplication {

	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OtpGenApplication.class, args);
	}
	
}