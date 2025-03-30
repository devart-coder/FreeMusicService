package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);
}
