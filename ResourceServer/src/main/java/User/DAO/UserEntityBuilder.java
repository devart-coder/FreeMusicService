package User.DAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import Playlist.DAO.PlayListBuilder;
import Playlist.DAO.PlayListEntity;
import User.Check.UserCheck;
import User.DAO.Settings.UserSettings;
import User.DAO.Settings.UserSettingsBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityBuilder {
	private UserEntity user=new UserEntity();
	
	public static UserEntityBuilder builder() {
		return new UserEntityBuilder();
	}
	public static UserEntity defaulUserWith(String username, String password) throws Exception {
		return 
			builder()
			.setUsername(username)
			.setPassword(password)
			.build();
	}
	public UserEntityBuilder setUsername(String username) {
		this.user.setUsername(username);
		return this;
	}
	public UserEntityBuilder setPassword(String password) {
		this.user.setPassword(password);
		return this;
	}
	public UserEntityBuilder setRole(String role) {
		this.user.setRole(role);
		return this;
	}
	public UserEntityBuilder setPlaylists(List<PlayListEntity> playlists) {
		this.user.setPlaylists(playlists);
		return this;
	}
	public UserEntityBuilder setSettings(UserSettings properties) {
		this.user.setSettings(properties);
		return this;
	}
	public UserEntityBuilder setLastEntity(LocalDateTime localDateTime) {
		this.user.setLastEntry(localDateTime);
		return this;
	}
	public UserEntityBuilder setOnline(boolean online) {
		this.user.setOnline(online);
		return this;
	}
	public UserEntityBuilder setActive(boolean online) {
		this.user.setActive(online);
		return this;
	}
	public UserEntity build() throws Exception {
		UserCheck.filedsCheck(user);
		return user;
	}
}
