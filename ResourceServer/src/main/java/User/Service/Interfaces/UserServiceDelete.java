package User.Service.Interfaces;

import User.DAO.UserEntity;

public interface UserServiceDelete {
	void deleteByEntity(UserEntity user) throws Exception;
}
