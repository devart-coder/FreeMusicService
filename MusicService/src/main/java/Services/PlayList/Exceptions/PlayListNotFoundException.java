package Services.PlayList.Exceptions;

public class PlayListNotFoundException extends Exception {
	public PlayListNotFoundException(String message) {
		super(message);
	}
	@Override
	public String getMessage() {
		return super.getMessage();
	};
}
