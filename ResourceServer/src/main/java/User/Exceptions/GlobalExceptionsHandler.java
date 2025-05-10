package User.Exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import User.Check.PasswordNotValidException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {
	@ExceptionHandler
	public ResponseEntity<Map<String,Object>> catchUserDuplicateException(UserDuplicateException u){
		log.error(u.getMessage());
		return ResponseEntity
				.status(u.getStatus())
				.body(
					Map.of( "Status",u.getStatus()
							,"Message: ",u.getMessage()
					) 
				);
	}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchUsernameNotValidException(UsernameNotValidException u){
		log.error(u.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_ACCEPTABLE)
				.body(
					Map.of( "Message: ",u.getMessage() ) 
				);
	}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchPasswordNotValidException(PasswordNotValidException u){
		log.error(u.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_ACCEPTABLE)
				.body(
					Map.of( "Message: ",u.getMessage() ) 
				);
	}
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchUserNotfoundException(UserNotFoundException u){
		log.error(u.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(
					Map.of( "Message: ",u.getMessage() ) 
				);
	}
}
