package MainApplication;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
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
import Interfaces.PlayListJpaTest.PlayListJpaCreateTest;
import Interfaces.PlayListJpaTest.PlayListJpaSearchTest;
import Repositories.UserRepository;
import Services.PlayList.PlayListService;
import Services.PlayList.Interfaces.PlayListCreate;
import Services.PlayList.Interfaces.PlayListDetails;
import Services.PlayList.Interfaces.PlayListErrors;
import lombok.extern.slf4j.Slf4j;

class PlayListJpaTesting{
	@AutoConfigureTestDatabase(replace = Replace.NONE)
	@EnableAutoConfiguration
	@EnableJpaRepositories(basePackages = "Repositories")
	@ComponentScan(basePackages = "Services")
	@EntityScan(basePackages = "DAO")
	@DataJpaTest
	@Nested
	class  SavedTestsGroup implements PlayListJpaCreateTest{
		@Autowired 
		private PlayListDetails service;
		@Autowired 
		private UserRepository userRep;
	
		private PlayListEntity playlist=null;
		private PlayListEntity unMainPlaylist;
		private UserEntity user=null;
		
		@BeforeEach
		public void everyTestInitialize() throws Exception {
			
			playlist = PlayListBuilder.builder()
				.setName("test_playlist_name")
				.setMain(true)
				.build();
			
			unMainPlaylist = PlayListBuilder.builder()
				.setName("test_new_playlist_name")
				.build();
			
			user = UserEntityBuilder.builder() 
				.setUsername("test_username")
				.setPassword("test_user_password")
				.setRole("ROLE_USER")
				.setPlaylists(List.of(playlist,unMainPlaylist))
				.build();

			//TODO:WithUserServiceImplementationWillNeedRemoveThisMethods
			unMainPlaylist.setUser(user);
			playlist.setUser(user);
				
			userRep.save(user);
			playlist=service.save(playlist);
			unMainPlaylist=service.save(unMainPlaylist);
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
			assertEquals(e.getMessage(),PlayListErrors.NULL_ARGUMENT);
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
			var expected = service.saveAll( List.of(playlist,unMainPlaylist) );
			var playlists = service.findAllByUser(user);
			assertArrayEquals(expected.toArray(),playlists.toArray());
		}
		@Test
		@Override
		public void saveIterableWithNullArgExceptionTest() throws Exception {
			var e = assertThrows(Exception.class, ()->service.saveAll(null));
			assertEquals(e.getMessage(), PlayListErrors.NULL_ARGUMENT);
		}

		@Test
		@Override
		public void saveSupplierWithNullArgExceptionTest() throws Exception {
		playlist=null;
			var e = assertThrows(Exception.class, ()->service.save( ()-> playlist ));
			assertEquals(e.getMessage(), PlayListErrors.NULL_ARGUMENT);
		}
	}
	
	@AutoConfigureTestDatabase(replace = Replace.NONE)
	@EnableAutoConfiguration
	@EnableJpaRepositories(basePackages = "Repositories")
	@ComponentScan(basePackages = "Services")
	@EntityScan(basePackages = "DAO")
	@DataJpaTest
	@Nested
	public class SearchTestsGroup implements PlayListJpaSearchTest {
		@Autowired 
		private PlayListDetails service;
		@Autowired 
		private UserRepository userRep;
	
		private PlayListEntity playlist;
		private PlayListEntity unMainPlaylist;
		private UserEntity user;
		
		@Configuration
	    static class TestConfig { }
		
