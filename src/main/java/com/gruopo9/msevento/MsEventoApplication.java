package com.gruopo9.msevento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.gruopo9.msevento.client")
//@EnableFeignClients
//@EnableSwagger2WebMvc
public class MsEventoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEventoApplication.class, args);
	}

}
