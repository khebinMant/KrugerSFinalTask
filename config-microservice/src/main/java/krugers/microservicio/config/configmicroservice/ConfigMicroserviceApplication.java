package krugers.microservicio.config.configmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigMicroserviceApplication.class, args);
	}

}
