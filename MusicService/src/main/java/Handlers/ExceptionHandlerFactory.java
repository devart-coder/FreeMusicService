package Handlers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionHandlerFactory {
	
	static public <T> HandlerWrapper getInstance() {
		return new HandlerWrapper();
	}
}
