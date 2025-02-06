package Entities;

public class ZaicevNetURL {
	private final String url;
	private String searchName = "?q=";
	//TODO:Implements SearchingByTrackName
	private String searchByTrack = "/tracks";
	private String artistLimit="&limitArtist=";
	private String trackLimit="&limitTrack=";
	public ZaicevNetURL(String url) {
		this.url=url;
	}
	public String getSeachName() {
		return searchName;
	}
	public ZaicevNetURL setSeachName(String searchName) {
		this.searchName += searchName;
		return this;
	}
	public String getArtistLimit() {
		return artistLimit;
	}
	public ZaicevNetURL setArtistLimit(String artistLimit) {
		this.artistLimit += artistLimit;
		return this;
	}
	public String getTrackLimit() {
		return trackLimit;
	}
	public ZaicevNetURL setTrackLimit(String trackLimit) {
		this.trackLimit += trackLimit;
		return this;
	}
	public String build() {
		return url + searchName + trackLimit + artistLimit ;
	}
}
