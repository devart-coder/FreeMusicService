package User.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import User.DAO.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
}
