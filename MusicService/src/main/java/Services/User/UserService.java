package Services.User;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DAO.User.UserEntity;
import Repositories.UserRepository;
import Services.User.Interfaces.UserServiceDetails;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Service
public class UserService implements UserServiceDetails{
	@Autowired
	private UserRepository repos; 
	@Override
	public UserEntity save(UserEntity user) throws Exception {
		notNull(user);
		return repos.save(user);
	}
	private void notNull(Object user) throws Exception {
		if(Objects.isNull(user))
			throw new Exception("Argument is null");//TODO::AddExceptionMessage
	}

	@Override
	public Iterable<UserEntity> findAll() throws Exception{
		return repos.findAll();
	}

	@Override
	public UserEntity findOnceById(Long id) throws Exception {
		notNull(id);
		return 
			repos.findById(id)
			.orElseThrow( () -> new Exception("Can't found user with id '%d'".formatted(id)) );//TODO::AddExceptionMessage
	}

	@Override
	public void deleteByEntity(UserEntity user) throws Exception {
		notNull(user);
		repos.delete(user);
	}

	@Override
	public UserEntity findOnceByName(String username) throws Exception {
		nameIsValid(username);
		return repos.findByUsername(username);
	}

	private void nameIsValid(String username) throws Exception {
		notNull(username);
		if(username.isEmpty() )
			throw new Exception("Username is empty");
		if(username.isBlank() )
			throw new Exception("Username is blank");
	}

}
