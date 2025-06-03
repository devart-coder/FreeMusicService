package FMSMain;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import Handlers.ResponseExceptionHandlerFactory;
import Playlist.DAO.PlayListEntity;
import User.DAO.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = {"Controllers"})
@Slf4j
public class MusicServiceApplication {
	private final String BASE_URI = "http://localhost:7070/api/";
	private final String tokenHeaderName = "Authorization";
	public static void main(String[] args) {
		SpringApplication.run(MusicServiceApplication.class, args);
	}
	
	@Bean
	SecurityFilterChain filterChain (
			HttpSecurity https) throws Exception {
		return 
		https
		.oauth2Client(Customizer.withDefaults())		
		.oauth2Login(login->login
			.defaultSuccessUrl("/home")
			.permitAll()
		)
		.authorizeHttpRequests(
			requests -> requests
			.requestMatchers("/register")
			.permitAll()
			.requestMatchers("/home","/playlists","/songs","/toplist","/**")
			.authenticated()
		)
		.formLogin(
			form -> form
			.defaultSuccessUrl("/home",true)
			.permitAll()
		)
		.logout(
			logout -> logout
			.logoutSuccessUrl("/login")
			.permitAll()
		)
		.build();
	}
	@Bean
	ClientRegistrationRepository clientRepos() {
		var fms = ClientRegistration
				.withRegistrationId("FMS")
				.clientId("client")
				.clientSecret("secret")
				.clientName("FMS")
				.clientAuthenticationMethod( ClientAuthenticationMethod.CLIENT_SECRET_BASIC )
				.authorizationGrantType ( AuthorizationGrantType.AUTHORIZATION_CODE )
				.authorizationUri("http://127.0.0.1:9090/oauth2/authorize")
				.issuerUri("http://127.0.0.1:9090")
				.redirectUri("http://localhost:8080/login/oauth2/code/FMS")
				.tokenUri("http://127.0.0.1:9090/oauth2/token")
				.jwkSetUri("http://127.0.0.1:9090/oauth2/jwks")
				.scope( "openid" )
				.build();
		return new InMemoryClientRegistrationRepository(fms);
	}
	@Bean
	OAuth2AuthorizedClientManager oauth2Manager(
		ClientRegistrationRepository repos,
		OAuth2AuthorizedClientRepository oauth2repos
	) {
		var provider = OAuth2AuthorizedClientProviderBuilder.builder()
				.clientCredentials()
				.authorizationCode()
				.build();
		var cm = new DefaultOAuth2AuthorizedClientManager(repos, oauth2repos);
		cm.setAuthorizedClientProvider(provider);
		return cm;
	}
	@Bean
	RestClient restClient() {
		return RestClient.builder().baseUrl(BASE_URI).build();
	}
	@Bean
	@SessionScope
	OAuth2AuthorizedClient getOAuth2Client( OAuth2AuthorizedClientManager manager ) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) 
			return null;

		var request = OAuth2AuthorizeRequest
			.withClientRegistrationId("FMS")
			.principal(auth)
			.build();
		
		return manager.authorize(request);
	}
	
	@Bean
	@SessionScope
	TokenHeader token(OAuth2AuthorizedClient cli) {
		var accessToken = cli.getAccessToken();
		var token = new TokenHeader();
		token.setTokenHeader(accessToken.getTokenType()+accessToken.getTokenValue());
		return token;
	}
	
	@Bean
	@SessionScope
	UserEntity getUser(RestClient client, OAuth2AuthorizedClient cli, TokenHeader token) {
		
		var user = client
			.get()
			.uri("users/"+cli.getPrincipalName())
			.header(tokenHeaderName, token.getTokenHeader())
			.exchange(ResponseExceptionHandlerFactory.getInstance().setBodyType(UserEntity.class).handler(HttpStatus.NOT_FOUND));
		return user;
	}
	
	@Bean
	@RequestScope
	List<PlayListEntity> getUserPlaylists(TokenHeader token, RestClient restClient, UserEntity user) {

		var handler = ResponseExceptionHandlerFactory
				.getInstance()
				.setBodyType(List.class)
				.handler(HttpStatus.NOT_ACCEPTABLE);
		
		var response =  
			restClient
			.get()
			.uri(user.getId() + "/playlists")
			.header(tokenHeaderName,token.getTokenHeader())
			.exchange(handler);
		return response;
	}
	@Bean
	@RequestScope
	PlayListEntity getMain(UserEntity user, RestClient restClient, TokenHeader token) {
		var handler = ResponseExceptionHandlerFactory
				.getInstance()
				.setBodyType(PlayListEntity.class)
				.handler(HttpStatus.NOT_ACCEPTABLE);
		var main = 
				restClient
				.get()
				.uri(user.getId() + "/playlists/main")
				.header(tokenHeaderName,token.getTokenHeader())
				.exchange(handler);
		return main;
		
	}

}
