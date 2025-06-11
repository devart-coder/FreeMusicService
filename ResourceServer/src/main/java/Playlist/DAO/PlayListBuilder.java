package Playlist.DAO;

import java.util.List;

import Playlist.Check.PlaylistCheck;
import Playlist.DAO.Track.TrackEntity;
import Playlist.Exceptions.PlaylistNameIsNotValidException;
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
	public static PlayListEntity defaultPlaylist()
//			throws PlaylistNameIsNotValidException 
	{ 
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
	public PlayListBuilder setTraks(List<TrackEntity> tracks) {
		this.playlist.setTracks(tracks);
		return this;
	}
	public PlayListEntity build()
//		throws PlaylistNameIsNotValidException
	{
		PlaylistCheck.filedsCheck(playlist);
		return playlist;
	}
}
