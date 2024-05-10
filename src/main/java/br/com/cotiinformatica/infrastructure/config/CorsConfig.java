package br.com.cotiinformatica.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// Permite todas as origens
				.allowedOrigins("*")
				// Métodos HTTP permitidos
				.allowedMethods("POST", "PUT", "DELETE", "GET")
				// Permite todos os cabeçalhos
				.allowedHeaders("*");
	}
}