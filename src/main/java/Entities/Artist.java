package Entities;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Artist {
	
	private String artistId;
	
	@JsonSetter(value = "name")
	private String artistName="Unknown";
	
	@JsonSetter(value = "imageJpg")
	private String artistImagePath;

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getArtistImagePath() {
		return artistImagePath;
	}

	public void setArtistImagePath(String artistImagePath) {
		this.artistImagePath = artistImagePath;
	}

	@Override
	public String toString() {
		return "Artist [artistId=" + getArtistId() + ", artistName=" + artistName + ", artistImagePath=" + artistImagePath
				+ "]";
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

}
