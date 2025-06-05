package User.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UsernameNotValidException extends Exception{
	private String message;
	public UsernameNotValidException(String mess) {
		this.message=mess;
	}
}
