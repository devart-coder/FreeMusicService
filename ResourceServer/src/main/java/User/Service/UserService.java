package User.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import User.Check.UserCheck;
import User.DAO.UserEntity;
import User.Exceptions.UserNotFoundException;
import User.Repository.UserRepository;
import User.Service.Interfaces.UserServiceDetails;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@NoArgsConstructor
public class UserService implements UserServiceDetails{
	@Autowired
	private UserRepository repos; 
	@Override
	public UserEntity save(UserEntity user) throws Exception {
		UserCheck.notNull(user);
		return repos.save(user);
	}
	@Override
	public Iterable<UserEntity> findAll(){
		return repos.findAll();
	}
	@Override
	public UserEntity findOnceById(Long id)  
		throws ResourceNotFoundException{
		
		UserCheck.idIsValid(id);
		return 
			repos
			.findById(id)
			.orElseThrow( 
				() -> new ResourceNotFoundException("Can't found user with id '%d'".formatted(id)) );
	}
	@Override
	public UserEntity findOnceByName(String username)  
		throws UserNotFoundException{

		try {
			UserCheck.usernameIsValid(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 
			repos
			.findByUsername(username)
			.orElseThrow( 
				() -> new UserNotFoundException("Can't found user with id '%s'".formatted(username)) );
	}
	@Override
	public void deleteByEntity(UserEntity user) {
		UserCheck.notNull(user);
		repos.delete(user);
	}
}