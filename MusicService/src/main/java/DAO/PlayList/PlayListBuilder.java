package DAO.PlayList;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import DAO.User.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayListBuilder {
	private PlayListEntity playlist = new PlayListEntity();
	
	public static PlayListBuilder builder() {
		return new PlayListBuilder();
	}
	public static PlayListEntity defaultPlaylist() { 
		return builder().setMain(true).build();
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
		if(user.getPlaylists()==null)
			user.setPlaylists(Arrays.asList(playlist));
		this.playlist.setUser(user);
		return this;
	}
	public PlayListBuilder setSize(Long size) {
		this.playlist.setSize(size);
		return this;
	}
	public PlayListEntity build() {
		if(this.playlist.getName() ==null) {
			this.playlist.setName("Default");
			log.warn(String.format("'name' field set default value: '%s'.",this.playlist.getName()));
		}
		if(this.playlist.getMain()==null) {
			this.playlist.setMain(false);
			log.warn(String.format("'main' field set default value: '%s'.",this.playlist.getMain()));
		}
		if (this.playlist.getSize()==null) {
			this.playlist.setSize(0l);
			log.warn(String.format("'size' field set default value: '%s'.",this.playlist.getSize()));
		}
		if(this.playlist.getCreatedBy()==null) {
			this.playlist.setCreatedBy(LocalDate.now());
			log.warn(String.format("'createdBy' field set default value: '%s'",this.playlist.getCreatedBy()));
		}
		return this.playlist ;
	}
}
