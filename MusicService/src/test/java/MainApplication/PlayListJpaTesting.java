package MainApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.util.Assert;

import DAO.PlayList.PlayListBuilder;
import DAO.PlayList.PlayListEntity;
import Services.Implementations.PlayListService;

@ComponentScan(
	basePackages = {
		"RestControllers"
		,"Controllers"
		,"DAO"
		,"Security"
		,"Services"
	}
)
@EnableJpaRepositories(basePackages = "Repositories")
@EntityScan(basePackages = "DAO")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayListJpaTesting {
	@Configuration
    static class ContextConfiguration {
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
	@Autowired 
	private PlayListService service;
	private PlayListEntity playlist;
	
	@BeforeEach
	public void init() { playlist=null; }
	//SaveTests
	@Test 
	public void saveSupplierTest() throws Exception {
		var excepted = service.save(() -> PlayListBuilder.defaultPlaylist());
		playlist = service.findOnceById(excepted.getId());
		assertEquals(excepted,playlist);
	}
	@Test 
	public void saveEntityTestByNull() throws Exception {
		var e = assertThrows(Exception.class, ()->service.save(playlist));
		assertTrue(e.getMessage().contains("not saved"));
	}
	@Test 
	public void saveEntityTest() throws Exception {
		var excepted = service.save(PlayListBuilder.defaultPlaylist());
		playlist = service.findOnceById(excepted.getId());
		assertEquals(excepted,playlist);
	}
	@Test 
	public void saveIterableTest() throws Exception {
		var exceptedList = service.saveAll( List.of(PlayListBuilder.defaultPlaylist()) );
		var list = service.findOnceById(exceptedList.get(0).getId());
		assertEquals(exceptedList,List.of(list));
	}
	//SearxhTests
//	@Test
//	public void findOnceByIdTest() throws Exception {
//		playlist = PlayListBuilder.defaultPlaylist();
//		var p = service.findOnceById(playlist.getId());
//		assertEquals(p, playlist);
//	}
//	@Test
//	public void findOnceNameTest() throws Exception {
//		playlist = PlayListBuilder.defaultPlaylist();
//		var p = service.findOnceByName(playlist.getName());
//		assertEquals(p, playlist);
//	}
//	
}
