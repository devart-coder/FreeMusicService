package Playlist.Service.Interfaces;

import Playlist.DAO.PlayListEntity;

public interface PlayListUpdate {
	//Update
		//ByEntity
			void updateNameByEntity(String newName,PlayListEntity playlist);
			void updateMainByEntity(Boolean newMain,PlayListEntity playlist);
		//ById 
			void updateNameById(String newName,Long playlist);
			void updateMainById(Boolean newMain,Long playlist);
		//ByName
			void updateMainByName(Boolean newMain,String name);
}
