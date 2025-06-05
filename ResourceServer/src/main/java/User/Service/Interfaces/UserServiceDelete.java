package User.Service.Interfaces;

import User.DAO.UserEntity;

public interface UserServiceDelete {
	void remove(UserEntity user)throws Exception;
}
