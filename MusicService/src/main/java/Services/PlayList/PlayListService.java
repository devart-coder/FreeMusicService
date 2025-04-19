package Services.PlayList;

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
import Services.PlayList.Interfaces.PlayListDetails;
import Services.PlayList.Interfaces.PlayListErrors;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PlayListService implements PlayListDetails {
	//TODO::implementsComparableInterface
	@Autowired
	private PlayListsRepository playListRepos;
	
	@Override
	public boolean existsById(Long id) throws Exception {
		if(id == null)
			throw new Exception(PlayListErrors.ID_IS_EMPTY);
		if(id < 0)
			throw new Exception(PlayListErrors.ID_LESS_ZERRO);
		return playListRepos.existsById(id);
	}
	@Override
	public PlayListEntity save(PlayListEntity newPlayList) throws Exception {
		if(newPlayList == null)
			throw new Exception(PlayListErrors.NULL_ARGUMENT);
		return  playListRepos.save(newPlayList);
	}
	@Override
	public List<PlayListEntity> saveAll(Iterable<PlayListEntity> newPlayList) throws Exception {
		if(newPlayList == null)
			throw new Exception(PlayListErrors.NULL_ARGUMENT);
		return playListRepos.saveAll(newPlayList);
	}
	@Override
	public PlayListEntity save(Supplier<? extends PlayListEntity> newPlayList) throws Exception {
		return save(newPlayList.get());
	}
	
	@Override
	public PlayListEntity findOnceById(Long id) throws Exception {
			if(id == null)
				throw new Exception(PlayListErrors.ID_IS_EMPTY);
			if(id < 0)
				throw new Exception(PlayListErrors.ID_LESS_ZERRO);
			return 
				playListRepos
				.findById(id)
				.orElseThrow( () -> new Exception(PlayListErrors.NOT_FOUNT_WITH_ID.formatted( id.toString() )) );
	}
	@Override
	public PlayListEntity findOnceByUserIdAndName(Long userId,String name) throws Exception {
		if(userId == null)
			throw new Exception(PlayListErrors.USERID_IS_EMPTY);
		if(userId < 0)
			throw new Exception(PlayListErrors.USERID_LESS_ZERRO);
		if(name.equals(null))
			throw new Exception(PlayListErrors.NAME_IS_EMPTY);
		//TODO:WhenUserServiceWillBeImplementsNeedAddExistsByUserIdMethod
		return 
			playListRepos
			.findOnceByUserIdAndName(userId,name)
			.orElseThrow( () -> new Exception(PlayListErrors.NOT_FOUNT_WITH_NAME.formatted(name)) );
	}
	@Override
	public List<PlayListEntity> findAllByUser(UserEntity user) throws Exception {
		if(user == null)
			throw new Exception(PlayListErrors.NULL_ARGUMENT);
		return 
			playListRepos
			.findAllByUser(user)
			.orElseThrow( ()-> new Exception(String.format("Any playlist with username '%s' does not exists", user==null?"null":user.getUsername())) );
	}
	@Override
	public List<PlayListEntity> findAllByAuth(Authentication auth) throws Exception {
		//TOTO::ArgsCheckByNull
		return 
			playListRepos
			.findAllByUserUsername(auth.getName())
			.orElseThrow(()->
				new Exception(String.format("Any playlist with username '%s' does not exists", auth.getName()==null?"null":auth.getName())));
	}
	@Override
	public List<PlayListEntity> findAllByUserName(String username) throws Exception {
			return 
				playListRepos
				.findAllByUserUsername(username)
				.orElseThrow( ()-> new Exception(String.format("Any playlist with username '%s' does not exists", username==null?"null":username)) );
	}
	@Override
	public List<PlayListEntity> findAllByUserId(Long id) throws Exception {
			return 
				playListRepos
				.findAllByUserId(id)
				.orElseThrow( ()-> new Exception(String.format("Any playlist with id '%s' does not exists", id==null?"null":id)) );
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
	public List<PlayListEntity> findAll() {
		return  playListRepos.findAll();
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
				throw new Exception("'id' is empty.");
			playListRepos.deleteById(id);
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void deleteByName(String name) throws Exception {
			if(name == null)
				throw new Exception("'name' is empty.");
			playListRepos.deleteByName(name);
	}
	@Override
	public void deleteByIdWithNotMainNotDefaultName(Long id, String name) throws Exception {
				if(id ==null)
					throw new Exception("'id' is empty.");
				if(name == null)
					throw new Exception("'name' is empty.");
				playListRepos.deleteByIdAndMainFalse(id)
				.orElseThrow( ()-> new Exception(String.format("Can't delete playlist with id:%s",id)));
	}
	
	

	

	

}
