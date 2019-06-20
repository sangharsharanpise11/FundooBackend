package com.bridgeIt.fundoo.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.bridgeIt.fundoo.response.Response;

@Configuration
public class ApplicationConfig
{
 @Bean
 ModelMapper getModelmapper()
 {
	 ModelMapper modelMapper=new ModelMapper();
	 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	 return modelMapper;
 }
 
 @Bean
 public Response getResponse()
 {
	 return new Response();
 }
 
 @Bean
	public PasswordEncoder getpasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
