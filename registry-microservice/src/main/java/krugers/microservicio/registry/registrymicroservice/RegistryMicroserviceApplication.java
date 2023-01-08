package krugers.microservicio.registry.registrymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegistryMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistryMicroserviceApplication.class, args);
	}

}
