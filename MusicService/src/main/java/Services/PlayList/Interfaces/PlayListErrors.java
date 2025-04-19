package Services.PlayList.Interfaces;

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
	String NOT_FOUNTED_WITH_ID = "Playlist with id '%d' was not found.";
	String NOT_FOUNTED_WITH_NAME = "Playlists with name '%s' was not found.";
	String NOT_FOUNTED_WITH_USER_ID = "Playlists with user_id '%d' was not found.";
}
