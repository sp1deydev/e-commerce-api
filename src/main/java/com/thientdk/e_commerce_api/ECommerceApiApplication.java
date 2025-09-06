package com.thientdk.e_commerce_api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
@ConfigurationPropertiesScan
public class ECommerceApiApplication {

	private static final Logger log = LogManager.getLogger(ECommerceApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApiApplication.class, args);

		log.info("========================================================");
		log.info("|              E-COMMERCE API CODE BASE                |");
		log.info("|              DEVELOP BY THIEN TRAN                   |");
		log.info("========================================================");
	}

}
