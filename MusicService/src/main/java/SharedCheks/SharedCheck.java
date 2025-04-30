package SharedCheks;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SharedCheck {
	protected static final String ARG_IS_NULL = "Argument is null.";
	protected static final String ID_IS_NULL ="'Id' field is null.";
	protected static final String ID_LESS_ZERRO ="'Id' field less zerro.";

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
		return ( idNotNull(id) && idGreaterOrEqualZerro(id) ) ? true : false;
	}
	public static boolean notNull(Object obj) {
		try {
			if(Objects.isNull(obj))
				throw new Exception(ARG_IS_NULL);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	} 
	public static boolean isNull(Object obj) {
		return !notNull(obj);
	}
}
