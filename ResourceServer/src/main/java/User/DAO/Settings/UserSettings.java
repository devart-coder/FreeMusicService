package User.DAO.Settings;

import java.io.Serializable;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride.GeneratedColumn;

import User.DAO.UserEntity;
import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_settings")
public class UserSettings implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Column(name = "email",nullable = true)
	@Getter
	@Setter
	private String email;
	
	@Column(name = "phone",nullable = true)
	@Setter
	@Getter
	private String phoneNumber;
	
	@Column(name = "avatar_path",nullable = false)
	@ColumnDefault(value = "''")
	@Getter
	@Setter
	private String imagePath;

}
