package User.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "User not founted")
public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
		super(message);
	}
}
