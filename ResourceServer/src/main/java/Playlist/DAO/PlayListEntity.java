package Playlist.DAO;

import java.time.LocalDate;

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
@NoArgsConstructor
public class PlayListEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long Id;

	@Column(nullable = false)
	@ColumnDefault(value = "'Default'")
	@Setter
	@Getter
	private String name;

	@Column(name = "main",nullable = false, columnDefinition = "boolean")
	@ColumnDefault(value = "false")
	@Setter
	@Getter
	private Boolean main;

	@Column(nullable = false)
	@ColumnDefault(value = "0")
	@Setter
	@Getter
	private Long size;

	@Column(nullable = false, updatable = false)
	@ColumnDefault(value = "now()")
	@Getter
	@Setter
	private LocalDate createdBy;

}
