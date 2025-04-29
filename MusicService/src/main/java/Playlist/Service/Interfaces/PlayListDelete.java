package Playlist.Service.Interfaces;

import java.util.function.Supplier;

import Playlist.DAO.PlayListEntity;


public interface PlayListDelete {
//Delete
	void delete(PlayListEntity playlist);
	void deleteById(Long deleteButton);
}
