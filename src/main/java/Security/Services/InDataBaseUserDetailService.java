package Security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import Entities.User;
import Repositories.UserRepository;
import Security.SecureUser;

@Service
public class InDataBaseUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo; 
	@Override
	public SecureUser loadUserByUsername(String username) throws UsernameNotFoundException {
		return new SecureUser(userRepo.findByUsername(username));
	}

}
