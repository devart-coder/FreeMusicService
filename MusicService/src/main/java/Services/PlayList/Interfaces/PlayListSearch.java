package Services.PlayList.Interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;

public interface PlayListSearch {
//Search/Read
	//All
		List<PlayListEntity> findAll();
		List<PlayListEntity> findAllUserPlaylists() throws Exception;
	//Once
		PlayListEntity findOnceUserMainPlaylist(UserEntity user) throws Exception;
		PlayListEntity findOnceUserPlaylist(String playlistName) throws Exception;

}
