package DAO;

import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TrackWrapper {
	@JsonSetter("id")
	private String id;

	@JsonSetter("streaming")
	private String streamCode;

	@JsonSetter("download")
	private String downloadCode;
}
