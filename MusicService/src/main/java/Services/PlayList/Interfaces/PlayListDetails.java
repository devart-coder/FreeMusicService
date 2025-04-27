package Services.PlayList.Interfaces;

import DAO.User.UserEntity;

public interface PlayListDetails extends PlayListCreate,PlayListUpdate, PlayListSearch, PlayListDelete{
	void setNewMain( String mainButton) throws Exception;
	UserEntity setUser( UserEntity user) throws Exception;
	void setOnlyDefaultPlayListAsMain(UserEntity user) throws Exception;
	static final String DEFAULT_NAME = "Default";
}
