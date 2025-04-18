package Services.PlayList.Interfaces;

import java.util.List;
import java.util.function.Supplier;

import DAO.PlayList.PlayListEntity;

public interface PlayListCreate {
//Create
	String PLAYLIST_NOT_SAVED ="'Playlist' was not saved."; 
	PlayListEntity save(PlayListEntity newPlayList) throws Exception;
	PlayListEntity save(Supplier<? extends PlayListEntity> newPlayList) throws Exception;
	List<PlayListEntity> saveAll(Iterable<PlayListEntity> newPlayList) throws Exception;
}
