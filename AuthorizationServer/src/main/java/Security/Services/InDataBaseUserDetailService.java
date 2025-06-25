package Security.Services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import Security.SecureUser;
import User.DAO.UserEntity;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class InDataBaseUserDetailService implements UserDetailsService {
//	@Autowired
//	private UserServiceDetails userService; 
	@Override
	public SecureUser loadUserByUsername(String username){
		var user = RestClient
		.builder()
		.baseUrl("http://localhost:7070")
		.build()
		.get()
		.uri("/api/users/"+username)
		.retrieve()
		.body(UserEntity.class);
		log.info("UserDetailService:Find: {}",user);
		return new SecureUser(user);
	}
}
