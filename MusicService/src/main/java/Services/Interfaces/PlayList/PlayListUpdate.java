package Services.Interfaces.PlayList;

import DAO.PlayList.PlayListEntity;

public interface PlayListUpdate {
	//Update
		//ByEntity
			void updateNameByEntity(String newName,PlayListEntity playlist);
			void updateMainByEntity(Boolean newMain,PlayListEntity playlist);
			void updateSizeByEntity(Long newSize,PlayListEntity playlist);
		//ById 
			void updateNameById(String newName,Long playlist);
			void updateMainById(Boolean newMain,Long playlist);
			void updateSizeById(Long newSize,Long playlist);
		//ByName
			void updateMainByName(Boolean newMain,String name);
			void updateSizeByName(Long newSize,String name);
}
