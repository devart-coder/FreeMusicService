package Handlers;

import org.springframework.web.client.RestClient.RequestHeadersSpec.ExchangeFunction;

public class ResponseHandlerWrapper {
	 public<T>  ResponseExceptionHandler<T> setBodyType( Class<T> classType) {
			return new ResponseExceptionHandler<T>(classType);
	}
}
