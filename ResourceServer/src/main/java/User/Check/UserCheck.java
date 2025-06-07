package User.Check;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

import Playlist.DAO.PlayListBuilder;
import Playlist.Exceptions.PlaylistNameIsNotValidException;
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
	
	public static void filedsCheck(UserEntity user)
			throws UsernameNotValidException, PasswordNotValidException, PlaylistNameIsNotValidException {
		usernameIsValid(user.getUsername());
		passwordIsValid(user.getPassword());
		
		//OnlineAndLastEntryNotSet
		roleCheck(user);
		activeCheck(user);
		playlistsCheck(user);
		settingsCheck(user);
		createdByCheck(user);
	}
	private static void createdByCheck(UserEntity user) {
		if(user.getCreatedBy()==null) {
			log.error(LocalDateTime.now().toString());
			user.setCreatedBy( LocalDateTime.now() );
			log.warn(String.format("'createdBy' field set default value: '%s'",user.getCreatedBy()));
		}
	}
	private static void settingsCheck(UserEntity user) {
		if(user.getSettings()==null) {
			user.setSettings(UserSettingsBuilder.builder().build());
			log.warn(String.format("'settings' field set default value"));
		}
	}
	private static void playlistsCheck(UserEntity user) 
			throws PlaylistNameIsNotValidException 
	{
		if(user.getPlaylists() == null) {
			user.setPlaylists( Arrays.asList(PlayListBuilder.defaultPlaylist()) );
			log.warn(String.format("'playlist' field set default value"));
		}
	}
	private static void activeCheck(UserEntity user) {
		if(user.isActive()==false) {
			user.setActive(true);
			log.warn(String.format("'enabled' field set default value: '%s'",user.isActive()));
		}
	}
	private static void roleCheck(UserEntity user) {
		if(user.getRole() == null) {
			user.setRole("ROLE_USER");
			log.warn(String.format("'role' field set default value: '%s'",user.getRole()));
		}
	}
}
