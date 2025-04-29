package SharedCheks;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SharedCheck {
	private final static String ID_LESS_ZERRO = "'Id' less zerro.";
	private final static String ARG_IS_NULL = "Argument is null.";
	private static boolean idGreaterOrEqualZerro(Long id) {
		try {
			if( id < 0 )
				throw new Exception(ID_LESS_ZERRO);
			return true;
		}catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	private static boolean idNotNull(Long id) {
		try {
			if(Objects.isNull(id)) 
				throw new Exception();
			return true;
		}catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	public static boolean idIsValid(Long id){
		if( idNotNull(id) && idGreaterOrEqualZerro(id) )
			return true;
		else
			return false;
	}
	public static boolean notNull(Object obj) {
		try {
			if(Objects.isNull(obj))
				throw new Exception(ARG_IS_NULL);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	} 
}
