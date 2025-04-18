package MainApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import DAO.PlayList.PlayListBuilder;
import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;
import DAO.User.UserEntityBuilder;
import Interfaces.PlayListJpaTest.PlayListJpaCreate;
import Repositories.UserRepository;
import Services.PlayList.PlayListService;
import Services.PlayList.Interfaces.PlayListCreate;
import Services.PlayList.Interfaces.PlayListDetails;
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
		private PlayListDetails service;
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
	    static class TestConfig { }
		
		@Test 
		@Override
		public void saveSupplierTest() throws Exception {
			var excepted = service.save(() -> PlayListBuilder.defaultPlaylist());
			playlist = service.findOnceById(excepted.getId());
			assertEquals(excepted,playlist);
		}
		@Test 
		@Override
		public void saveEntityWithNullArgExceptionTest() throws Exception {
			playlist=null;
			var e = assertThrows(Exception.class, ()->service.save(playlist));
			assertEquals(e.getMessage(),PlayListCreate.PLAYLIST_NOT_SAVED);
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
		@Override
		public void saveSupplierWithDuplicationTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void saveIterableWithDuplicationTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void saveEntityWithDuplicationTest() throws Exception {
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
		
		@Configuration
	    static class TestConfig { }
		
		@BeforeEach
		public void init() throws Exception {
			user = userRep.save(UserEntityBuilder.defaulUserWith("testUser", "testPassword"));
			playlist=user.getPlaylists().get(0);
			playlist.setName("Test");
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
