package Handlers;

import org.springframework.web.client.RestClient.RequestHeadersSpec.ExchangeFunction;

public class HandlerWrapper {
	 public<T>  ExceptionHandler<T> setResultType( Class<T> classType) {
			return new ExceptionHandler<T>(classType);
	}
}
