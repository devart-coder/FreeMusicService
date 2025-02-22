package Security.Providers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import Security.SecureUser;
import Security.Services.InDataBaseUserDetailService;

@Component
public class UserAuthProvider implements AuthenticationProvider {
	@Autowired
	private InDataBaseUserDetailService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//AuthentificationLogic
		try {
			String username = authentication.getName();
			String password = authentication.getCredentials().toString();
			SecureUser user = userDetailsService.loadUserByUsername(username);
		
			if(passwordEncoder.matches(password, user.getPassword()))
				return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
			else
				throw new BadCredentialsException("Wrong username or password");
		}catch(BadCredentialsException e) {
			System.out.println( e.getMessage() );
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
