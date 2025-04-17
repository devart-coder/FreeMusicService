package MainApplication;

import java.util.UUID;

import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;

@SpringBootTest
=======
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = PlayListJpaTesting.class)
>>>>>>> 115b575 ( On branch test)
class MusicServiceApplicationTests { 	
}
