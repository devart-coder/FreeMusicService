package Playlist.Check;

public class PlaylistNameIsNotValidException extends Exception {

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	public PlaylistNameIsNotValidException(String nameIsNull) {
		super(nameIsNull);
	}

}
