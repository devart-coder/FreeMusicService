package RestAPI.API.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import User.DAO.UserEntity;
import User.Exceptions.UserDuplicateException;

public interface UserAPICreation {
	public UserEntity userCreationWithBody( @RequestBody UserEntity user) throws UserDuplicateException;
}
