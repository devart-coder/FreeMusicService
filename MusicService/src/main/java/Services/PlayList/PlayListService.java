package Services.PlayList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

	private void userIdIsValid(Long id) throws Exception {
		if(Objects.isNull(id))
			throw new Exception(PlayListErrors.USERID_IS_NULL);
		if(id < 0)
			throw new Exception(PlayListErrors.USERID_LESS_ZERRO);
	}
	private void idIsValid(Long id) throws Exception {
		if(Objects.isNull(id))
			throw new Exception(PlayListErrors.USERID_IS_NULL);
		if(id < 0)
			throw new Exception(PlayListErrors.USERID_LESS_ZERRO);
	}
	private void nameIsValid(String name) throws Exception {
		if(Objects.isNull(name) )
			throw new Exception(PlayListErrors.NAME_IS_NULL);
		if (name.isEmpty() || name.isBlank())
			throw new Exception(PlayListErrors.NAME_IS_EMPTY);
	}
	private void usernameIsValid(String name) throws Exception {
		if(Objects.isNull(name) )
			throw new Exception(PlayListErrors.NAME_IS_NULL);
		if (name.isEmpty() || name.isBlank())
			throw new Exception(PlayListErrors.NAME_IS_EMPTY);
	}
	private void notNull(Object obj) throws Exception {
		if(Objects.isNull(obj))
			throw new Exception(PlayListErrors.NULL_ARGUMENT);
	}
	
	@Override
	public boolean existsById(Long id) throws Exception {
		idIsValid(id);
		return playListRepos.existsById(id);
	}
	@Override
	public PlayListEntity save(PlayListEntity newPlayList) throws Exception {
		notNull(newPlayList);
		return  playListRepos.save(newPlayList);
	}
	@Override
	public List<PlayListEntity> saveAll(Iterable<PlayListEntity> newPlayList) throws Exception {
		notNull(newPlayList);
		return playListRepos.saveAll(newPlayList);
	}
	@Override
	public PlayListEntity save(Supplier<? extends PlayListEntity> newPlayList) throws Exception {
		return save(newPlayList.get());
	}
	@Override
	public PlayListEntity findOnceById(Long id) throws Exception {
		idIsValid(id);	
		return 
			playListRepos
			.findById(id)
			.orElseThrow( () -> new Exception(PlayListErrors.PLAYLIST_NOT_FOUND_WITH_ID.formatted( id.toString() )) );
	}
//	@Override
//	public List<PlayListEntity> findAllByUser(UserEntity user) throws Exception {
//		notNull(user);
//		return 
////			playListRepos
////			.findAllByUser(user)
//
//			.orElseThrow( ()-> new Exception( PlayListErrors.PLAYLISTS_NOT_FOUND_WITH_USER_ID.formatted(user.getId()) ) );
//	}
//	@Override
//	public List<PlayListEntity> findAllByAuth(Authentication auth) throws Exception {
//		notNull(auth);
//		return 
//			playListRepos
//			.findAllByUserUsername(auth.getName())
//			//TODO:ReplaseUserNameToUserId;
//			.orElseThrow(()->
//				new Exception(String.format("Any playlist with username '%s' does not exists", auth.getName()==null?"null":auth.getName())));
//	}
	@Override
	public List<PlayListEntity> findAll() {
		return  playListRepos.findAll();
	}
	
	@Override
	public void updateNameByEntity(String newName, PlayListEntity playlist) {
		updateNameById(newName,playlist.getId());
	}
	@Override
	public void updateMainByEntity(Boolean newMain, PlayListEntity playlist) throws Exception {
		playlist.setMain(newMain);
		updateMainById(newMain,playlist.getId());
	}
	@Override
	public void updateSizeByEntity(Long newSize, PlayListEntity playlist) {
		updateSizeById(newSize,playlist.getId());
	}
	@Override
	public void updateNameById(String newName, Long playlistId) {
		try {
			playListRepos.updateNameById(newName, playlistId);
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void updateMainById(Boolean newMain, Long playlistId) {
		try {
			playListRepos.updateMainById(newMain, playlistId);
		}catch(Exception e) {
			log.error(e.getMessage());
		}	
	}
	@Override
	public void updateSizeById(Long newSize, Long playlistId) {
		try {
			playListRepos.updateSizeById(newSize, playlistId);
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void updateMainByName(Boolean newMain, String name) {
		try {
			playListRepos.updateMainByName(newMain, name);
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	@Override
	public void updateSizeByName(Long newSize, String name) {
		try {
			playListRepos.updateSizeByName(newSize, name);
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
	@Override
	public PlayListEntity findOnceMainPlaylist(UserEntity user) throws Exception {
		notNull(user);
		var playlists = user.getPlaylists();
		if( ((List<PlayListEntity>)user.getPlaylists())
				.stream()
				.filter(p->p.getMain()==true)
				.count()>1 ) 
			throw new Exception(PlayListErrors.DUPLICATED);
		return 
			((List<PlayListEntity>)user.getPlaylists())
			.stream()
			.filter(p->p.getMain()==true)
			.findFirst()
			.orElseThrow(()->new Exception(PlayListErrors.MAIN_PLAYLIST_NOT_FOUND));//TODO::AddExceptionA
	}
//	@Override
//	public PlayListEntity findMainPlayListAndSetDown(UserEntity user) throws Exception {
//
//		// TODO Auto-generated method stub
//		return null;
//	}
	@Override
	public List<PlayListEntity> findAllByUserId(UserEntity user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PlayListEntity findAndSetMain(UserEntity user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PlayListEntity findOncePlaylist(UserEntity user) throws Exception {
		notNull(user);
		var playlistOption = ((List<PlayListEntity>)user.getPlaylists())
			.stream()
			.filter(p->p.getMain()==true)
			.findFirst();
		return null;
	}
	
	

	

	

}
