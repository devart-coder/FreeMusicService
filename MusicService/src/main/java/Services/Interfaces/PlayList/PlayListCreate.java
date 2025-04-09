package Services.Interfaces.PlayList;

import java.util.function.Supplier;

import DAO.PlayList.PlayListEntity;

public interface PlayListCreate {
//Create
	void save(PlayListEntity newPlayList);
	void save(Supplier<? extends PlayListEntity> newPlayList);
	void saveAll(Iterable<? extends PlayListEntity> newPlayList);
}
