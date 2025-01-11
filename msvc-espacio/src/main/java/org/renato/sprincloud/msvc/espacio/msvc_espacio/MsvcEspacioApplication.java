package org.renato.sprincloud.msvc.espacio.msvc_espacio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsvcEspacioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcEspacioApplication.class, args);
	}

}
