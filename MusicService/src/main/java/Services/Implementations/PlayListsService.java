package Services.Implementations;

import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import DAO.PlayLists.PlayListBuilder;
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
@Component
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
				.orElseThrow( () -> new Exception(String.format("Playlist with id '%s' not found.", Id==null ? "null":Id)) );
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
		try {
			return 
				playListRepos
				.findAllByUser(user)
				.orElseThrow( ()-> new Exception(String.format("Any playlist with username '%s' not exists", user==null?"null":user.getUsername())) );
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<PlayListEntity> findAllByUserName(String username) {
		try {
			return 
				playListRepos
				.findAllByUserUsername(username)
				.orElseThrow( ()-> new Exception(String.format("Any playlist with username '%s' not exists", username==null?"null":username)) );
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<PlayListEntity> findAllByUserId(Long id) {
		try {
			return 
				playListRepos
				.findAllByUserId(id)
				.orElseThrow( ()-> new Exception(String.format("Any playlist with id '%s' not exists", id==null?"null":id)) );
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public void deleteById(Long Id) {
		try {
			if(Id == null)
				throw new Exception("PlaylistId is null.");
			playListRepos.deleteById(Id);
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void deleteByName(String name) {
		try{
			if(name == null)
				throw new Exception("'name' is null");
			playListRepos.deleteByName(name);
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void delete(PlayListEntity playlist) {
		// TODO Auto-generated method stub
		try{
			if(playlist == null)
				throw new Exception("Reference 'PlayListEntity' is null");
			playListRepos.delete(playlist);
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public PlayListEntity findByUserIdAndMain(Long userid , Boolean main) {
		var m = playListRepos.findByUserIdAndMain(userid,main);
		return m.orElseGet(()->PlayListBuilder.defaultPlaylist());
	}
	@Override
	public PlayListEntity findByUserNameAndMain(String username , Boolean main) {
		var m = playListRepos.findByUserUsernameAndMain(username,main);
		return m.orElseGet(()->PlayListBuilder.defaultPlaylist());
	}

	@Override
	public List<PlayListEntity> findAllByAuth(Authentication auth) {
		try {
		return 
			playListRepos
			.findAllByUserUsername(auth.getName())
			.orElseThrow(()-> new Exception(String.format("Any playlist with username '%s' not exists", auth.getName()==null?"null":auth.getName())));
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

}
