package Playlist.Exceptions;

import java.time.LocalDate;
import java.util.Objects;

import Playlist.Check.PlaylistNameIsNotValidException;
import Playlist.DAO.PlayListEntity;
import Playlist.ErrorMessanges.PlaylistErrorMessanges;
import SharedChecks.SharedCheck;

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
	
	public static void filedsCheck(PlayListEntity playlist) throws PlaylistNameIsNotValidException {
		nameIsValid(playlist.getName());
		
		if( Objects.isNull(playlist.getMain()) )
			playlist.setMain(false);
		
		if( Objects.isNull(playlist.getSize()) )
			playlist.setSize(0l);
		
		if( Objects.isNull(playlist.getCreatedBy()))
			playlist.setCreatedBy(LocalDate.now());
	}
}
