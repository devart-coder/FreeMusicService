package Repositories;

import org.springframework.data.repository.CrudRepository;

import Entities.PlayList;

public interface PlayListsRepository extends CrudRepository<PlayList, Long>{
	Iterable<PlayList> findAllByUsername(String username);
}
