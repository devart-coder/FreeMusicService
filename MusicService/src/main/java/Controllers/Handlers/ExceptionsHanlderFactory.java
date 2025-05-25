package Controllers.Handlers;

import org.springframework.ui.Model;

import Playlist.DAO.PlayListEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionsHanlderFactory {
	static public ExceptionHander getInstance(ExceptionHanderType type) {
		return 
			switch(type) {
				case Default -> new DefaultExceptionHander<PlayListEntity>();
				case WithModel -> new ExceptionHandlerWithModel(Model.class );
				default -> throw new IllegalArgumentException("Unexpected value: " + type);
			};
	}
}
