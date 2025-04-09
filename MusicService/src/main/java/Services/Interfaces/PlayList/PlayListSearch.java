package Services.Interfaces.PlayList;

import java.util.List;

import org.springframework.security.core.Authentication;

import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;

public interface PlayListSearch {
//Search/Read
	//All
		List<PlayListEntity> findAllByUserId(Long id);
		List<PlayListEntity> findAllByUserName(String name);
		List<PlayListEntity> findAllByUser(UserEntity user);
		List<PlayListEntity> findAllByAuth(Authentication auth);
	//Once
		PlayListEntity findOnceById(Long Id);
		PlayListEntity findOnceByName(String name);
		PlayListEntity findOnceByUserNameAndMain(String username, Boolean main);
		PlayListEntity findOnceByUserIdAndMain(Long userid, Boolean main);

}
