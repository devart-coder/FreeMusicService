package Playlist.Exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalPlaylistExceptionsHanler {
	private final String ERROR_MESSAGE = "ErrorMessage";
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> catchPlaylistNameisNotValid(PlaylistNameIsNotValidException e){
		return ResponseEntity
				.status(HttpStatus.NOT_ACCEPTABLE)
				.body(Map.of(ERROR_MESSAGE,e.getMessage()));
		}
}
