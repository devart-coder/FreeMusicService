package Security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import DAO.User.UserEntity;
import Repositories.UserRepository;
import Security.SecureUser;
import Services.User.UserService;
import Services.User.Exceptions.UserNotFoundException;
import Services.User.Interfaces.UserServiceDetails;
import lombok.extern.slf4j.Slf4j;
@Slf4j
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
