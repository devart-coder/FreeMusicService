package MainApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import Entities.User;
import Repositories.PlayListsRepository;
import Repositories.UserRepository;

//@EnableWebSecurity
@Configuration
public class SecurityConfiguration{
	@Bean
	PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
	
	@Bean 
	UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			User user = userRepo.findByUsername(username);
			if(user != null)   
				return user;
			else
				throw new UsernameNotFoundException("User '" + user +"' not founted");
		};
	}
	
	@Bean
	SecurityFilterChain filterChain (HttpSecurity https) throws Exception {
		return https
		.authorizeHttpRequests(
			requests -> requests
			.requestMatchers("/register").permitAll()
			.requestMatchers("/home","/playlists","/songs","/toplist").hasRole("USER").anyRequest().authenticated()
		).formLogin(
			form -> form
			.loginPage("/login")
			.defaultSuccessUrl("/home")
			.permitAll()
		).logout(
			(logout) -> logout
			.logoutSuccessUrl("/login")
			.permitAll()
		)
//		.csrf(c->c.disable())
		.build();
	}
}