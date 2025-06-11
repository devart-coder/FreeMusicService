package Playlist.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.sound.midi.Track;

import org.hibernate.annotations.ColumnDefault;

import Playlist.DAO.Track.TrackEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "playlists")
@Table(name = "playlists")
@Getter
@NoArgsConstructor
public class PlayListEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Setter
	@Column(nullable = false)
	@ColumnDefault(value = "'Default'")
	private String name;

	@Setter
	@Column(name = "main",nullable = false, columnDefinition = "boolean")
	@ColumnDefault(value = "false")
	private Boolean main;

	@Setter
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "playlist_id")
	private List<TrackEntity> tracks;
	
	@Setter
	@Column(nullable = false, updatable = false)
	@ColumnDefault(value = "now()")
	private LocalDate createdBy;
}
