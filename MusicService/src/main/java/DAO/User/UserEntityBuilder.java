package DAO.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import DAO.PlayLists.PlayListBuilder;
import DAO.PlayLists.PlayListEntity;
import DAO.User.Settings.UserSettings;
import DAO.User.Settings.UserSettingsBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityBuilder {
	private UserEntity user = new UserEntity();
	
	public static UserEntityBuilder builder() {
		return new UserEntityBuilder();
	}
	public static UserEntity defaulUserWith(String username, String password) {
		return 
			builder()
			.setUsername(username)
			.setPassword(password)
			.setRole("ROLE_USER")
			.setPlaylists(List.of(PlayListBuilder.defaultPlaylist()))
			.setUserSettings(UserSettingsBuilder.builder().setEmail("").setImagePath("").setPhoneNumber("").build())
			.setCreatedAt(LocalDate.now())
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
	public UserEntityBuilder setCreatedAt(LocalDate createdAt) {
		this.user.setCreatedAt(createdAt);
		return this;
	}
	public UserEntityBuilder setPlaylists(List<PlayListEntity> playlists) {
		this.user.setPlaylists(playlists);
		return this;
	}
	public UserEntityBuilder setUserSettings(UserSettings properties) {
		if(properties.getUser()==null)
			properties.setUser(user);
		this.user.setProperties(properties);
		return this;
	}
	public UserEntity build() {
		//TODO:AddNullPointersCheckers
		return this.user;
	}
}
