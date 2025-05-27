package Handlers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseExceptionHandlerFactory {
	
	static public <T> ResponseHandlerWrapper getInstance() {
		return new ResponseHandlerWrapper();
	}
}
