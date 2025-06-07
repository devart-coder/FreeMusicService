package Playlist.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.sound.midi.Track;

import org.hibernate.annotations.ColumnDefault;

import User.DAO.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@Setter
	@Column(nullable = false)
	@ColumnDefault(value = "'Default'")
	private String name;

	@Setter
	@Column(name = "main",nullable = false, columnDefinition = "boolean")
	@ColumnDefault(value = "false")
	private Boolean main;

	@Setter
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private Long size;
	
	@Setter
	@Column
	@OneToMany(cascade = CascadeType.ALL)
	private List<Track> tracks;
	
	@Setter
	@Column(nullable = false, updatable = false)
	@ColumnDefault(value = "now()")
	private LocalDate createdBy;
}
