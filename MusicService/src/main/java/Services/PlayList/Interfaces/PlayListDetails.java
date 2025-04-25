package Services.PlayList.Interfaces;

public interface PlayListDetails extends PlayListCreate,PlayListUpdate, PlayListSearch, PlayListDelete{
	boolean existsById(Long id) throws Exception;
	static final String defaulName = "Default";
}
