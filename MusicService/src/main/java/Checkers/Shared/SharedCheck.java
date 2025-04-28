package Checkers.Shared;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SharedCheck {
	private final static String NULL_ARGUMENT = "Argument is null";
	public static void notNull(Object obj) {
		try {
			if(Objects.isNull(obj))
				throw new Exception(NULL_ARGUMENT);
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
}
