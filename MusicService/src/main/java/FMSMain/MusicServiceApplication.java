package FMSMain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import User.DAO.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = {"Controllers"})
public class MusicServiceApplication {

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
		return RestClient
			.builder()
			.baseUrl("http://localhost:7070/api/")
			.build();

	}
	
	@Bean
	@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS )
	UserEntity getUser(RestClient client, OAuth2AuthorizedClientManager manager) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) 
			return null;
		
		var request = OAuth2AuthorizeRequest
			.withClientRegistrationId("FMS")
			.principal(auth)
			.build();
		
		var cli = manager.authorize(request);
		var token = cli.getAccessToken();
		if(token == null)
			return null;
		return client
				.get()
				.uri("users/"+auth.getName())
				.header("Authorize",token.getTokenType().toString()+token.getTokenValue())
				.retrieve()
				.body(UserEntity.class);
	}
}
