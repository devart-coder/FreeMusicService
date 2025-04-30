package Security.Providers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import Security.SecureUser;
import Security.Services.InDataBaseUserDetailService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UserAuthProvider implements AuthenticationProvider {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication){
		try {
			String username = authentication.getName();
			String password = authentication.getCredentials().toString();
			var user = (SecureUser)userDetailsService.loadUserByUsername(username);
			if(user == null)
				return null;
			if(passwordEncoder.matches(password, user.getPassword())) 
				return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
			else 
				throw new BadCredentialsException("User '"+username+"': Wrong password ["+password+"]");
		}catch(BadCredentialsException | UsernameNotFoundException e) { 
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
