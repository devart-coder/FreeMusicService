package User.Exceptions;

import org.springframework.http.HttpStatus;

public class UserDuplicateException extends Exception {
	public UserDuplicateException( String message) {
		super(message);
	}
}
