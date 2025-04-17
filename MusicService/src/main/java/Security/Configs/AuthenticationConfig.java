package Security.Configs;

import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import DAO.User.UserEntityBuilder;
import Security.Providers.UserAuthProvider;
import Security.Services.InDataBaseUserDetailService;

@Configuration
public class AuthenticationConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		var encoders = Map.of(
			"noop",NoOpPasswordEncoder.getInstance()
			,"bcrypt",new BCryptPasswordEncoder()
		);
		
		var encoder= new DelegatingPasswordEncoder("bcrypt", encoders);
		encoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
		return encoder;
	}
	@Bean
	UserDetailsService users() {
		return new InDataBaseUserDetailService();
	}
	@Bean
	AuthenticationProvider authProvider() {
		return new UserAuthProvider();
	}
}
