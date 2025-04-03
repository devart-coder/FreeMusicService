package Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import DAO.PlayLists.PlayListEntity;
import DAO.User.UserEntity;
import jakarta.transaction.Transactional;

public interface PlayListsRepository extends JpaRepository<PlayListEntity, Long>{
	@Transactional
	@Modifying
	@Query(value="update playlists p set p.main = ?2 where p.id = ?1",nativeQuery = true)
	void updateMainById(Long id,boolean flag);
	
	@Transactional
	@Modifying
	@Query(value="update playlists p set p.name = ?2 where p.id = ?1",nativeQuery = true)
	void updateNameById(Long id,String newName);
	
	@Transactional
	@Modifying
	@Query(value="update playlists p set p.size = ?2 where p.id = ?1",nativeQuery = true)
	void updateSizeById(Long id,Long size);
	
	Optional<PlayListEntity> findByName(String name);
	List<PlayListEntity> findAllByUser(UserEntity user);
	List<PlayListEntity> findAllByUserUsername(String username);
	List<PlayListEntity> findAllByUserId(Long id);
	
}
