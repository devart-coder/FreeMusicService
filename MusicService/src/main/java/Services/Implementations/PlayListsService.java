package Services.Implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import Entities.PlayListEntity;
import Repositories.PlayListsRepository;
import Services.Interfaces.PlayListsDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@NoArgsConstructor
@Data
public class PlayListsService implements PlayListsDetails {
	@Autowired
	private PlayListsRepository playListRepos;
	private PlayListEntity playList;
	
	@Override
	public void save(PlayListEntity newPlayList) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<PlayListEntity> findById(Long Id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<PlayListEntity> findByName(String name) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<PlayListEntity> findAllByUserName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayListEntity> findAllByUseId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateName(String newName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMain(boolean newMain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSize(Long newSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long Id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteByName(String name) {
		// TODO Auto-generated method stub

	}

}
