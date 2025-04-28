package Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DAO.User.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
}
