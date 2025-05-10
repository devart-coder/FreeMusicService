package User.DAO.Settings;

import java.nio.file.Paths;

import User.DAO.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class UserSettingsBuilder {
	private UserSettings settings = new UserSettings();
	private final String defaultPath = "src/main/resources/static/images/icons/UserAvatar/"; 
	public static UserSettingsBuilder builder() {
		return new UserSettingsBuilder();
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
	public UserSettings build() {
		if(this.settings.getImagePath()==null) {
			settings.setImagePath(Paths.get(defaultPath).toAbsolutePath().toString());
			log.warn(String.format("'image_path' field set default value: '%s'",settings.getImagePath()));
		}
		return settings;
	}
}
