package pe.edu.cibertec.rueditasfrontend.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


import java.time.Duration;

@Configuration // Indica que esta clase es una clase de configuración de Spring
public class RestTemplateCon {

    @Bean // Define un bean de Spring que se puede inyectar en otras partes de la aplicación
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(3)) // Establece el tiempo de espera de conexión a 3 segundos
                .setReadTimeout(Duration.ofSeconds(3)) // Establece el tiempo de espera de lectura a 3 segundos
                .build(); // Construye y devuelve una instancia de RestTemplate
    }
}
