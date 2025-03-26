package Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("mainplaylist")
public class MainPlayList {
	@Id
	private Long id;
	@Column("main_playlistname")
	private String playlistName;
	private String username;
	
	public MainPlayList() {}
	public MainPlayList(String name, String username) {
		this.setPlaylistName(name);
		this.username=username;
	}
	public MainPlayList(PlayList playlist) {
		this.playlistName = playlist.getName();
		this.username = playlist.getUsername();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
