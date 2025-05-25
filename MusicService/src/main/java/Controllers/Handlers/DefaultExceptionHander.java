package Controllers.Handlers;

import org.springframework.web.client.RestClient.RequestHeadersSpec.ExchangeFunction;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultExceptionHander<T> implements ExceptionHander<T>{
	@Override
	public ExchangeFunction<T> handler() {
		// TODO Auto-generated method stub
		return null;
	}

}
