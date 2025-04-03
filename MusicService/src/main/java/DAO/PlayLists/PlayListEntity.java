package DAO.PlayLists;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import DAO.User.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "playlists")
@Table(name = "playlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayListEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long Id;

	@Column(nullable = false, unique = true)
	@ColumnDefault(value = "'Default'")
	private String name;

	@Column(name = "main",nullable = false, columnDefinition = "boolean")
	@ColumnDefault(value = "false")
	private boolean main;

	@ManyToOne
	private UserEntity user;
	
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private Long size;

	@Column(nullable = false, updatable = false)
	@ColumnDefault(value = "now()")
	private LocalDate createdBy;
}
