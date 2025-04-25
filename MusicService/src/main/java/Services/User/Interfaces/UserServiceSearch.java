package Services.User.Interfaces;

import DAO.User.UserEntity;

public interface UserServiceSearch {
	Iterable<UserEntity> findAll() throws Exception;
	UserEntity findOnceById(Long id) throws Exception;
	UserEntity findOnceByName(String username) throws Exception;
}
