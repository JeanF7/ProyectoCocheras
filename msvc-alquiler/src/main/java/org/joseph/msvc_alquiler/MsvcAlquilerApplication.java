package org.joseph.msvc_alquiler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcAlquilerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAlquilerApplication.class, args);
	}

}
