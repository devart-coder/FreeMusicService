package Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import Entities.PlayListEntity;
import Entities.UserEntity;

public interface PlayListsRepository extends JpaRepository<PlayListEntity, Long>{
//	Optional<User> findAllByUserId(User user);
}
