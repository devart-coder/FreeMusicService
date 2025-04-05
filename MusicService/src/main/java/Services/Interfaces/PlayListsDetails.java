package Services.Interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import DAO.PlayLists.PlayListEntity;
import DAO.User.UserEntity;

public interface PlayListsDetails {
	//Create
	void save(PlayListEntity newPlayList);

	//Read
	PlayListEntity findById(Long Id);
	PlayListEntity findByName(String name);
	List<PlayListEntity> findAllByUserId(Long id);
	List<PlayListEntity> findAllByUserName(String name);
	List<PlayListEntity> findAllByUser(UserEntity user);
	List<PlayListEntity> findAllByAuth(Authentication auth);
	
	//Update
	
	//Delete
	void deleteById(Long Id);
	void delete(PlayListEntity playlist);
	void deleteByName(String name);

	PlayListEntity findByUserNameAndMain(String username, Boolean main);

	PlayListEntity findByUserIdAndMain(Long userid, Boolean main);

}
