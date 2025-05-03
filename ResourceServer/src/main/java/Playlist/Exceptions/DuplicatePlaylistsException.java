package Playlist.Exceptions;

public class DuplicatePlaylistsException extends Exception {
	public DuplicatePlaylistsException(String message) {
		super(message);
	}
	@Override
	public String getMessage() {
		return super.getMessage();
	};
}
