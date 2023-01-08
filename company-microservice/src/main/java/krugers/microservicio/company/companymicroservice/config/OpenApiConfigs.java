package krugers.microservicio.company.companymicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfigs {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(
                    new Info()
                    .title("Company API")
                    .description("Esta API contiene un CRUD de compañias, además de implementar todas las funcionalidades y los criterios de aceptación.")
                    .version("V1.0")
                    );
    }
}
