package FMSMain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
//	@Bean
//	SecurityFilterChain filterChain (
////			OAuth2AuthorizedClientManager manager,
//			HttpSecurity https) throws Exception {
//		return 
//		https
//		.oauth2Client(Customizer.withDefaults())		
//		.oauth2Login(login->login
//			.defaultSuccessUrl("/home")
//			.permitAll()
//		)
//		.authorizeHttpRequests(
//			requests -> requests
//			.requestMatchers("/register")
//			.permitAll()
//			.requestMatchers("/home","/playlists","/songs","/toplist","/**")
//			.authenticated()
//		)
//		.formLogin(
//			form -> form
//			.defaultSuccessUrl("/home",true)
//			.permitAll()
//		)
//		.logout(
//			logout -> logout
//			.logoutSuccessUrl("/login")
//			.permitAll()
//		)
//		.build();
//	}
//	@Bean
//	ClientRegistrationRepository clientRepos() {
//		var fms = ClientRegistration
//				.withRegistrationId("FMS")
//				.clientId("client")
//				.clientSecret("secret")
//				.clientName("FMS")
//				.clientAuthenticationMethod( ClientAuthenticationMethod.CLIENT_SECRET_BASIC )
//				.authorizationGrantType ( AuthorizationGrantType.AUTHORIZATION_CODE )
//				.authorizationUri("http://127.0.0.1:9090/oauth2/authorize")
//				.issuerUri("http://127.0.0.1:9090")
//				.redirectUri("http://localhost:8080/login/oauth2/code/FMS")
//				.tokenUri("http://127.0.0.1:9090/oauth2/token")
//				.jwkSetUri("http://127.0.0.1:9090/oauth2/jwks")
//				.scope( "openid" )
//				.build();
//		return new InMemoryClientRegistrationRepository(fms);
//	}
//	@Bean
//	OAuth2AuthorizedClientManager oauth2Manager(
//		ClientRegistrationRepository repos,
//		OAuth2AuthorizedClientRepository oauth2repos
//	) {
//		var provider = OAuth2AuthorizedClientProviderBuilder.builder()
//				.clientCredentials()
//				.authorizationCode()
//				.build();
//		var cm = new DefaultOAuth2AuthorizedClientManager(repos, oauth2repos);
//		cm.setAuthorizedClientProvider(provider);
//		return cm;
//	}
//	@Bean
//	UserServiceDetails userServiceDetails() {
//		return new UserService();
//	}
}