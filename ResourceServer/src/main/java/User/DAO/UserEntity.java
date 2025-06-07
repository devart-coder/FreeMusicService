package User.DAO;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import Playlist.DAO.PlayListEntity;
import User.DAO.Settings.UserSettings;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "users")
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter
	@Column(nullable = false)
	private String username;
	
	@Setter
	@Column(nullable = false)
	private String password;
	
	@Setter
	@Column(nullable = false)
	@ColumnDefault(value = "'ROLE_USER'")
	private String role;
	
	@Setter
	@Column(nullable = false,columnDefinition = "boolean")
	@ColumnDefault(value = "true")
	private boolean active; 
	
	@Setter
	@Column(nullable = false,columnDefinition = "boolean")
	@ColumnDefault(value = "false")
	private boolean online;
	
	@Setter
	@Column(nullable = false)
	@ColumnDefault(value = "now()")
	private LocalDateTime lastEntry;
	
	@Setter
	@Column(nullable = false,updatable = false)
	@ColumnDefault(value = "now()")
	private LocalDateTime createdBy;
	
	@Setter
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<PlayListEntity> playlists;
	
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserSettings settings;

	 
}