		@BeforeEach
		public void everyTestInitialize() throws Exception {
			
			playlist = PlayListBuilder.builder()
				.setName("test_playlist_name")
				.setMain(true)
				.build();
			
			unMainPlaylist = PlayListBuilder.builder()
				.setName("test_new_playlist_name")
				.build();
			
			user = UserEntityBuilder.builder() 
				.setUsername("test_username")
				.setPassword("test_user_password")
				.setRole("ROLE_USER")
				.setPlaylists(List.of(playlist,unMainPlaylist))
				.build();
			
			unMainPlaylist.setUser(user);
			playlist.setUser(user);
				
			userRep.save(user);
			playlist=service.save(playlist);
			unMainPlaylist=service.save(unMainPlaylist);
		}
		@Test
		@Override
		public void findOnceByIdTest() throws Exception {
			var p = service.findOnceById(playlist.getId());
			assertEquals(playlist,p);
		}
		@Test
		@Override
		public void findOnceByUserIdAndNameTest() throws Exception {
		//TODO::NeedAddArgsWithNullCheck
			var p = service.findOnceByUserIdAndName(user.getId(),playlist.getName());
			assertEquals(playlist,p);
		}
		@Test
		@Override
		public void findAllByUserTest() throws Exception {
			var expected = user.getPlaylists();
			var playlists = service.findAllByUserId(user.getId());
			assertArrayEquals(expected.toArray(),playlists.toArray());
		}
		@Test
		@Override
		public void findAllByAuthTest() throws Exception {
//			TODO::MakeImplementationWithSecurityAuthentication
//			var p = service.findAllByAuth(auth);
//			assertEquals(playlist,p);
		}
		@Test
		@Override
		public void findOnceByUserIdAndMainTest() throws Exception {
			var p = service.findOnceByUserIdAndMain(user.getId(), true);
			assertEquals(playlist, p);
			assertNotEquals(unMainPlaylist, p);
			
			p = service.findOnceByUserIdAndMain(user.getId(), false);
			assertEquals(unMainPlaylist, p);
			assertNotEquals(playlist, p);
		}
		@Test
		@Override
		public void findOnceByUserNameAndMainTest() throws Exception {
			var p = service.findOnceByUserNameAndMain(user.getUsername(), true);
			assertEquals(playlist, p);
			assertNotEquals(unMainPlaylist, p);
			
			p = service.findOnceByUserNameAndMain(user.getUsername(), false);
			assertEquals(unMainPlaylist, p);
			assertNotEquals(playlist, p);
		}
		@Test
		@Override
		public void findOnceByAuthAndMainTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findOnceTests() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findAllByUserIdTest() throws Exception {
			var expected = user.getPlaylists();
			var playlists = service.findAllByUserId(user.getId());
			assertArrayEquals(expected.toArray(), playlists.toArray()); 
		}
		@Test
		@Override
		public void findAllByUserNameTest() throws Exception {
			var expected = user.getPlaylists();
			var playlists = service.findAllByUserName(user.getUsername());
			assertArrayEquals(expected.toArray(), playlists.toArray());
		}
		@Test
		@Override
		public void findAll() throws Exception {
			//create a new user
			var secondUser = userRep.save(UserEntityBuilder.defaulUserWith("test_second_username", "test_second_password"));
			
			var playlistsFromSecondUser = secondUser.getPlaylists().get(0);
			var allPlaylists = service.findAll();
			assertArrayEquals(
				allPlaylists.toArray()
				,List.of(playlist,unMainPlaylist,playlistsFromSecondUser).toArray()
			);
		}
//		@Override
//		public void findByNullArgsWithThrowTest() throws Exception {
//			Exception e;
//			
//			e = assertThrows(Exception.class,() -> service.findOnceById(null));
//				assertEquals(e.getMessage(), PlayListErrors.ID_IS_EMPTY);
//				
//			e = assertThrows(Exception.class,() -> service.findOnceById(-1l));
//				assertEquals(e.getMessage(), PlayListErrors.ID_LESS_ZERRO);
//
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(null,playlist.getName()));
//				assertEquals(e.getMessage(), PlayListErrors.USERID_IS_EMPTY);
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(user.getId(),null));
//				assertEquals(e.getMessage(), PlayListErrors.NAME_IS_EMPTY);
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(-1l,playlist.getName()));
//				assertEquals(e.getMessage(), PlayListErrors.USERID_LESS_ZERRO);
//			//TODO::NeedMoreChecksFormethod	
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(-1l,playlist.getName()));
//				assertEquals(e.getMessage(), PlayListErrors.USERID_LESS_ZERRO);
//		}
	}

}
