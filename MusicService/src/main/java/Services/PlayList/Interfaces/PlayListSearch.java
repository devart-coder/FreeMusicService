package Services.PlayList.Interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;

public interface PlayListSearch {
//Search
	//All
		List<PlayListEntity> findAll();
		List<PlayListEntity> findAllUserPlaylists();
	//Once
		PlayListEntity findOnceUserMainPlaylist(UserEntity user);
		PlayListEntity findOnceUserPlaylist(String playlistName);
}
