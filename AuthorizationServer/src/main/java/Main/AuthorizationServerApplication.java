package Main;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import Repositories.UserRepository;
import Security.Providers.UserAuthProvider;
import Security.Services.InDataBaseUserDetailService;

@SpringBootApplication
@Configuration
@EnableJpaRepositories(basePackages = "Repositories")
@EntityScan(basePackages = {"DAO"})
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}
	
	@Bean
	@Order(1)
	SecurityFilterChain asFirstFilter(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http
		.getConfigurer( OAuth2AuthorizationServerConfigurer.class )
		.oidc(Customizer.withDefaults());
		http.exceptionHandling(c -> c
			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));
		return http.build();
	}
	
	@Bean
	@Order(2)
	SecurityFilterChain asSecondFilter(HttpSecurity http) throws Exception {
		return
			http
			.formLogin(Customizer.withDefaults())
			.authorizeHttpRequests(auth->auth.anyRequest().permitAll())
			.build();
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
	UserDetailsService users() {
		return new InDataBaseUserDetailService();
	}
	@Bean
	UserAuthProvider provider() {
		return new UserAuthProvider();
	}
	@Bean
	JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException{
		var generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		var keys = generator.generateKeyPair();
		
		var publicKey = (RSAPublicKey)keys.getPublic();
		var privateKey = (RSAPrivateKey)keys.getPrivate();
		
		var rsaKey = new RSAKey.Builder(publicKey)
			.privateKey(privateKey)
			.keyID(UUID.randomUUID().toString())
			.build();
		var jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}
}
