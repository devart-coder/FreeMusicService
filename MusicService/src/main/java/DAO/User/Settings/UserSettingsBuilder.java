package DAO.User.Settings;

import DAO.User.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSettingsBuilder {
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
	public UserSettingsBuilder setUser(UserEntity user) {
		if(user.getProperties()==null)
			user.setProperties(settings);
		this.settings.setUser(user);
		return this;
	}
	public UserSettings build() {
		//TODO:AddNullPointersCheckers
		return settings;
	}
}
