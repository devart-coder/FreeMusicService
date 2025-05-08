package User.Check;

import java.util.Objects;

import SharedChecks.SharedCheck;
import User.ErrorMessanges.UserErrorMessanges;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UserCheck extends SharedCheck{
	public static void usernameIsValid(String username) throws Exception{
		if(Objects.isNull(username) )
			throw new Exception(UserErrorMessanges.USERNAME_IS_NULL);
		if (username.isEmpty())
			throw new Exception(UserErrorMessanges.USERNAME_IS_EMPTY);
		if (username.isBlank())
			throw new Exception(UserErrorMessanges.USERNAME_IS_BLANK);
	}
}
