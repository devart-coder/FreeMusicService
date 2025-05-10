package User.Exceptions;

import org.springframework.http.HttpStatus;

public class UserDuplicateException extends UserExceptions {

	public UserDuplicateException(HttpStatus status, String message) {
		super(status, message);
	}

}
