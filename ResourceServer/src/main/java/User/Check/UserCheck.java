package User.Check;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import Playlist.DAO.PlayListBuilder;
import SharedChecks.SharedCheck;
import User.DAO.UserEntity;
import User.DAO.Settings.UserSettingsBuilder;
import User.ErrorMessanges.UserErrorMessanges;
import User.Exceptions.PasswordNotValidException;
import User.Exceptions.UsernameNotValidException;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UserCheck extends SharedCheck{
	public static void usernameIsValid(String username) throws UsernameNotValidException{
		if(Objects.isNull(username) )
			throw new UsernameNotValidException(UserErrorMessanges.USERNAME_IS_NULL);
		if (username.isEmpty())
			throw new UsernameNotValidException(UserErrorMessanges.USERNAME_IS_EMPTY);
		if (username.isBlank())
			throw new UsernameNotValidException(UserErrorMessanges.USERNAME_IS_BLANK);
	}
	public static void passwordIsValid(String username) throws PasswordNotValidException{
		if(Objects.isNull(username) )
			throw new PasswordNotValidException(UserErrorMessanges.PASSWORD_IS_NULL);
		if (username.isEmpty())
			throw new PasswordNotValidException(UserErrorMessanges.PASSWORD_IS_EMPTY);
		if (username.isBlank())
			throw new PasswordNotValidException(UserErrorMessanges.PASSWORD_IS_BLANK);
	}
	public static UserEntity filedsCheck(UserEntity user) throws UsernameNotValidException,PasswordNotValidException {
		usernameIsValid(user.getUsername());
		passwordIsValid(user.getPassword());
		
		if(user.getRole() == null) {
			user.setRole("ROLE_USER");
			log.warn(String.format("'role' field set default value: '%s'",user.getRole()));
		}
		if(user.isEnabled()==false) {
			user.setEnabled(true);
			log.warn(String.format("'enabled' field set default value: '%s'",user.isEnabled()));
		}
		if(user.getPlaylists() == null) {
			user.setPlaylists( Arrays.asList(PlayListBuilder.defaultPlaylist()) );
			log.warn(String.format("'playlist' field set default value"));
		}
		if(user.getSettings()==null) {
			user.setSettings(UserSettingsBuilder.builder().build());
			log.warn(String.format("'settings' field set default value"));
		}
		if(user.getCreatedBy()==null) {
			user.setCreatedBy(LocalDate.now());
			log.warn(String.format("'createdBy' field set default value: '%s'",user.getCreatedBy()));
		}
		return user;
		
	}
}
