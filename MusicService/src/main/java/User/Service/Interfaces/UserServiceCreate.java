package User.Service.Interfaces;

import User.DAO.UserEntity;

public interface UserServiceCreate {
	UserEntity save(UserEntity user) throws Exception;
}
