package DAO.PlayLists;

import java.util.Date;

import DAO.User.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayListBuilder {
	private String name="Default";
	private boolean main=false;
	private UserEntity user;
	private Long size=0L;
	private Date createdBy = new Date();
	
	static PlayListBuilder builder() {
		return new PlayListBuilder();
	}
	
	static PlayListEntity defaultPlaylist() {
		return builder().build();
	} 
	
	public PlayListEntity build() {
		return new PlayListEntity(null,name,main,user,size,createdBy) ;
	}
}
