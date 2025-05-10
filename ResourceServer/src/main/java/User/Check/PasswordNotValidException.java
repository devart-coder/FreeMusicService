package User.Check;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PasswordNotValidException extends Exception {
	private String message;
	public PasswordNotValidException(String message) {
		this.message=message;
	}
}
