package com.efeitoeco.eCommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	private Contact contact() {
		return new Contact("Efeito Eco",
				"https://github.com/efeitoeco",
				"efeitoeco@email.com");
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Efeito Eco")
				.description("API do Ecommerce: Efeito Eco")
				.version("1.0")
				.contact(contact())
				.build();
	}
	
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.efeitoeco.eCommerce.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	

}
