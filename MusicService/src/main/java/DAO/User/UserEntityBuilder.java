package DAO.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import DAO.PlayList.PlayListBuilder;
import DAO.PlayList.PlayListEntity;
import DAO.User.Settings.UserSettings;
import DAO.User.Settings.UserSettingsBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityBuilder {
	@Autowired
	private PasswordEncoder encoder;
	private UserEntity user;
	
	private void  notNull() {
		
	}
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
		this.user.setPassword(encoder.encode(password));
		return this;
	}
	public UserEntityBuilder setRole(String role) {
		this.user.setRole(role);
		return this;
	}
	public UserEntityBuilder setPlaylists(List<PlayListEntity> playlists) {
		for( var p : playlists) 
			if(p.getUser()==null)
				p.setUser(user);
		this.user.setPlaylists(playlists);
		return this;
	}
	public UserEntityBuilder setSettings(UserSettings properties) {
		if(properties.getUser()==null)
			properties.setUser(user);
		this.user.setSettings(properties);
		return this;
	}
	public UserEntity build() throws Exception {
		
		if(this.user.getUsername()==null) {
			throw new Exception(String.format("'username' field is empty."));
		}
		if(this.user.getPassword()==null) {
			throw new Exception(String.format("'password' field is empty."));
		}
		if(this.user.getRole() == null) {
			this.user.setRole("ROLE_USER");
			log.warn(String.format("'role' field set default value: '%s'",this.user.getRole()));
		}
		if(this.user.getPlaylists() == null) {
			this.user.setPlaylists( Arrays.asList(PlayListBuilder.defaultPlaylist()) );
			log.warn(String.format("'playlist' field set default value"));
		}
		if(this.user.getSettings()==null) {
			this.user.setSettings(UserSettingsBuilder.defaultWithUser(user));
			log.warn(String.format("'settings' field set default value"));
		}
		if(this.user.getCreatedBy()==null) {
			this.user.setCreatedBy(LocalDate.now());
			log.warn(String.format("'createdBy' field set default value: '%s'",user.getCreatedBy()));
		}
		return this.user;
	}
}
