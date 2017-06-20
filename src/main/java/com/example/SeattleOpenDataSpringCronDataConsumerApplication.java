package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EntityScan(
        basePackageClasses = {SeattleOpenDataSpringCronDataConsumerApplication.class, Jsr310JpaConverters.class}
)
public class SeattleOpenDataSpringCronDataConsumerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SeattleOpenDataSpringCronDataConsumerApplication.class, args);
	}
}
