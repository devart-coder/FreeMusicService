package Security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import DAO.User.UserEntity;
import Repositories.UserRepository;
import Security.SecureUser;

public class InDataBaseUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo; 
	@Override
	public SecureUser loadUserByUsername(String username) throws UsernameNotFoundException {
		var user=userRepo.findByUsername(username);
		if(user!=null)
			return new SecureUser(user);
		else
			throw new UsernameNotFoundException(String.format("User '%s' not fount.", username));
	}

}
