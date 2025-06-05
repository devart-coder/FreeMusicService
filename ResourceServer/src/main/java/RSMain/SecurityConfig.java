package RSMain;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	PasswordEncoder password() {
		var encoders = Map.of(
			"noop",NoOpPasswordEncoder.getInstance()
			,"bcrypt",new BCryptPasswordEncoder()
		);
		var encoder =  new DelegatingPasswordEncoder("bcrypt", encoders);
		encoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
		return encoder;
	}	
	@Bean
	SecurityFilterChain sfc(HttpSecurity http) throws Exception {
		return http
		.oauth2ResourceServer( c -> c.jwt(Customizer.withDefaults()) )
		.authorizeHttpRequests(c -> c.anyRequest().permitAll())
		.csrf(c->c.disable())
		.build();
	}
}
