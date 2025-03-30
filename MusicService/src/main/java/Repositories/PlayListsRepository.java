package Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import Entities.PlayListEntity;
import Entities.UserEntity;

public interface PlayListsRepository extends JpaRepository<PlayListEntity, Long>{
//	@Modifying
//	void setMainByID(Long id);
}
