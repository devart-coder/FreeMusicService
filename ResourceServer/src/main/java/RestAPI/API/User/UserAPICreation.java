package RestAPI.API.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import User.DAO.UserEntity;

public interface UserAPICreation {
	public ResponseEntity<?> userCreationWithBody( @RequestBody UserEntity user);
	public ResponseEntity<?> userCreationWithParams( @RequestParam String username, @RequestParam String password);
}
