package DAO.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ForeignKey;

import DAO.PlayList.PlayListEntity;
import DAO.User.Settings.UserSettings;
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

@Entity(name = "users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
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
	private LocalDate createdBy;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<PlayListEntity> playlists;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
	private UserSettings settings; 
}
