package Security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import User.DAO.UserEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecureUser implements UserDetails {
	private static final long serialVersionUID = -8826382408543735707L;
	private final UserEntity user;
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
	public Long getId() {
		return user.getId()
;	}
}
