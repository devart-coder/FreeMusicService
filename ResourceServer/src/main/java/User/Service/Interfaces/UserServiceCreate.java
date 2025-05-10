package User.Service.Interfaces;

import User.DAO.UserEntity;

public interface UserServiceCreate {
	UserEntity create(UserEntity user) throws Exception;
}
