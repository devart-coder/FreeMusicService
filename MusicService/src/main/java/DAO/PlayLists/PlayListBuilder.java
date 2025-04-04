package DAO.PlayLists;

import java.time.LocalDate;
import java.util.List;

import DAO.User.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
			user.setPlaylists(List.of(playlist));
		this.playlist.setUser(user);
		return this;
	}
	public PlayListBuilder setSize(Long size) {
		this.playlist.setSize(size);
		return this;
	}
	public PlayListBuilder setCreatedBy(LocalDate date){
		this.playlist.setCreatedBy(date);
		return this;
	}
	public PlayListEntity build() {
		if(this.playlist.getName() ==null)
			this.playlist.setName("Default");
		
		else if(this.playlist.getMain()==null)
			this.playlist.setMain(false);
		
		else if (this.playlist.getCreatedBy()==null)
			this.playlist.setCreatedBy(LocalDate.now());
		
		else if (this.playlist.getSize()==null)
			this.playlist.setSize(0l);
		
		return this.playlist ;
	}
}
