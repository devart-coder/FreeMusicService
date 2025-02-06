package Repositories;

import org.springframework.data.repository.CrudRepository;
import Entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
