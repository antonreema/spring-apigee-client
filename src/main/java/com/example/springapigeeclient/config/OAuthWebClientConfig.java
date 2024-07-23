package com.example.springapigeeclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class OAuthWebClientConfig {
	
	@Bean
	public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations,
			ObjectMapper objectMapper, ServerOAuth2AuthorizedClientRepository clientRepository) {
		
		ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
				new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, clientRepository);
		oauth.setDefaultClientRegistrationId("apigeesample"); // --> registration-id mentioned in yaml file.
		WebClient.Builder builder = WebClient.builder();
		builder.defaultHeader("Content-Type", MediaType.APPLICATION_JSON.toString());
		builder.defaultHeader("Accept", MediaType.APPLICATION_JSON.toString());
		return builder.filter(oauth).build();
	}
	
	/**Removing Authorization to the current project for APIGEE to connect back and give AuthToken**/
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.oauth2Login(Customizer.withDefaults())
				.authorizeExchange( auth -> auth.pathMatchers("/*").permitAll())
				.build();
	}

}
