package com.gcit.lms;

import javax.security.auth.message.config.AuthConfigFactory;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class LmsApplication {

	public static void main(String[] args) {
		if(AuthConfigFactory.getFactory() == null)
			AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
		SpringApplication.run(LmsApplication.class, args);
	}
}
