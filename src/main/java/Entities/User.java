package Entities;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User{
	@Id
	private Long id;
	private String username;
	private String password;
	private String role="ROLE_USER";
	private boolean enabled=true; 
	private Date createdAt;

	public User(String username, String password) {
		this.username=username;
		this.password=password;
	}

	public User(String username, String password,String role ) {
		this.username=username;
		this.password=password;
		this.role=role;
	}
	
	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public Date getCreationTime() {
		return createdAt;
	}

	public void setCreationTime(Date creationTime) {
		this.createdAt = creationTime;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", enabled="
				+ enabled + ", creationTime=" + createdAt + "]";
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
