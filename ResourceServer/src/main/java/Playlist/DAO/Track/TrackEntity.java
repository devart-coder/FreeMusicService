package Playlist.DAO.Track;

import java.nio.file.Path;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tracks")
@Table(name = "tracks")
@Getter
@NoArgsConstructor
public class TrackEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false)
	private Long trackId;
	
	@Setter
	@Column(nullable = false)
	private String trackName;
	
	@Setter
	@Column(nullable = false)
	private String albumName;
	
	@Setter
	@Column
	private String albumPicture;
	
	@Setter
	@Column(nullable = false)
	private String groupName;
	
	@Setter
	@Column
	private String groupPicture;
}
