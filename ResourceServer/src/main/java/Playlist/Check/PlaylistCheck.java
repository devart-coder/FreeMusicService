package Playlist.Check;

import java.util.Objects;

import Playlist.DAO.PlayListEntity;
import Playlist.ErrorMessanges.PlaylistErrorMessanges;
import SharedChecks.SharedCheck;

public class PlaylistCheck extends SharedCheck{
	public static boolean nameIsValid(String name) throws Exception{
		if(Objects.isNull(name) )
			throw new Exception(PlaylistErrorMessanges.NAME_IS_NULL);
		if (name.isEmpty())
			throw new Exception(PlaylistErrorMessanges.NAME_IS_EMPTY);
		if (name.isBlank())
			throw new Exception(PlaylistErrorMessanges.NAME_IS_BLANK);
		return true;
	}
	public static void filedsCheck(PlayListEntity playlist) {
//		if(pla)
	}
}
