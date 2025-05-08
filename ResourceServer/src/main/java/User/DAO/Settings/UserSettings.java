package User.DAO.Settings;

import java.io.Serializable;

import org.hibernate.annotations.ColumnDefault;

import User.DAO.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Override
	public String toString() {
		return "UserSettings [id=" + id + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", imagePath=" + imagePath + "]";
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Getter
	private Long id;

	@OneToOne
	@MapsId
	@Getter
	@Setter
	private UserEntity user;
	
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
