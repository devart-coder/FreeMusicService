package User.Service.Interfaces;

import User.DAO.UserEntity;
import User.Exceptions.UserNotFoundException;

public interface UserServiceSearch {
	Iterable<UserEntity> findAll();
	UserEntity findOnceById(Long id) throws UserNotFoundException;
	UserEntity findOnceByName(String username) throws UserNotFoundException, Exception;
}
