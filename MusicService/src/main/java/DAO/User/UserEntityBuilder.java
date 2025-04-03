package DAO.User;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import DAO.PlayLists.PlayListBuilder;
import DAO.PlayLists.PlayListEntity;
import DAO.User.Settings.UserSettings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityBuilder {
	private UserEntity user;
//	private String username;
//	private String password;
//	private String role="ROLE_USER";
//	private boolean enabled=true; 
//	private Date createdAt= new Date();
//	private List<PlayListEntity> playlists=List.of(PlayListBuilder.defaultPlaylist() );
//	private UserSettings properties=new UserSettings(); 
	
	public static UserEntityBuilder builder() {
		return new UserEntityBuilder();
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
	public UserEntityBuilder setCreatedAt(Date createdAt) {
		this.user.setCreatedAt(createdAt);
		return this;
	}
	public UserEntityBuilder setPlaylists(List<PlayListEntity> playlists) {
		this.user.setPlaylists(playlists);
		return this;
	}
	public UserEntityBuilder setUserSettings(UserSettings properties) {
//		this.user.user;
		return this;
	}
	public UserEntity build() {
		return this.user;
//		return new UserEntity (null, username, password, role, enabled, createdAt, playlists, properties);
	}

}
