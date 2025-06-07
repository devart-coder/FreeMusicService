package Playlist.Check;

import java.time.LocalDate;
import java.util.Objects;

import Playlist.DAO.PlayListEntity;
import Playlist.ErrorMessanges.PlaylistErrorMessanges;
import Playlist.Exceptions.PlaylistNameIsNotValidException;
import SharedChecks.SharedCheck;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class PlaylistCheck extends SharedCheck{
	public static boolean nameIsValid(String name) throws PlaylistNameIsNotValidException{
		if(Objects.isNull(name) )
			throw new PlaylistNameIsNotValidException(PlaylistErrorMessanges.NAME_IS_NULL);
		if (name.isEmpty())
			throw new PlaylistNameIsNotValidException(PlaylistErrorMessanges.NAME_IS_EMPTY);
		if (name.isBlank())
			throw new PlaylistNameIsNotValidException(PlaylistErrorMessanges.NAME_IS_BLANK);
		return true;
	}
	
	public static void filedsCheck(PlayListEntity playlist)
//		throws PlaylistNameIsNotValidException 
	{
//		nameIsValid(playlist.getName());
		if(playlist.getName() == null) {
			playlist.setName("Default");
			log.warn(String.format("'name' field set default value: '%s'.",playlist.getName()));
		}
		if(playlist.getMain() == null) {
			playlist.setMain(false);
			log.warn(String.format("'main' field set default value: '%s'.",playlist.getMain()));
		}
		if(playlist.getCreatedBy() == null) {
			playlist.setCreatedBy(LocalDate.now());
			log.warn(String.format("'createdBy' field set default value: '%s'",playlist.getCreatedBy()));
		}
	}
}
