package Services.Implementations;

import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import DAO.PlayLists.PlayListEntity;
import DAO.User.UserEntity;
import Repositories.PlayListsRepository;
import Services.Interfaces.PlayListsDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PlayListsService implements PlayListsDetails {
	@Autowired
	private PlayListsRepository playListRepos;
	
	@Override
	public void save(PlayListEntity newPlayList) {
		try {
			var result = playListRepos.save(newPlayList);
			if(result == null)
				throw new Exception("PlayList was't save.");
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public PlayListEntity findById(Long Id) {
		try {
			return 
				playListRepos
				.findById(Id)
				.orElseThrow( () -> new Exception(String.format("Playlist with id '%s' not found.", Id)) );
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public PlayListEntity findByName(String name) {
		try {
		return 
			playListRepos
			.findByName(name)
			.orElseThrow( () -> new Exception(String.format("Playlist with name '%s' not found.", name)) );
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<PlayListEntity> findAllByUser(UserEntity user) {
		return playListRepos.findAllByUser(user);
	}
	
	@Override
	public List<PlayListEntity> findAllByUserName(String name) {
		return playListRepos.findAllByUserUsername(name);
	}

	@Override
	public List<PlayListEntity> findAllByUserId(Long id) {
		return playListRepos.findAllByUserId(id);
	}

	@Override
	public void updateName(String newName) {
		
	}

	@Override
	public void updateMain(boolean newMain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSize(Long newSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long Id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteByName(String name) {
		// TODO Auto-generated method stub

	}

	

}
