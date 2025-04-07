package Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DAO.PlayLists.PlayListEntity;
import DAO.User.UserEntity;

public interface PlayListsRepository extends JpaRepository<PlayListEntity, Long>{
	
	Optional<PlayListEntity> findByName(String name);
	Optional<List<PlayListEntity>> findAllByUser(UserEntity user);
	Optional<List<PlayListEntity>> findAllByUserUsername(String username);
	Optional<List<PlayListEntity>> findAllByUserId(Long id);
	Optional<PlayListEntity> findByUserIdAndMain(Long userId,Boolean main);
	Optional<PlayListEntity> findByUserUsernameAndMain(String username, Boolean main);
	
	void deleteById(Long id);
	void deleteByName(String name);
	
	
}
