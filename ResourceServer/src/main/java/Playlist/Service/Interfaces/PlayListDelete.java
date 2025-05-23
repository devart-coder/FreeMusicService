package Playlist.Service.Interfaces;

import java.util.function.Supplier;

import Playlist.DAO.PlayListEntity;
import Playlist.Exceptions.PlayListNotFoundException;


public interface PlayListDelete {
//Delete
	void delete(PlayListEntity playlist)throws PlayListNotFoundException;
	void deleteById(Long deleteButton) throws PlayListNotFoundException;
}
