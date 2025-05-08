package Main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceConfig {
	@Bean
	SecurityFilterChain sfc(HttpSecurity http) throws Exception {
		return http
		.oauth2ResourceServer( c -> c.jwt(Customizer.withDefaults()) )
		.authorizeHttpRequests(c->c.anyRequest().permitAll())
		.build();
	}
}
