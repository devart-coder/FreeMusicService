package Security.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration{

	@Bean
	PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
	
	@Bean
	SecurityFilterChain filterChain (AuthenticationProvider provider, HttpSecurity https) throws Exception {
		return https
		.authenticationProvider(provider)
		.authorizeHttpRequests(
			requests -> requests
			.requestMatchers("/register")
			.permitAll()
			.requestMatchers("/home","/playlists","/songs","/toplist")
			.hasRole("USER")
			.anyRequest()
			.authenticated()
		)
		.formLogin(
			form -> form
			.loginPage("/login")
			.defaultSuccessUrl("/home")
			.permitAll()
		)
		.logout(
			logout -> logout
			.logoutSuccessUrl("/login")
			.permitAll()
		)
//		.csrf(c->c.disable())
		.build();
	}
}