package Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Entities.PlayListEntity;
import Entities.UserEntity;
import jakarta.transaction.Transactional;

public interface PlayListsRepository extends JpaRepository<PlayListEntity, Long>{
	@Transactional
	@Modifying
	@Query(value="update playlists p set p.main = ?1 where p.id = ?2",nativeQuery = true)
	void setMainById(Boolean flag, Long id);
}
