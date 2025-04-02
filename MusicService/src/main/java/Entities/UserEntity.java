package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ForeignKey;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Table(name = "users")
@NoArgsConstructor
@Data
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public UserEntity(String username, String password) {
		this.username=username;
		this.password=password;
	}
	public UserEntity(String username, String password, String role) {
		this.username=username;
		this.password=password;
		this.role=role;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@Getter
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	@ColumnDefault(value = "'ROLE_USER'")
	private String role;
	
	@Column(nullable = false, columnDefinition = "boolean")
	@ColumnDefault(value = "true")
	private boolean enabled; 
	
	@Column(nullable = false,updatable = false)
	@ColumnDefault(value = "now()")
	private Date createdAt;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<PlayListEntity> playlists;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
	private UserSettings properties; 
}
