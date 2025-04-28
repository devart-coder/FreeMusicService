package Services.PlayList;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import Checkers.Playlist.PlaylistCheck;
import Checkers.Shared.SharedCheck;
import DAO.PlayList.PlayListBuilder;
import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;
import Repositories.PlayListsRepository;
import Services.PlayList.Exceptions.DuplicatePlaylistsException;
import Services.PlayList.Exceptions.PlayListErrors;
import Services.PlayList.Exceptions.PlayListNotFoundException;
import Services.PlayList.Interfaces.PlayListDetails;
import Services.User.Interfaces.UserServiceDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlayListService implements PlayListDetails {
	//TODO::implementsComparableInterface
	@Autowired
	private PlayListsRepository playListRepos;
	@Autowired
	private UserServiceDetails userService;
	private UserEntity user;

	private Stream<PlayListEntity> mainPlaylistStream (UserEntity user) {
		return 
			user
			.getPlaylists()
			.stream()
			.filter(p->p.getMain()==true);
	}
	private PlayListEntity defaultPlaylistStream (UserEntity user) {
		try {
			return 
				user
				.getPlaylists()
				.stream()
				.filter(p->p.getName().equals(PlayListDetails.DEFAULT_NAME))
				.findFirst()
				.orElseThrow(() -> new PlayListNotFoundException("'%s' not found".formatted(PlayListDetails.DEFAULT_NAME)));
		}catch(PlayListNotFoundException e) {
			log.warn(e.getMessage());
		}
		return null;
	}
	//Create
	//TODO::NeedDeleteExceptionsFrom'Save'Methods?
	@Override
 	public PlayListEntity save(PlayListEntity newPlayList) throws Exception {
		SharedCheck.notNull(newPlayList);
		return  playListRepos.save(newPlayList);
	}
	@Override
	public PlayListEntity save(Supplier<? extends PlayListEntity> newPlayList) throws Exception {
		return save(newPlayList.get());
	}
	@Override
	public List<PlayListEntity> saveAll(Iterable<PlayListEntity> newPlayList) throws Exception {
		SharedCheck.notNull(newPlayList);
		//TODO::AddContainCheckByNull
		return playListRepos.saveAll(newPlayList);
	}
	//Search
	@Override
	public PlayListEntity findOnceUserPlaylist(String playlistName){
		SharedCheck.notNull(user);

		return null;
	}
	@Override
	public PlayListEntity findOnceUserMainPlaylist(UserEntity user) { 
		SharedCheck.notNull(user);
		try {
			if(mainPlaylistStream(user).count() > 1 )
				throw new DuplicatePlaylistsException(PlayListErrors.DUPLICATED);
			return 
				mainPlaylistStream(user)
				.findFirst()
				.orElseThrow(() -> new PlayListNotFoundException(PlayListErrors.MAIN_PLAYLIST_NOT_FOUND));
			
		}catch (DuplicatePlaylistsException e) {
			log.error(e.getMessage());
			setOnlyDefaultPlayListAsMain(user);
		}catch(PlayListNotFoundException e) {
			log.error(e.getMessage());
			var p = PlayListBuilder.defaultPlaylist();
			p.setUser(user);
			return playListRepos.save(p);
		}
		return null;
	}
	public List<PlayListEntity> findAll() {
		return  playListRepos.findAll();
	}
	@Override
	public List<PlayListEntity> findAllUserPlaylists(){
		return user.getPlaylists();
	}
	//Update
	@Override
	public void updateNameByEntity(String newName, PlayListEntity playlist) {
		playlist.setName(newName);
		updateNameById(newName,playlist.getId());
	}
	@Override
	public void updateMainByEntity(Boolean newMain, PlayListEntity playlist) {
		playlist.setMain(newMain);
		updateMainById(newMain,playlist.getId());
	}
	@Override
	public void updateSizeByEntity(Long newSize, PlayListEntity playlist) {
		playlist.setSize(newSize);
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
	//Delete
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
	//Other
	@Override
	public void setNewMain(String mainButton) throws Exception {
		SharedCheck.notNull(user);
		PlaylistCheck.nameIsValid(mainButton);
		var p = findOnceUserMainPlaylist(user);
		updateMainByEntity(false, p);
			p = user
				.getPlaylists()
				.stream()
				.filter(pl->pl.getName().equals(mainButton))
				.findFirst()
				.orElseThrow(()->new Exception(PlayListErrors.PLAYLISTS_NOT_FOUND_WITH_NAME.formatted(mainButton)));
		updateMainByEntity(true, p);
	}
	@Override
	public UserEntity setUser(UserEntity user) throws Exception {
		SharedCheck.notNull(user);
		this.user=user;
		return user;
	}
	@Override
	public void deleteById(Long deleteButton) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void  setOnlyDefaultPlayListAsMain(UserEntity user) {
		SharedCheck.notNull(user);
		var defaultPlaylist = defaultPlaylistStream(user);
		
		updateMainByEntity(true, defaultPlaylist);
		
		user.getPlaylists()
			.stream()
			.forEach(p -> {
				if(!p.getName().equals(PlayListDetails.DEFAULT_NAME))
					updateMainByEntity(false, p);
				}
			);
	}
}
