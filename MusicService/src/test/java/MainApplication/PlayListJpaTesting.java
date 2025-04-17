package MainApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import DAO.PlayList.PlayListBuilder;
import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;
import DAO.User.UserEntityBuilder;
import Interfaces.PlayListJpaTest.PlayListJpaCreate;
import Repositories.UserRepository;
import Services.Implementations.PlayListService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class PlayListJpaTesting{
	
	@DataJpaTest
	@AutoConfigureTestDatabase(replace = Replace.NONE)
	@EnableAutoConfiguration
	
	@EnableJpaRepositories(basePackages = "Repositories")
	@ComponentScan(basePackages = "Services")
	@EntityScan(basePackages = "DAO")
	@Nested
	class  SavedTestsGroup implements PlayListJpaCreate{
		@Autowired 
		private PlayListService service;
		
		@Autowired 
		private UserRepository userRep;
	
		private PlayListEntity playlist=null;
		private UserEntity user=null;
		
		@BeforeEach
		public void init() throws Exception {
			user = userRep.save(UserEntityBuilder.defaulUserWith("testUser", "testPassword"));
			playlist=user.getPlaylists().get(0);
			playlist.setName("Test");
		}
		@Configuration
	    static class TestConfig {
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
		@Test 
		@Override
		public void saveSupplierTest() throws Exception {
			//TODO::NeedMakeArgsWithNullCheck
			var excepted = service.save(() -> PlayListBuilder.defaultPlaylist());
			playlist = service.findOnceById(excepted.getId());
			assertEquals(excepted,playlist);
		}
		@Test 
		@Override
		public void saveEntityWithNullArgExceptionTest() throws Exception {
			playlist=null;
			var e = assertThrows(Exception.class, ()->service.save(playlist));
			assertTrue(e.getMessage().contains("not saved"));
		}
		@Test 
		@Override
		public void saveEntityTest() throws Exception {
			var excepted = service.save(PlayListBuilder.defaultPlaylist());
			playlist = service.findOnceById(excepted.getId());
			assertEquals(excepted,playlist);
		}
		@Test 
		@Override
		public void saveIterableTest() throws Exception {
			//TODO:REMAKEIT
			var exceptedList = service.saveAll( List.of(PlayListBuilder.defaultPlaylist()) );
			var list = service.findOnceById(exceptedList.get(0).getId());
			assertEquals(exceptedList,List.of(list));
		}
		@Test
		@Override
		public void saveIterableWithNullArgExceptionTest() throws Exception {
			// TODO Auto-generated method stub
		
		}

	@Test
	@Override
	public void saveSupplierWithNotSavedExceptionTest() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Override
	public void saveSupplierWithNullArgExceptionTest() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Override
	public void saveIterableWithNotSavedExceptionTest() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Override
	public void saveEntityWithNotSavedExceptionTest() throws Exception {
		// TODO Auto-generated method stub
		
	}
	}
	
	@AutoConfigureTestDatabase(replace = Replace.NONE)
	@EnableAutoConfiguration
	@EnableJpaRepositories(basePackages = "Repositories")
	@ComponentScan(basePackages = "Services")
	@EntityScan(basePackages = "DAO")
	@DataJpaTest
	@Nested
	public class SearchTestsGroup{
		@Autowired 
		private PlayListService service;
		
		@Autowired 
		private UserRepository userRep;
	
		private PlayListEntity playlist=null;
		private UserEntity user=null;
		
		@BeforeEach
		public void init() throws Exception {
			user = userRep.save(UserEntityBuilder.defaulUserWith("testUser", "testPassword"));
			playlist=user.getPlaylists().get(0);
			playlist.setName("Test");
		}
		@Configuration
	    static class TestConfig {
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
		@Test
		public void findOnceByIdTest() throws Exception {
			//TODO::NeedMakeArgsWithNullCheck
			var p = service.findOnceById(playlist.getId());
			assertEquals(playlist,p);
		}
		@Test
		public void findOnceByUserIdAndNameTest() throws Exception {
		//TODO::NeedMakeArgsWithNullCheck
			var p = service.findOnceByUserIdAndName(user.getId(),playlist.getName());
			assertEquals(playlist,p);
		}
		@Test
		public void findAllByUserTest() throws Exception {
			var p = service.findAllByUser(user);
			assertEquals(playlist,p.get(0));
		}
		@Test
		public void findAllByAuthTest() throws Exception {
//			TODO::MakeImplementationWithSecurityAuthentication
//			var p = service.findAllByAuth(auth);
//			assertEquals(playlist,p);
		}
	}


	
}
