package Services.User.Interfaces;

import DAO.User.UserEntity;

public interface UserServiceDelete {
	void deleteByEntity(UserEntity user) throws Exception;
}
