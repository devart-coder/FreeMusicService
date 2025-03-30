package Entities;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "playlists")
@Data
@NoArgsConstructor
public class PlayListEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(nullable = false)
	@ColumnDefault(value = "'Default'")
	private String name = "Default";

	
	@Column(name = "main",nullable = false,columnDefinition = "boolean ")
	@ColumnDefault(value = "false")
	private boolean main = false;

	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private Long size=0l;

	@Column(nullable = false)
	@ColumnDefault(value = "now()")
	private Date createdBy = new Date();

	@ManyToOne
	private MainPlayListEntity mainPlayList = new MainPlayListEntity();
	
}
