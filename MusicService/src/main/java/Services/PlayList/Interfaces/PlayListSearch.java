package Services.PlayList.Interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;

public interface PlayListSearch {
//Search/Read
	//All
		List<PlayListEntity> findAll();
		List<PlayListEntity> findAllByUserId(UserEntity user) throws Exception;
	//Once
		PlayListEntity findOnceById(Long Id) throws Exception;
		PlayListEntity findOnceMainPlaylist(UserEntity user) throws Exception;
		PlayListEntity findOncePlaylist(UserEntity user) throws Exception;
		PlayListEntity findAndSetMain(UserEntity user) throws Exception;

}
