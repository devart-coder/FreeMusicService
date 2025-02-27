package Security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Repositories.UserRepository;
import Security.SecureUser;

@Service
public class InDataBaseUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo; 
	@Override
	public SecureUser loadUserByUsername(String username) throws UsernameNotFoundException {
		var user=userRepo.findByUsername(username);
		if(user!=null)
			return new SecureUser(user);
		else
			throw new UsernameNotFoundException("User '" + username + "' was not founted.");
	}

}
