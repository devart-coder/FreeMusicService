package Services.PlayList.Interfaces;

public interface PlayListErrors {
	
	String ID_IS_EMPTY ="'Id' field is empty.";
	String ID_LESS_ZERRO ="'Id' less zerro.";
	
	String NAME_IS_EMPTY ="'Name' field is empty.";
	String MAIN_IS_EMPTY ="'Main' field is empty.";
	String USERID_IS_EMPTY ="'UserId' field is emty."; 
	String LOCALDATE_IS_EMPTY ="'CreatedBy' field is empty.";
	
	String NULL_ARGUMENT = "Argument is 'null'.";
	String NOT_FOUNT_WITH_ID = "Playlist with id '%s' was not found.";
}
