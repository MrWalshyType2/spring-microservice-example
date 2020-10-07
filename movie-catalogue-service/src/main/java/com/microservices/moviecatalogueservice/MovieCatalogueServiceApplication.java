package com.microservices.moviecatalogueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieCatalogueServiceApplication {

	/**
	 * Use RestTemplate to programmatically communicate with APIs,
	 * returns a RestTemplate. RestTemplate is synchronous.
	 *
	 * Is a @Bean for dependency injection, is @LoadBalanced for balancing between services of the same name.
	 *
	 * @return RestTemplate
	 * @deprecated Spring will be deprecating RestTemplate in the future.
	 */
	@Bean
	@LoadBalanced // Does service discovery, balances the load. The RestTemplate will look for a relevant service
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/**
	 * Returns a WebClient Builder for asynchronous
	 * programmatic API communication.
	 *
	 * @return WebClient.Builder
	 */
	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}

	/**
	 * Program entry point.
	 *
	 * @param args Unused
	 * @return Nothing
	 */
	public static void main(String[] args) {

		SpringApplication.run(MovieCatalogueServiceApplication.class, args);
	}

}
