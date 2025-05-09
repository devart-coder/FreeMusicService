package User.Service.Interfaces;

import User.DAO.UserEntity;

public interface UserServiceCreate {
	UserEntity add(UserEntity user) throws Exception;
}
