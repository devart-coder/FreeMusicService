package User.DAO.Settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;

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
		String fileName="";
		log.warn("{}",Objects.equals( this.settings.getImagePath(), null ) ? "Y":"N");
		if(Objects.equals( this.settings.getImagePath(), null )) {
			var path = Paths.get(defaultPath).toAbsolutePath();
			try (var fs = Files.list(path)){
				var array = fs.toArray(String[]::new);
				var size = array.length;
				var random =new Random(size);
				var l = random.nextInt();
				fileName = array[l];
				log.warn(fileName);
				settings.setImagePath(Paths.get(defaultPath).toAbsolutePath()+fileName);
				log.warn(String.format("'image_path' field set default value: '%s'",settings.getImagePath()));
			}catch(Exception e) {
				
			}
		}
		return settings;
	}
}
