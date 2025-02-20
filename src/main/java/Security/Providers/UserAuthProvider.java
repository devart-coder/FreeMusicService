package Security.Providers;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthProvider implements AuthenticationProvider {
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	public UserAuthProvider(UserDetailsService uds, PasswordEncoder password) {
		userDetailsService=uds;
		passwordEncoder=password;
	}
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//AuthentificationLogic
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		var user = userDetailsService.loadUserByUsername(username);
		if(user !=null && passwordEncoder.matches(password, user.getPassword()))
			return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
		else
			throw new BadCredentialsException("Wrong username or password");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
