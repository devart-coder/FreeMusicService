package Checkers.Playlist;

import java.util.Objects;

import Services.PlayList.Exceptions.PlayListErrors;

public class PlaylistCheck {
	public static void nameIsValid(String name) throws Exception{
		if(Objects.isNull(name) )
			throw new Exception(PlayListErrors.NAME_IS_NULL);
		if (name.isEmpty() || name.isBlank())
			throw new Exception(PlayListErrors.NAME_IS_EMPTY);
	}
	
}
