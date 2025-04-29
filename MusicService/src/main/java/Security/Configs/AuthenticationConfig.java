package Security.Configs;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	InDataBaseUserDetailService users() {
		return new InDataBaseUserDetailService();
	}
	
	@Bean
	AuthenticationProvider authProvider() {
		return new UserAuthProvider();
	}
}
