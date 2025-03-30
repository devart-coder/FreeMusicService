package Entities;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity(name = "mainplaylist")
@Table(name = "mainplaylist")
public class MainPlayListEntity {
	public MainPlayListEntity(List<PlayListEntity> playList) {
		this.playlist=playList;
	}
	@Id
	private Long id;
	@MapsId
	@OneToMany(mappedBy = "mainPlayList")
	private List<PlayListEntity> playlist = new ArrayList<>();
}
