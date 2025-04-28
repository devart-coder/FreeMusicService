package Services.User.Interfaces;

import DAO.User.UserEntity;
import Services.User.Exceptions.UserNotFoundException;

public interface UserServiceSearch {
	Iterable<UserEntity> findAll();
	UserEntity findOnceById(Long id) throws UserNotFoundException;
	UserEntity findOnceByName(String username) throws UserNotFoundException, Exception;
}
