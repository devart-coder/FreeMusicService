package Services.User.Interfaces;

import DAO.User.UserEntity;

public interface UserServiceCreate {
	UserEntity save(UserEntity user) throws Exception;
}
