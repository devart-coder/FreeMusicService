package User.Exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalUserExceptionsHandler {
	private final String ERROR_MESSAGE = "ErrorMessage";
	@ExceptionHandler
	public ResponseEntity<Map<String,Object>> catchUserDuplicateException(UserDuplicateException u){
		log.error(ERROR_MESSAGE,u.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_ACCEPTABLE)
				.body( Map.of( ERROR_MESSAGE,u.getMessage() ) );
	}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchUsernameNotValidException(UsernameNotValidException u){
		log.error(ERROR_MESSAGE,u.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_ACCEPTABLE)
				.body( Map.of( ERROR_MESSAGE,u.getMessage() ) );
	}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchPasswordNotValidException(PasswordNotValidException u){
		log.error(ERROR_MESSAGE,u.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_ACCEPTABLE)
				.body( Map.of( ERROR_MESSAGE,u.getMessage() ) );
	}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchUserNotfoundException(UserNotFoundException u){
		log.error(ERROR_MESSAGE,u.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_ACCEPTABLE)
				.body( Map.of(ERROR_MESSAGE,u.getMessage()) );
	}
}
