package com.microservices.moviecatalogueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient // Enables this app as a Netflix Eureka client
@EnableCircuitBreaker // Enables the Netflix Hystrix circuit breaker
@EnableHystrixDashboard // Makes the app a Hystrix dashboard alongside its normal behaviour | find at url/hystrix
public class MovieCatalogueServiceApplication {

	/**
	 * Returns a RestTemplate with a 5 second timeout to programmatically communicate with APIs,
	 * returns a RestTemplate. RestTemplate is synchronous.
	 *
	 * Is a @Bean for dependency injection, is @LoadBalanced for balancing between services of the same name.
	 *
	 * @return RestTemplate
	 * @deprecated Deprecated as of Spring 5.0, exists in 'maintenance mode'.
	 */
	@Bean
	@LoadBalanced // Does service discovery, balances the load. The RestTemplate will look for a relevant service
	public RestTemplate getRestTemplate() {
		// Using for creating the timeout property to help prevent thread hanging. This can't fully handle multiple requests
		// at once on a thread.
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(5000); // 5s/5000ms till thread is killed and next request is dealt with
		return new RestTemplate(clientHttpRequestFactory);
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
