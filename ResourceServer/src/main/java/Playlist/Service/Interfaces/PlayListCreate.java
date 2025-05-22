package Playlist.Service.Interfaces;

import java.util.List;
import java.util.function.Supplier;

import Playlist.DAO.PlayListEntity;


public interface PlayListCreate {
//Create
	PlayListEntity add(PlayListEntity newPlayList);
	PlayListEntity add(Supplier<? extends PlayListEntity> newPlayList);
	List<PlayListEntity> addAll(Iterable<PlayListEntity> newPlayList);
}
