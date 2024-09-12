package io.logik.graph_ql_poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class GraphQlPocApplication {

	private final String endpoint1 = "https://swapi-graphql.netlify.app/.netlify/functions/index";

	public static void main(String[] args) {
		SpringApplication.run(GraphQlPocApplication.class, args);
	}

	@Bean
	public HttpSyncGraphQlClient httpSyncGraphQlClient() {
		return HttpSyncGraphQlClient.builder(RestClient.create(endpoint1))
                .build();
	}

	@Bean
	public HttpGraphQlClient httpGraphQlClient() {
		return HttpGraphQlClient.builder(WebClient.create(endpoint1))
				.build();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


}
