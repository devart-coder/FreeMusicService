package Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ForeignKey;

import Services.PlayListService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity{
	public UserEntity(String username, String password) {
		this.username=username;
		this.password=password;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	@ColumnDefault(value = "'ROLE_USER'")
	private String role="ROLE_USER";
	
	@Column(nullable = false, columnDefinition = "boolean")
	@ColumnDefault(value = "false")
	private boolean enabled=true; 
	
	@Column(nullable = false,updatable = false)
	@ColumnDefault(value = "now()")
	private Date createdAt = new Date();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private List<PlayListEntity> playlist = List.of(new PlayListEntity());
}
