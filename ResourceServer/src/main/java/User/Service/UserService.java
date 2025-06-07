package User.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import User.Check.UserCheck;
import User.DAO.UserEntity;
import User.ErrorMessanges.UserErrorMessanges;
import User.Exceptions.PasswordNotValidException;
import User.Exceptions.UserDuplicateException;
import User.Exceptions.UserNotFoundException;
import User.Exceptions.UsernameNotValidException;
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
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserEntity create(UserEntity user) throws UserDuplicateException,UsernameNotValidException,PasswordNotValidException {
		UserCheck.notNull(user);
		UserCheck.filedsCheck(user);
		log.warn( repos.findByUsername(user.getUsername()).toString() );
		if(repos.findByUsername(user.getUsername()).isPresent())
			throw new UserDuplicateException("User with name '%s' exists.".formatted(user.getUsername()));

		user.setPassword(encoder.encode(user.getPassword()));
		var newUser = repos.save(user);
		log.info("User with id '%d' created.".formatted(newUser.getId()));
		return newUser;
	}
	
	@Override
	public List<UserEntity> findAll(){
		return repos.findAll();
	}
	
	@Override
	public UserEntity findOnceById(Long id)  
		throws UserNotFoundException{
		
		UserCheck.idIsValid(id);
		var user = 
			repos
			.findById(id)
			.orElseThrow( 
				() -> new ResourceNotFoundException("Can't found user with id '%d'".formatted(id)) );
		log.info("User with id '"+user.getId()+"' was found.");
		return user;
	}
	
	@Override
	public UserEntity findOnceByName(String username)
		throws UserNotFoundException, UsernameNotValidException{
		UserCheck.usernameIsValid(username);
		 
		var user = 
			repos
			.findByUsername(username)
			.orElseThrow( 
				() -> new UserNotFoundException(UserErrorMessanges.USER_NOT_FOUND_WITH_NAME.formatted(username)) );
		log.info("User with id '"+user.getId()+"' was found.");
		return user;
	}
	
	@Override
	public void remove(UserEntity user) {
		UserCheck.notNull(user);
		repos.delete(user);
	}

	public void deleteById(Long user_id) throws UserNotFoundException{
		if(UserCheck.idIsValid(user_id)==false)
			return;
		if(repos.existsById(user_id)==false)
			throw new UserNotFoundException(UserErrorMessanges.USER_NOT_FOUND_WITH_ID.formatted(user_id));
		repos.deleteById(user_id);
	}

	public UserEntity updateByUsername(String username, UserEntity newUser) 
			throws UsernameNotValidException, PasswordNotValidException, UserNotFoundException {
		var user = repos
				.findByUsername(username)
				.orElseThrow(
					() -> new UserNotFoundException(UserErrorMessanges.USER_NOT_FOUND_WITH_NAME.formatted(username)) );

		//'Id' and 'CreatedBy' fiels not updateable.
		if(Objects.nonNull(newUser.getUsername()) && user.getUsername().equalsIgnoreCase(newUser.getUsername())==false) {
			UserCheck.usernameIsValid(newUser.getUsername());
			user.setUsername(newUser.getUsername());
			log.warn("'Username' was update to.");
		}
		if(Objects.nonNull(newUser.getPassword()) && user.getPassword().equalsIgnoreCase(encoder.encode(newUser.getPassword()))==false) {
			UserCheck.passwordIsValid(newUser.getPassword());
			user.setPassword(encoder.encode(newUser.getPassword()));
			log.warn("'Password' was update.");
		}
		if(Objects.nonNull(newUser.getRole()) && user.getRole().equalsIgnoreCase(newUser.getRole())==false) {
			user.setRole(newUser.getRole());
			log.warn("'Role' was update.");
		}
		if(Objects.nonNull(newUser.isActive()) && user.isActive()!=newUser.isActive()) {
			user.setActive(newUser.isActive());
			log.warn("'Enable' was update.");
		}
		if(Objects.nonNull(newUser.getPlaylists()) && Objects.deepEquals(user.getPlaylists(), newUser.getPlaylists()) == false) {
			user.setPlaylists(newUser.getPlaylists());
			log.warn("'Playlists' was update.");
		}
		if(Objects.nonNull(newUser.getSettings()) && Objects.deepEquals( user.getSettings(), newUser.getSettings())==false) { 
			user.setSettings(newUser.getSettings());
			log.warn("'Settings' was update.");
		}
		return repos.save(user);
	}
}