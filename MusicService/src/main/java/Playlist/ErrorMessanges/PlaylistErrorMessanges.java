package Playlist.ErrorMessanges;

public interface PlaylistErrorMessanges {
	//Fields
	String ID_IS_NULL ="'Id' is null.";
	String ID_LESS_ZERRO ="'Id' less zerro.";
	String NAME_IS_NULL ="'Name' is null.";
	String NAME_IS_EMPTY ="'Name' is empty.";
	String NAME_IS_BLANK ="'Name' is blank.";
	String MAIN_IS_NULL ="'Main' is empty.";
	String USERID_LESS_ZERRO = "'UserId' less zerro.";
	String USERID_IS_NULL ="'UserId' is null."; 
	String LOCALDATE_IS_NULL ="'CreatedBy' is empty.";
	//Action
	String PLAYLIST_NOT_FOUND_WITH_ID = "Playlist with id '%d' was not found.";
	String PLAYLISTS_NOT_FOUND_WITH_NAME = "Playlists with name '%s' were not found.";
	String PLAYLISTS_NOT_FOUND_WITH_USER_ID = "Playlists with user_id '%d' were not found.";
	String PLAYLISTS_NOT_FOUND_WITH_USER_NAME = "Playlists with username '%s' were not found.";
	String MAIN_PLAYLIST_NOT_FOUND = "Main playlist not founted.";
	String DUPLICATED = "Duplicate playlists were found.";
}
