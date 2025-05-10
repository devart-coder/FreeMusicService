package User.ErrorMessanges;

public interface UserErrorMessanges {
	static String USERNAME_IS_NULL="'Username' is null.";
	static String USERNAME_IS_EMPTY="'Username' is empty.";
	static String USERNAME_IS_BLANK="'Username' is blank.";

	static String PASSWORD_IS_NULL = "'Password' is null.";
	static String PASSWORD_IS_EMPTY = "'Password' is empty.";
	static String PASSWORD_IS_BLANK = "'Password' is blank.";

	static String USER_NOT_FOUND_WITH_NAME = "User with name '%s' not found.";
	static String USER_NOT_FOUND_WITH_ID = "User with id '%d' not found.";
}
