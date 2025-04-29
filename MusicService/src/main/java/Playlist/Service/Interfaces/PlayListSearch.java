package Playlist.Service.Interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import Playlist.DAO.PlayListEntity;
import User.DAO.UserEntity;


public interface PlayListSearch {
//Search
	//All
		List<PlayListEntity> findAll();
		List<PlayListEntity> findAllUserPlaylists();
	//Once
		PlayListEntity findOnceUserMainPlaylist(UserEntity user);
		PlayListEntity findOnceUserPlaylist(String playlistName);
}
