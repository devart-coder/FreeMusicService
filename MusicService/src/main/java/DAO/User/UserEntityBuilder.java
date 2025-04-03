package DAO.User;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import DAO.PlayLists.PlayListEntity;
import DAO.User.Settings.UserSettings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityBuilder {
	private String username;
	private String password;
	private String role="ROLE_USER";
	private boolean enabled=true; 
	private Date createdAt= new Date();
	private List<PlayListEntity> playlists=List.of(new PlayListEntity() );
	private UserSettings properties=new UserSettings(); 
	
	public static UserEntityBuilder builder() {
		return new UserEntityBuilder();
	}
	public UserEntity defaultUser(String username, String password) {
		return builder().setUsername(username).setPassword(password).build();
	}
	public UserEntityBuilder setUsername(String username) {
		this.username=username;
		return this;
	}
	public UserEntityBuilder setPassword(String password) {
		this.password=password;
		return this;
	}
	public UserEntityBuilder setRole(String role) {
		this.role=role;
		return this;
	}
	public UserEntityBuilder setCreatedAt(Date createdAt) {
		this.createdAt=createdAt;
		return this;
	}
	public UserEntityBuilder setPlaylists(List<PlayListEntity> playlists) {
		this.playlists=playlists;
		return this;
	}
	public UserEntityBuilder setUserSettings(UserSettings properties) {
		this.properties=properties;
		return this;
	}
	public UserEntity build() {
		return new UserEntity (null, username, password, role, enabled, createdAt, playlists, properties);
	}

}
