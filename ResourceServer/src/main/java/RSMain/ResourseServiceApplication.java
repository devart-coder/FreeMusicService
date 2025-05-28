package RSMain;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@ComponentScan(
	basePackages = {
		"RestAPI"
		,"ResourceServiceMain"
		,"User"
		,"Playlist"
	}
)
@EnableJpaRepositories(basePackages = {
		"User"
		,"Playlist"})
@EntityScan(basePackages = {
		"User"
		,"Playlist"})
@SpringBootApplication
@Configuration
public class ResourseServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResourseServiceApplication.class, args);
	}
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
