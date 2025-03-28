package Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Track {

	private String id;
	
	@JsonSetter(value = "size")
	private String size;
	
	@JsonSetter(value = "track")
	private String name;

	@JsonSetter(value = "imageJpg")
	private String trackImagePath;
	
	@JsonSetter(value = "bitrate")
	private Integer bitrate;
	
	@JsonSetter(value = "duration")
	private String duration;
	
	@JsonSetter(value = "artistName")
	private String artistName;
	
	@JsonIgnore
	private String url;
	
	private Artist artist = new Artist();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrackImagePath() {
		return trackImagePath;
	}

	public void setTrackImagePath(String trackImagePath) {
		this.trackImagePath = trackImagePath;
	}

	public Integer getBitrate() {
		return bitrate;
	}

	public void setBitrate(Integer bitrate) {
		this.bitrate = bitrate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Track [id=" + id + ", size=" + size + ", name=" + name + ", trackImagePath=" + trackImagePath
				+ ", bitrate=" + bitrate + ", duration=" + duration + ", artistName=" + artistName + ", url=" + url
				+ ", artist=" + artist + "]";
	}
}
