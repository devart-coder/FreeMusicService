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
import org.springframework.boot.context.properties.source.MutuallyExclusiveConfigurationPropertiesException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlayListService implements PlayListDetails {
	//TODO::implementsComparableInterface
	@Autowired
	private PlayListsRepository playListRepos;
	private UserEntity user;
	
	
	private void idIsValid(Long id) throws Exception {
		if(Objects.isNull(id))
			throw new Exception(PlayListErrors.USERID_IS_NULL);
		if(id < 0)
			throw new Exception(PlayListErrors.USERID_LESS_ZERRO);
	}
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
	//Create
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
		return playListRepos.saveAll(newPlayList);
	}
	//Search
	@Override
	public PlayListEntity findOnceUserPlaylist(String playlistName) throws Exception {
		notNull(user);
		return null;
	}
	public List<PlayListEntity> findAll() {
		return  playListRepos.findAll();
	}
	@Override
	public List<PlayListEntity> findAllUserPlaylists() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PlayListEntity findOnceUserMainPlaylist(UserEntity user) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
}
