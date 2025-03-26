package Entities;

import com.fasterxml.jackson.annotation.JsonSetter;

public class TrackWrapper {
	@JsonSetter("id")
	private String id;
	@JsonSetter("streaming")
	private String streamCode;
	@JsonSetter("download")
	private String downloadCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStreamCode() {
		return streamCode;
	}
	public void setStreamCode(String streamCode) {
		this.streamCode = streamCode;
	}
	public String getDownloadCode() {
		return downloadCode;
	}
	public void setDownloadCode(String downloadCode) {
		this.downloadCode = downloadCode;
	}
	@Override
	public String toString() {
		return "TrackWrapper [id=" + id + ", streamCode=" + streamCode + ", downloadCode=" + downloadCode + "]";
	}
}
