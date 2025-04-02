package Services.Interfaces;

import java.util.List;
import java.util.Optional;

import Entities.PlayListEntity;
import Entities.UserEntity;
import Services.Implementations.UserService;

public interface PlayListsDetails {
	//Create
	void save(PlayListEntity newPlayList);

	//Read
	PlayListEntity findById(Long Id);
	PlayListEntity findByName(String name);
	List<PlayListEntity> findAllByUserId(Long id);
	List<PlayListEntity> findAllByUserName(String name);
	List<PlayListEntity> findAllByUser(UserEntity user);
	
	//Update
	void updateName(String newName);
	void updateMain(boolean newMain);
	void updateSize(Long newSize);
	
	//Delete
	void deleteById(Long Id);
	void deleteByName(String name);

}
