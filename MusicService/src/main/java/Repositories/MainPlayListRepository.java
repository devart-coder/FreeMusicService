package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Entities.MainPlayListEntity;


public interface MainPlayListRepository extends JpaRepository<MainPlayListEntity,Long>{
//	@Modifying
//	@Query("update mainplaylist m set m.playlistname = :name where username = :username")
//	public void updatePlaylistNameByUsername( String name, String username );
//	@Query("select m.playlistname from mainplaylist m where username = :username")
//	public MainPlayList findByUsername( String username );
}