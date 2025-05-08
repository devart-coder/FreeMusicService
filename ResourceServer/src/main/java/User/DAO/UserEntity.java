package User.DAO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.annotations.ColumnDefault;

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
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", enabled=" + enabled + ", createdBy=" + createdBy + ", playlists=" + playlists + ", settings="
				+ settings + "]";
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@Getter
	private Long id;
	
	@Setter
	@Getter
	@Column(nullable = false)
	private String username;
	
	@Setter
	@Getter
	@Column(nullable = false)
	private String password;
	
	@Setter
	@Getter
	@Column(nullable = false)
	@ColumnDefault(value = "'ROLE_USER'")
	private String role;
	
	@Setter
	@Getter
	@Column(nullable = false,columnDefinition = "boolean")
	@ColumnDefault(value = "true")
	private boolean enabled; 
	
	@Setter
	@Getter
	@Column(nullable = false,updatable = false)
	@ColumnDefault(value = "now()")
	private LocalDate createdBy;
	
	@Setter
	@Getter
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<PlayListEntity> playlists;
	
	@Setter
	@Getter
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
	private UserSettings settings; 
}
