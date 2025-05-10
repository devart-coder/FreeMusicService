package User.Exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class UserExceptions extends Exception {
	protected HttpStatus status;
	protected String message;
	public UserExceptions(HttpStatus status,String message) {
		this.status=status;
		this.message=message;
	}
}
