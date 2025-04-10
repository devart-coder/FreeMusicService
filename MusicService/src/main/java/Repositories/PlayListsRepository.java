package Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;
import jakarta.transaction.Transactional;

public interface PlayListsRepository extends JpaRepository<PlayListEntity, Long>{
	
	Optional<List<PlayListEntity>> findAllByUser(UserEntity user);
	Optional<List<PlayListEntity>> findAllByUserUsername(String username);
	Optional<List<PlayListEntity>> findAllByUserId(Long id);
	
	Optional<PlayListEntity> findOnceByUserIdAndName(Long userId,String name);
	Optional<PlayListEntity> findOnceByUserIdAndMain(Long userId,Boolean main);
	Optional<PlayListEntity> findOnceByUserUsernameAndMain(String username, Boolean main);
	
	@Modifying
	@Transactional
	@Query("update playlists p set p.name = ?1 where p.id = ?2 ")
	Optional<String> updateNameById(String newName, Long id);
	
	@Modifying
	@Transactional
	@Query("update playlists p set p.name = ?1 where p.id = ?2 ")
	Optional<Boolean> updateMainById(Boolean newMain, Long id);
	
	@Modifying
	@Transactional
	@Query("update playlists p set p.name = ?1 where p.id = ?2 ")
	Optional<Long> updateSizeById(Long size, Long id);

	@Modifying
	@Transactional
	@Query("update playlists p set p.name = ?1 where p.name = ?2 ")
	Optional<Boolean> updateMainByName(Boolean newMain, String name);
	
	@Modifying
	@Transactional
	@Query("update playlists p set p.name = ?1 where p.name = ?2 ")
	Optional<Long> updateSizeByName(Long size, String name);
	
	
	
	
	void deleteById(Long id);
	void deleteByName(String name);
	Optional<PlayListEntity> deleteByIdAndMainFalse(Long id);
	
}
