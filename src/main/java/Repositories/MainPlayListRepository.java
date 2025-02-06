package Repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import Entities.MainPlayList;


public interface MainPlayListRepository extends CrudRepository<MainPlayList,Long>{
	@Modifying
	@Query("update mainplaylist set main_playlistname = :name where username = :username")
	public void updatePlaylistNameByUsername( @Param("name") String name, @Param("username") String username );
	@Query("select main_playlistname from mainplaylist where username = :username")
	public MainPlayList findByUsername( @Param("username") String username );
}