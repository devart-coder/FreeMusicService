package DAO.User.Settings;

import java.io.Serializable;

import org.hibernate.annotations.ColumnDefault;

import DAO.User.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "user_settings")
public class UserSettings implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Getter
	private Long id;

	@OneToOne
	@MapsId
	private UserEntity user;
	
	@Column(name = "email",nullable = true)
	private String email;
	
	@Column(name = "phone",nullable = true)
	private String phoneNumber;
	
	@Column(name = "avatar_path",nullable = false)
	@ColumnDefault(value = "''")
	private String imagePath;

}
