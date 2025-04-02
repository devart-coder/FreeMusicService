package Services.Interfaces;

import java.util.List;
import java.util.Optional;

import Entities.PlayListEntity;

public interface PlayListsDetails {
	//Create
	void save(PlayListEntity newPlayList);

	//Read
	Optional<PlayListEntity> findById(Long Id);
	Optional<PlayListEntity> findByName(String name);
	List<PlayListEntity> findAllByUserName(String name);
	List<PlayListEntity> findAllByUseId(Long id);
	
	//Update
	void updateName(String newName);
	void updateMain(boolean newMain);
	void updateSize(Long newSize);
	
	//Delete
	void deleteById(Long Id);
	void deleteByName(String name);
}
