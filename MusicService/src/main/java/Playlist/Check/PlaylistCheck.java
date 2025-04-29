package Playlist.Check;

import java.util.Objects;

import Playlist.Errors.PlayListErrors;
import SharedCheks.SharedCheck;

public class PlaylistCheck extends SharedCheck{
	public static void nameIsValid(String name) throws Exception{
		if(Objects.isNull(name) )
			throw new Exception(PlayListErrors.NAME_IS_NULL);
		if (name.isEmpty() || name.isBlank())
			throw new Exception(PlayListErrors.NAME_IS_EMPTY);
	}
	
}
