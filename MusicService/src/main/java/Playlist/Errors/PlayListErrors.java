package Playlist.Errors;

public interface PlayListErrors {
	//Fields
	String ID_IS_NULL ="'Id' field is null.";
	String ID_LESS_ZERRO ="'Id' less zerro.";
	String NAME_IS_NULL ="'Name' field is null.";
	String NAME_IS_EMPTY ="'Name' field is empty.";
	String MAIN_IS_NULL ="'Main' field is empty.";
	String USERID_LESS_ZERRO = "'UserId' less zerro.";
	String USERID_IS_NULL ="'UserId' field is null."; 
	String LOCALDATE_IS_NULL ="'CreatedBy' field is empty.";
	//Action
	String NULL_ARGUMENT = "Argument is 'null'.";
	String PLAYLIST_NOT_FOUND_WITH_ID = "Playlist with id '%d' was not found.";
	String PLAYLISTS_NOT_FOUND_WITH_NAME = "Playlists with name '%s' were not found.";
	String PLAYLISTS_NOT_FOUND_WITH_USER_ID = "Playlists with user_id '%d' were not found.";
	String PLAYLISTS_NOT_FOUND_WITH_USER_NAME = "Playlists with username '%s' were not found.";
	String DUPLICATED = "Duplicate playlists were found.";
	String MAIN_PLAYLIST_NOT_FOUND = "Main playlist not founted.";
}
