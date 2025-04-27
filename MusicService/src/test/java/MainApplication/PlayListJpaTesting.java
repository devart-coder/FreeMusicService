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

@Slf4j
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
//			playlist = service.findOnceById(excepted.getId());
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
//			var excepted = service.save(PlayListBuilder.defaultPlaylist());
//			playlist = service.findOnceById(excepted.getId());
//			assertEquals(excepted,playlist);
		}
		@Test 
		@Override
		public void saveIterableTest() throws Exception {
//			var expected = service.saveAll( List.of(playlist,unMainPlaylist) );
//			var playlists = service.findAllByUser(user);
//			assertArrayEquals(expected.toArray(),playlists.toArray());
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
		private PlayListEntity duplicate;
		
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
			duplicate = PlayListBuilder.builder()
				.setName("test_new_playlist_name")
				.setMain(false)
				.build();
			
			user = UserEntityBuilder.builder() 
				.setUsername("test_username")
				.setPassword("test_user_password")
				.setRole("ROLE_USER")
				.setPlaylists(List.of(playlist,unMainPlaylist,duplicate))
				.build();
			
			unMainPlaylist.setUser(user);
			playlist.setUser(user);
				
			userRep.save(user);
			playlist=service.save(playlist);
			unMainPlaylist=service.save(unMainPlaylist);
			duplicate=service.save(duplicate);
		}
		@Test
		@Override
		public void findOnceByIdTest() throws Exception {
//			var p = service.findOnceById(playlist.getId());
//			assertEquals(playlist,p);
		}
		@Test
		@Override
		public void findOnceByUserIdAndNameTest() throws Exception {
//			var p = service.findOnceByUserAndName(user,playlist.getName());
//			assertEquals(playlist,p);
		}
		@Test
		@Override
		public void findAllByUserTest() throws Exception {
//			var expected = user.getPlaylists();
//			var playlists = service.findAllByUser(user);
//			assertArrayEquals(expected.toArray(),playlists.toArray());
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
//			var p = service.findOnceMainPlayListByUserId(user.getId());
//			assertEquals(playlist, p);
//			assertNotEquals(unMainPlaylist, p);
		}
		@Test
		@Override
		public void findOnceByUserNameAndMainTest() throws Exception {
//			var p = service.findOnceMainPlaylistByUsername(user.getUsername());
//			assertEquals(playlist, p);
//			assertNotEquals(unMainPlaylist, p);
		}
		@Test
		@Override
		public void findOnceByAuthAndMainTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findAllByUserIdTest() throws Exception {
//			var expected = user.getPlaylists();
//			var playlists = service.findAllByUserId(user.getId());
//			assertArrayEquals(expected.toArray(), playlists.toArray()); 
		}
		@Test
		@Override
		public void findAllByUsernameTest() throws Exception {
//			var expected = user.getPlaylists();
//			var playlists = service.findAllByUsername(user.getUsername());
//			assertArrayEquals(expected.toArray(), playlists.toArray());
		}
		@Test
		@Override
		public void findAllTests() throws Exception {
			//Create a new user
			var secondUser = userRep.save(UserEntityBuilder.defaulUserWith("test_second_username", "test_second_password"));
			
//			var playlistsFromSecondUser = secondUser.getPlaylists().get(0);
//			var allPlaylists = service.findAll();
//			assertArrayEquals(
//				allPlaylists.toArray()
//				,List.of(
//					playlist
//					,unMainPlaylist
//					,duplicate
//					,playlistsFromSecondUser
//				).toArray()
//			);
		}
		@Test
		@Override
		public void findOnceByIdWithThrowsTest() throws Exception {
//			Exception e;
//			e = assertThrows(Exception.class,() -> service.findOnceById(null));
//				assertEquals(e.getMessage(), PlayListErrors.ID_IS_NULL);
//			e = assertThrows(Exception.class,() -> service.findOnceById(-1l));
//				assertEquals(e.getMessage(), PlayListErrors.ID_LESS_ZERRO);
		}
		@Test
		@Override
		public void findOnceByUserIdAndNameWithThrowsTest() throws Exception {
			Exception e;
			//NullTests
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(null,playlist.getName()));
//				assertEquals(e.getMessage(), PlayListErrors.USERID_IS_NULL);
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(user.getId(),null));
//				assertEquals(e.getMessage(), PlayListErrors.NAME_IS_NULL);
//			//EmptyBlankNameTests
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(user.getId(),""));
//				assertEquals(e.getMessage(), PlayListErrors.NAME_IS_EMPTY);
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(user.getId()," 	"));
//				assertEquals(e.getMessage(), PlayListErrors.NAME_IS_EMPTY);
//			//UserIdLessZerro
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(-1l,playlist.getName()));
//				assertEquals(e.getMessage(), PlayListErrors.USERID_LESS_ZERRO);
//			//UnExistsUserIdOrNameTests
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(user.getId(),"SomeUnExistsName"));
//				assertEquals(e.getMessage(), PlayListErrors.PLAYLISTS_NOT_FOUND_WITH_NAME.formatted("SomeUnExistsName"));
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(123456l,playlist.getName()));
//				assertEquals(e.getMessage(), PlayListErrors.PLAYLISTS_NOT_FOUND_WITH_USER_ID.formatted(123456l));
			//DuplicateTests
//			e = assertThrows(Exception.class,() -> service.findOnceByUserIdAndName(user.getId(),unMainPlaylist.getName()));
//				assertEquals(e.getMessage(), PlayListErrors.DUPLICATED);
		}
		@Test
		@Override
		public void findOnceByUserIdAndMainWithThrowsTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findOnceByUserNameAndMainWithThrowsTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findOnceByAuthAndMainWithThrowsTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findAllByUserIdWithThrowsTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findAllByUsernameWithThrowsTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findAllByUserWithThrowsTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findAllByAuthWithThrowsTest() throws Exception {
			// TODO Auto-generated method stub
			
		}
		@Test
		@Override
		public void findAllWithThrowsTests() throws Exception {
			// TODO Auto-generated method stub
			
		}
	}

}
