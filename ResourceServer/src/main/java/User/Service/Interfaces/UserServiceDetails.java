package User.Service.Interfaces;

import Playlist.Exceptions.PlaylistNameIsNotValidException;
import User.DAO.UserEntity;
import User.Exceptions.PasswordNotValidException;
import User.Exceptions.UsernameNotValidException;

//TODO::Rename'UserserviceDetails'
public interface UserServiceDetails extends UserServiceCreate, UserServiceSearch, UserServiceUpdate, UserServiceDelete{
	UserEntity update(UserEntity user)throws UsernameNotValidException, PasswordNotValidException, PlaylistNameIsNotValidException;
}
