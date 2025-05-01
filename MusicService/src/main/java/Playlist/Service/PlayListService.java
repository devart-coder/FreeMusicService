package Playlist.Service;

import java.util.List;
import java.util.function.Supplier;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Playlist.Check.PlaylistCheck;
import Playlist.DAO.PlayListEntity;
import Playlist.Repository.PlayListsRepository;
import Playlist.Service.Interfaces.PlayListDetails;
import User.DAO.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlayListService implements PlayListDetails {
	@Autowired
	private PlayListsRepository playListRepos;

	//Create
	@Override
 	public PlayListEntity save(PlayListEntity newPlayList) 
 			throws Exception {
		PlaylistCheck.notNull(newPlayList);
		//TODO::AddCheckOnExists
		return  playListRepos.save(newPlayList);
	}
	@Override
	public PlayListEntity save(Supplier<? extends PlayListEntity> newPlayList) 
			throws Exception {
		return save(newPlayList.get());
	}
	@Override
	public List<PlayListEntity> saveAll(Iterable<PlayListEntity> newPlayList)
			throws Exception {
		PlaylistCheck.notNull(newPlayList);
		//TODO::AddContainCheckByNull
		return playListRepos.saveAll(newPlayList);
	}
	
	//Find
	@Override
	public List<PlayListEntity> findAll() {
		return  playListRepos.findAll();
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
		if(PlaylistCheck.isNull(playlist))
			return;
		playListRepos.delete(playlist);
	}
	@Override
	public void deleteById(Long id) {
		if(PlaylistCheck.idIsValid(id))
			playListRepos.deleteById(id);
	}
	@Override
	public <T extends UserEntity > Wrapper<T> withUser(T user) {
		return new Wrapper<T>(this,user);
	}
}