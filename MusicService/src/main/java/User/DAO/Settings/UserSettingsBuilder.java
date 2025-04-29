package User.DAO.Settings;

import User.DAO.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class UserSettingsBuilder {
	private UserSettings settings = new UserSettings();
	public static UserSettingsBuilder builder() {
		return new UserSettingsBuilder();
	}
	public static UserSettings defaultWithUser(UserEntity user) throws Exception {
		return builder().setUser(user).build();
	}
	public UserSettingsBuilder setEmail(String newEmail) {
		this.settings.setEmail(newEmail);
		return this;
	}
	public UserSettingsBuilder setPhoneNumber(String newPhoneNumber) {
		this.settings.setPhoneNumber(newPhoneNumber);
		return this;
	}
	public UserSettingsBuilder setImagePath(String newImagePath) {
		this.settings.setImagePath(newImagePath);
		return this;
	}
	public UserSettingsBuilder setUser(UserEntity user) {
		if(user.getSettings()==null)
			user.setSettings(settings);
		this.settings.setUser(user);
		return this;
	}
	public UserSettings build() throws Exception {
		if(this.settings.getImagePath()==null) {
			//TODO::AddDefaultAvaterImagePath
			settings.setImagePath("some_default_path");
			log.warn(String.format("'image_path' field set default value: '%s'",settings.getImagePath()));
		}
		if(this.settings.getUser()==null)
			throw new Exception("'User' field is null");
		return settings;
	}
}
