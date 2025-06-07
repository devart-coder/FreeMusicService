package Playlist.DAO.Track;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrackEntity {
	@Id
	private Long id;
	
	@Column(nullable = false)
	@ColumnDefault(value = "'Default'")
	private Long trackId;
	
	private String trackName;
	
	private String albumName;
	
	private String albumPicture;
	
	private String groupName;
	
	private String groupPicture;
}
