package Services.Implementations;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.naming.NameNotFoundException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import DAO.PlayList.PlayListBuilder;
import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;
import Repositories.PlayListsRepository;
import Services.Interfaces.PlayList.PlayListDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Component
public class PlayListService implements PlayListDetails {
	@Autowired
	private PlayListsRepository playListRepos;
	
	@Override
	public PlayListEntity save(PlayListEntity newPlayList) throws Exception {
		if(newPlayList == null)
			throw new Exception("'Playlist' was not saved.");
		return  playListRepos.save(newPlayList);
	}
	@Override
	public List<PlayListEntity> saveAll(Iterable<PlayListEntity> newPlayList) throws Exception {
		if(newPlayList == null)
			throw new Exception("'Playlist' was not saved.");
		return playListRepos.saveAll(newPlayList);
	}
	@Override
	public PlayListEntity save(Supplier<? extends PlayListEntity> newPlayList) throws Exception {
		return save(newPlayList.get());
	}
	
	@Override
	public PlayListEntity findOnceById(Long Id) throws Exception {
			if(Id == null)
				throw new Exception("Id is empty.");
			return 
				playListRepos
				.findById(Id)
				.orElseThrow( () -> new Exception(String.format("Playlist with id '%s' was not found.", Id.toString())) );
	}
	@Override
	public PlayListEntity findOnceByName(String name) {
		try {
			return 
				playListRepos
				.findOnceByName(name)
				.orElseThrow( () -> new Exception(String.format("Playlist with name '%s' was not found.", name)) );
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
				.orElseThrow( ()-> new Exception(String.format("Any playlist with username '%s' does not exists", user==null?"null":user.getUsername())) );
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	@Override
	public List<PlayListEntity> findAllByAuth(Authentication auth) {
		try {
		return 
			playListRepos
			.findAllByUserUsername(auth.getName())
			.orElseThrow(()-> new Exception(String.format("Any playlist with username '%s' does not exists", auth.getName()==null?"null":auth.getName())));
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
				.orElseThrow( ()-> new Exception(String.format("Any playlist with username '%s' does not exists", username==null?"null":username)) );
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
				.orElseThrow( ()-> new Exception(String.format("Any playlist with id '%s' does not exists", id==null?"null":id)) );
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	@Override
	public PlayListEntity findOnceByUserIdAndMain(Long userid , Boolean main) {
		var m = playListRepos.findOnceByUserIdAndMain(userid,main);
		return m.orElseGet(()->PlayListBuilder.defaultPlaylist());
	}
	public PlayListEntity findOnceByAuthAndMain(Authentication auth, boolean b) {
		return findOnceByUserNameAndMain(auth.getName(), b);
	}
	@Override
	public PlayListEntity findOnceByUserNameAndMain(String username , Boolean main) {
		var m = playListRepos.findOnceByUserUsernameAndMain(username,main);
		return m.orElseGet(()->PlayListBuilder.defaultPlaylist());
	}
	
	@Override
	public void updateNameByEntity(String newName, PlayListEntity playlist) {
		updateNameById(newName,playlist.getId());
	}
	@Override
	public void updateMainByEntity(Boolean newMain, PlayListEntity playlist) {
		updateMainById(newMain,playlist.getId());
	}
	@Override
	public void updateSizeByEntity(Long newSize, PlayListEntity playlist) {
		updateSizeById(newSize,playlist.getId());
	}
	@Override
	public void updateNameById(String newName, Long playlistId) {
		try {
			playListRepos.updateNameById(newName, playlistId)
			.orElseThrow(()-> new Exception("Field 'name' of Playlist was not updated."));
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void updateMainById(Boolean newMain, Long playlistId) {
		try {
			playListRepos.updateMainById(newMain, playlistId)
			.orElseThrow(()-> new Exception("Field 'main' of Playlist was not updated."));
		}catch(Exception e) {
			log.error(e.getMessage());
		}	
	}
	@Override
	public void updateSizeById(Long newSize, Long playlistId) {
		try {
			playListRepos.updateSizeById(newSize, playlistId)
			.orElseThrow(()-> new Exception("Field 'main' of Playlist was not updated."));
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void updateMainByName(Boolean newMain, String name) {
		try {
			playListRepos.updateMainByName(newMain, name)
			.orElseThrow(()-> new Exception("Field 'main' of Playlist was not updated."));
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void updateSizeByName(Long newSize, String name) {
		try {
			playListRepos.updateSizeByName(newSize, name)
			.orElseThrow(()-> new Exception("Field 'size' of Playlist was not updated."));
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public void delete(PlayListEntity playlist) {
		try{
			if(playlist == null)
				throw new Exception(String.format("'PlayListEntity' is '%s'.",playlist ==null ? "null":playlist.getName()));
			playListRepos.delete(playlist);
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void delete(Supplier<? extends PlayListEntity> playlist) {
		playListRepos.delete(playlist.get());
	}
	@Override
	public void deleteById(Long id) {
		try {
			if(id == null)
				throw new Exception(String.format("'id' is '%s'.",id ==null ? "null":id));
			playListRepos.deleteById(id);
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void deleteByName(String name) {
		try{
			if(name == null)
				throw new Exception(String.format("'name' is '%s'.",name ==null ? "null":name));
			playListRepos.deleteByName(name);
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void deleteByIdWithNotMainNotDefaultName(Long id, String name) {
		//TODO:RenameDeleteMethosByExceptions
		try {
			playListRepos.deleteByIdAndMainFalse(id)
			.orElseThrow( ()-> new Exception("Something Wrong") );
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}

	

	

}
