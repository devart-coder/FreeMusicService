package Services.Interfaces;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.security.core.Authentication;

import DAO.PlayLists.PlayListEntity;
import DAO.User.UserEntity;

public interface PlayListsDetails {
	//Create
		void save(PlayListEntity newPlayList);
		void save(Supplier<? extends PlayListEntity> newPlayList);
	//Search/Read
	//All
		List<PlayListEntity> findAllByUserId(Long id);
		List<PlayListEntity> findAllByUserName(String name);
		List<PlayListEntity> findAllByUser(UserEntity user);
		List<PlayListEntity> findAllByAuth(Authentication auth);
	//Once
		PlayListEntity findOnceById(Long Id);
		PlayListEntity findOnceByName(String name);
		PlayListEntity findOnceByUserNameAndMain(String username, Boolean main);
		PlayListEntity findOnceByUserIdAndMain(Long userid, Boolean main);
	//Update
	//ByEntity
		void updateNameBy(PlayListEntity playlist,String newName);
		void updateMainBy(PlayListEntity playlist,Boolean newMain);
		void updateSizeBy(PlayListEntity playlist,Long newSize);
	//ById 
		void updateNameById(Long playlist,String newName);
		void updateMainById(Long playlist,Boolean newMain);
		void updateSizeById(Long playlist,Long newSize);
	//ByName
		void updateMainByName(String name,Boolean newMain);
		void updateSizeByName(String name,Long newSize);
	
	//Delete
		void delete(PlayListEntity playlist);
		void delete(Supplier<? extends PlayListEntity> playlist);
		void deleteById(Long Id);
		void deleteByName(String name);


}
