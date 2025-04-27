package Services.PlayList.Interfaces;

import java.util.function.Supplier;

import DAO.PlayList.PlayListEntity;

public interface PlayListDelete {
//Delete
	void delete(PlayListEntity playlist);
}
