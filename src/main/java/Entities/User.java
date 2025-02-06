package Entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table("users")
public class User implements UserDetails{
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String username;
	private String password;
	private String role="ROLE_USER";
	private boolean enabled=true; 
	private Date creationTime;

	public User(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}public User(String username, String password,String role) {
		this.setUsername(username);
		this.setPassword(password);
	}public User(){}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	@Override
	public int hashCode() {
		return Objects.hash(creationTime, password, getUsername());
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
				+ enabled + ", creationTime=" + creationTime + "]";
	}
}
