package Repositories;

import org.springframework.data.repository.CrudRepository;

import Entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);
}
