package Services.PlayList.Interfaces;

public interface PlayListDetails extends PlayListCreate,PlayListUpdate, PlayListSearch, PlayListDelete{
	boolean existsById(Long id) throws Exception;
	
	String ID_IS_EMPTY ="'Id' is empty."; 
	String USERID_IS_EMPTY ="'UserId' is emty."; 
	String NAME_IS_EMPTY ="'Name' is empty.";
	String USER_IS_EMPTY ="'User' is empty.";
}
