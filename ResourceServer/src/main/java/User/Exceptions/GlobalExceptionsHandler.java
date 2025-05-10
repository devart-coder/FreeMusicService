package User.Exceptions;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {
	@ExceptionHandler
	public ResponseEntity<Map<String,Object>> catchUserDuplicateException(UserDuplicateException u){
		log.error(u.getMessage());
		return ResponseEntity
				.status(u.getStatus())
				.body(Map.of(
						"Status",u.getStatus()
						,"Message: ",u.getMessage()) 
				);
	}
}
