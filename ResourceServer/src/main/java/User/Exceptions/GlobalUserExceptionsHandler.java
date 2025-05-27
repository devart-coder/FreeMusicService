package User.Exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalUserExceptionsHandler {
	private final String ERROR_MESSAGE = "ErrorMessage";
	private ResponseEntity<Map<String,String>> local ( HttpStatus status, Exception u ){
		log.error(ERROR_MESSAGE,u.getMessage());
		return ResponseEntity
				.status(status)
				.body( Map.of( ERROR_MESSAGE,u.getMessage() ) );
	} 
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchUserDuplicateException(UserDuplicateException u){
		return local(HttpStatus.NOT_ACCEPTABLE,u);
	}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchUsernameNotValidException(UsernameNotValidException u){
		return local(HttpStatus.NOT_ACCEPTABLE,u);
	}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchPasswordNotValidException(PasswordNotValidException u){
		return local(HttpStatus.NOT_ACCEPTABLE,u);
	}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchUserNotfoundException(UserNotFoundException u){
		return local(HttpStatus.NOT_FOUND,u);
	}
}
