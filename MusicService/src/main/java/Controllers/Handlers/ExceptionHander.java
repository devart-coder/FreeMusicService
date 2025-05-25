package Controllers.Handlers;

import org.springframework.web.client.RestClient.RequestHeadersSpec.ExchangeFunction;
import Playlist.DAO.PlayListEntity;

//@FunctionalInterface
public interface ExceptionHander<T> {
	 ExchangeFunction<T> handler(); 
}
