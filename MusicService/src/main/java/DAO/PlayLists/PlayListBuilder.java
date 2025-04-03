package DAO.PlayLists;

import java.util.Date;

import DAO.User.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayListBuilder {
	private PlayListEntity playlist = new PlayListEntity(null,"Default",false,null,0l,new Date());
	
	public static PlayListBuilder builder() {
		return new PlayListBuilder();
	}
	public static PlayListEntity defaultPlaylist() {
		return builder().build();
	}
	public PlayListBuilder setName(String name) {
		this.playlist.setName(name);
		return this;
	}
	public PlayListBuilder setMain(boolean flag) {
		this.playlist.setMain(flag);
		return this;
	}
	public PlayListBuilder setUserEntity(UserEntity user) {
		this.playlist.setUser(user);
		return this;
	}
	public PlayListBuilder setSize(Long size) {
		this.playlist.setSize(size);
		return this;
	}
	public PlayListBuilder setCreatedBy(Date date) {
		this.playlist.setCreatedBy(date);
		return this;
	}
	public PlayListEntity build() {
		return this.playlist ;
	}
}
