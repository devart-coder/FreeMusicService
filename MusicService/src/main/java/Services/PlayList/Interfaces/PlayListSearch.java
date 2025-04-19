package Services.PlayList.Interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;

public interface PlayListSearch {
//Search/Read
	//All
		List<PlayListEntity> findAll();
		List<PlayListEntity> findAllByUserId(Long id) throws Exception;
		List<PlayListEntity> findAllByUsername(String name) throws Exception;
		List<PlayListEntity> findAllByUser(UserEntity user) throws Exception;
		List<PlayListEntity> findAllByAuth(Authentication auth) throws Exception;
	//Once
		PlayListEntity findOnceById(Long Id) throws Exception;
		PlayListEntity findOnceByUserIdAndName(Long userId,String name) throws Exception;
		PlayListEntity findOnceByUsernameAndMain(String username, Boolean main);
		PlayListEntity findOnceByUserIdAndMain(Long userid, Boolean main);

}
