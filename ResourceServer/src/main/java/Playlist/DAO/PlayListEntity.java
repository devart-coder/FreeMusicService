package Playlist.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.sound.midi.Track;

import org.hibernate.annotations.ColumnDefault;

import User.DAO.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "playlists")
@Table(name = "playlists")
@Getter
@NoArgsConstructor
public class PlayListEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(nullable = false)
	@ColumnDefault(value = "'Default'")
	@Setter
	private String name;

	@Column(name = "main",nullable = false, columnDefinition = "boolean")
	@ColumnDefault(value = "false")
	@Setter
	private Boolean main;

	@Column(nullable = false)
	@ColumnDefault(value = "0")
	@Setter
	private Long size;
	
	@Column
	@Setter
	private List<Track> tracks;
	
	@Column(nullable = false, updatable = false)
	@ColumnDefault(value = "now()")
	@Setter
	private LocalDate createdBy;
	
	
	
	
}
