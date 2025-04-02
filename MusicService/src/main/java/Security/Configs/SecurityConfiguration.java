package Security.Configs;

import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

import Security.Providers.UserAuthProvider;
import Security.Services.InDataBaseUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
	@Bean
	SecurityFilterChain filterChain (UserAuthProvider provider, HttpSecurity https) throws Exception {
		return 
		https
		.oauth2Client(Customizer.withDefaults())		
		.oauth2Login(login->login
			.defaultSuccessUrl("/home")
			.permitAll()
		)
		.authenticationProvider(provider)
		.authorizeHttpRequests(
			requests -> requests
			.requestMatchers("/register")
			.permitAll()
			.requestMatchers("/home","/playlists","/songs","/toplist","/**")
//			.hasAnyRole("USER","user","User")
//			.anyRequest()
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