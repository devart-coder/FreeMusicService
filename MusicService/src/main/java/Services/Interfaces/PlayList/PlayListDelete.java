package Services.Interfaces.PlayList;

import java.util.function.Supplier;

import DAO.PlayList.PlayListEntity;

public interface PlayListDelete {
//Delete
	void delete(PlayListEntity playlist);
	void delete(Supplier<? extends PlayListEntity> playlist);
	void deleteById(Long Id);
	void deleteByName(String name);
	void deleteByIdWithNotMainNotDefaultName(Long id, String name);
}
