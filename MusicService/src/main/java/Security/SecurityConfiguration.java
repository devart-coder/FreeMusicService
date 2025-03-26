package Security;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;

import Security.Providers.UserAuthProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

	@Bean
	PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

	@Bean
	ClientRegistrationRepository clientRepository() {
		var client = ClientRegistration
				.withRegistrationId(UUID.randomUUID().toString())
				.clientId("client")
				.clientSecret("{noop}secret")
				.clientName("Custom")
				.authorizationUri("http://localhost:8080/oauth2/authorize")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.issuerUri("http://127.0.0.1:8080")
				.jwkSetUri("http://127.0.0.1:8080/oauth/jwks")
				.redirectUri("http://localhost:8080/login/oauth2/my_auth")
				.scope(OidcScopes.OPENID)
				.tokenUri("http://127.0.0.1:8080/token")
				.userInfoUri(null)
				.build();
		return new InMemoryClientRegistrationRepository(client);		
	}
	
	
	@Bean
	SecurityFilterChain filterChain (UserAuthProvider provider, HttpSecurity https) throws Exception {
		return 
		https
		.oauth2Client(Customizer.withDefaults())		
		.oauth2Login(Customizer.withDefaults())
		.authenticationProvider(provider)
		.authorizeHttpRequests(
			requests -> requests
			.requestMatchers("/register")
			.permitAll()
			.requestMatchers("/home","/playlists","/songs","/toplist")
			.hasAnyRole("USER")
			.anyRequest()
			.authenticated()
		)
		.formLogin(
			form -> form
//			.loginPage("/login")
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
}