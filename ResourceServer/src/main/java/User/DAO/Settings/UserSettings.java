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

@Entity(name = "user_settings")
@Table(name = "user_settings")
@Getter
@ToString
public class UserSettings implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email",nullable = true)
	@Setter
	private String email;
	
	@Column(name = "phone",nullable = true)
	@Setter
	private String phoneNumber;
	
	@Column(nullable = false)
	@Setter
	private String imagePath;

}
