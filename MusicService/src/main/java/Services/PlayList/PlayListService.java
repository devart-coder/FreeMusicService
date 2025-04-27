package Services.PlayList;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;
import Repositories.PlayListsRepository;
import Services.PlayList.Exceptions.DuplicatePlaylistsException;
import Services.PlayList.Exceptions.PlayListNotFoundException;
import Services.PlayList.Interfaces.PlayListDetails;
import Services.PlayList.Interfaces.PlayListErrors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlayListService implements PlayListDetails {
	//TODO::implementsComparableInterface
	@Autowired
	private PlayListsRepository playListRepos;
	private UserEntity user;
	//PrivateCheckers
	private void stringIsValid(String name) throws Exception {
		if(Objects.isNull(name) )
			throw new Exception(PlayListErrors.NAME_IS_NULL);
		if (name.isEmpty() || name.isBlank())
			throw new Exception(PlayListErrors.NAME_IS_EMPTY);
	}
	private void notNull(Object obj) throws Exception {
		if(Objects.isNull(obj))
			throw new Exception(PlayListErrors.NULL_ARGUMENT);
	}
	private Stream<PlayListEntity> mainPlaylistStream (UserEntity user) {
		return 
			user
			.getPlaylists()
			.stream()
			.filter(p->p.getMain()==true);
	}
	//Create
	//TODO::NeedDeleteExceptionsFrom'Save'Methods?
	@Override
 	public PlayListEntity save(PlayListEntity newPlayList) throws Exception {
		notNull(newPlayList);
		return  playListRepos.save(newPlayList);
	}
	@Override
	public PlayListEntity save(Supplier<? extends PlayListEntity> newPlayList) throws Exception {
		return save(newPlayList.get());
	}
	@Override
	public List<PlayListEntity> saveAll(Iterable<PlayListEntity> newPlayList) throws Exception {
		notNull(newPlayList);
		//TODO::AddContainCheckByNull
		return playListRepos.saveAll(newPlayList);
	}
	//Search
	@Override
	public PlayListEntity findOnceUserPlaylist(String playlistName) throws Exception {
		notNull(user);

		return null;
	}
	@Override
	public PlayListEntity findOnceUserMainPlaylist(UserEntity user) 
			throws Exception,DuplicatePlaylistsException,PlayListNotFoundException {
		notNull(user);
		if(mainPlaylistStream(user).count() > 1 )
			throw new DuplicatePlaylistsException(PlayListErrors.DUPLICATED);
		return 
			mainPlaylistStream(user)
			.findFirst()
			.orElseThrow(()->new PlayListNotFoundException(PlayListErrors.MAIN_PLAYLIST_NOT_FOUND));//TODO::ADDExceptionMessage
	}
	public List<PlayListEntity> findAll() {
		return  playListRepos.findAll();
	}
	@Override
	public List<PlayListEntity> findAllUserPlaylists() throws Exception {
		return user.getPlaylists();
	}
	//Update
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
		notNull(user);
		stringIsValid(mainButton);
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
		notNull(user);
		this.user=user;
		return user;
	}
	@Override
	public void deleteById(Long deleteButton) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void  setOnlyDefaultPlayListAsMain(UserEntity user) throws Exception,PlayListNotFoundException {
		//TODO:MakeCheckArgByNull
		notNull(user);
		var def = user.getPlaylists()
			.stream()
			.filter(p->p.getName().equals(PlayListDetails.DEFAULT_NAME))
			.findFirst()
			.orElseThrow(()->new PlayListNotFoundException("'%s' not found".formatted(PlayListDetails.DEFAULT_NAME)));
			updateMainByEntity(true, def);

		user.getPlaylists().stream().forEach(p->{
			//TODO:RemakeThis
			if(!p.getName().equals(PlayListDetails.DEFAULT_NAME))
				try {
					updateMainByEntity(false, p);
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
	}
}
