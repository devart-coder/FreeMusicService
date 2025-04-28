package Services.User;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import DAO.User.UserEntity;
import Repositories.UserRepository;
import Services.User.Exceptions.UserNotFoundException;
import Services.User.Interfaces.UserServiceDetails;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@NoArgsConstructor
@Slf4j
@Service
public class UserService implements UserServiceDetails{
	@Autowired
	private UserRepository repos; 
	@Autowired
	private PasswordEncoder encoder; 
	
	@Override
	public UserEntity save(UserEntity user) throws Exception {
		notNull(user);
		return repos.save(user);
	}
	@Override
	public Iterable<UserEntity> findAll(){
		return repos.findAll();
	}
	@Override
	public UserEntity findOnceById(Long id) throws UserNotFoundException{
		notNull(id);
		return 
			repos
			.findById(id)
			.orElseThrow( () -> new UserNotFoundException("Can't found user with id '%d'".formatted(id)) );
	}
	@Override
	public UserEntity findOnceByName(String username) throws Exception,UserNotFoundException{
		nameIsValid(username);
		return 
			repos
			.findByUsername(username)
			.orElseThrow( () -> new UserNotFoundException("Can't found user with name '%s'".formatted(username)) );
	}
	@Override
	public void deleteByEntity(UserEntity user) {
		notNull(user);
		repos.delete(user);
	}
	private void nameIsValid(String username) throws Exception{
		notNull(username);
			if(username.isEmpty() )
				throw new Exception("Username is empty");
			if(username.isBlank() )
				throw new Exception("Username is blank");
	}
	private void notNull(Object user) {
		try {
			if(Objects.isNull(user))
				throw new Exception("Argument is null");//TODO::AddExceptionMessage
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
}
