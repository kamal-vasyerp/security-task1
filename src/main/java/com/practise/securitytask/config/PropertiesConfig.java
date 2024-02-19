package com.practise.securitytask.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class PropertiesConfig {
	
	@Value("${spring.users}")
	private List<String> users;
	
	@Value("${spring.passwords}")
	private List<String> passwords;
	
	@Value("${spring.roles}")
	private List<String> roles;
	
}
