package User.DAO.Settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

import User.DAO.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class UserSettingsBuilder {
	private final String defaultPath = "src/main/resources/static/images/icons/avatar/"; 
	
	private UserSettings settings = new UserSettings();

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
		if(Objects.equals( this.settings.getImagePath(), null ) || settings.getImagePath().equals(null)) {
			var path = Paths.get(defaultPath).toAbsolutePath();
			try (var fs = Files.list(path)){
				var array = fs.toList();
				var s =new Random().nextInt(array.size());
				settings.setImagePath(array.get(s).toAbsolutePath().toString());
				log.warn(String.format("'image_path' field set default value: '%s'",settings.getImagePath()));
			}catch(Exception e) {
				log.error(e.getMessage());
			}
		}
		return settings;
	}
}
