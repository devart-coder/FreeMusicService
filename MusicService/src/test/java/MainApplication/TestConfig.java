package MainApplication;

import java.util.UUID;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@TestConfiguration
public class TestConfig {
	@Bean
	ClientRegistrationRepository monitoringService() {
		var client = ClientRegistration
			.withRegistrationId(UUID.randomUUID().toString())
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
			.clientId("client")
			.clientName("Authentication")
			.clientSecret("secret")
			.redirectUri("http://localhost:8080/login/oauth2/code/FMS")
			.authorizationUri("http://localhost:8080/aoth2/authorize")
			.tokenUri("http://localhost:8080/aoth2/token")
			.build();
		return new InMemoryClientRegistrationRepository(client);
	}
}
