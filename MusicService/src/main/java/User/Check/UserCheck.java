package User.Check;

import java.util.Objects;

import SharedCheks.SharedCheck;
import User.Errors.UserErrors;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UserCheck extends SharedCheck{
	public static void usernameIsValid(String username) throws Exception{
		if(Objects.isNull(username) )
			throw new Exception(UserErrors.NAME_IS_NULL);
		if (username.isEmpty() || username.isBlank())
			throw new Exception(UserErrors.NAME_IS_EMPTY);
	}
}
