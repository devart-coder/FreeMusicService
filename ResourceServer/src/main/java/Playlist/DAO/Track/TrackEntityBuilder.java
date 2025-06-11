package Playlist.DAO.Track;

import java.nio.file.Paths;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrackEntityBuilder {
	private TrackEntity track = new TrackEntity();
	public static TrackEntityBuilder builder() {
		return new TrackEntityBuilder();
	}
	public TrackEntityBuilder setTrackId(Long id) {
		track.setTrackId(id);
		return this;
	}
	public TrackEntityBuilder setTrackName(String name) {
		track.setTrackName(name);
		return this;
	}
	public TrackEntityBuilder setAlbumName(String name) {
		track.setAlbumName(name);
		return this;
	}
	public TrackEntityBuilder setAlbumPicture(String path) {
		track.setAlbumPicture(path);
		return this;
	}
	public TrackEntityBuilder setGroupName(String name) {
		track.setGroupName(name);
		return this;
	}
	public TrackEntityBuilder setGroupPicture(String path) {
		track.setGroupPicture(path);
		return this;
	}
	public TrackEntity build() {
		return track;
	}
}
