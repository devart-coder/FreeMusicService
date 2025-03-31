package Security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import Entities.UserEntity;

public class SecureUser implements UserDetails {
	private static final long serialVersionUID = -5191799633594168456L;
	private final UserEntity user;
	public SecureUser(UserEntity user) {
		this.user=user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(user.getRole()));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	@Override
	public String toString() {
		return "SecureUser [user=" + user + "]";
	}

}
