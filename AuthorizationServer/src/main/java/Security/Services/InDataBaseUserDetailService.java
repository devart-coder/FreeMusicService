package Security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import Security.SecureUser;
import User.Exceptions.UserNotFoundException;
import User.Service.Interfaces.UserServiceDetails;
//import User.Exceptions.UserNotFoundException;
//import User.Service.Interfaces.UserServiceDetails;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class InDataBaseUserDetailService implements UserDetailsService {
	@Autowired
	private UserServiceDetails userService; 
	@Override
	public SecureUser loadUserByUsername(String username){
		try {
			return new SecureUser(userService.findOnceByName(username));
		} catch (UserNotFoundException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
