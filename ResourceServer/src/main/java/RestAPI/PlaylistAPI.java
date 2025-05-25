package RestAPI;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import Playlist.Check.PlaylistCheck;
import Playlist.DAO.PlayListEntity;
import Playlist.Exceptions.DuplicatePlaylistsException;
import Playlist.Exceptions.PlayListNotFoundException;
import Playlist.Exceptions.PlaylistNameIsNotValidException;
import Playlist.Service.PlayListService;
import Playlist.Service.Interfaces.PlayListDetails;
import User.Exceptions.UserNotFoundException;
import User.Service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@NoArgsConstructor
@Slf4j
public class PlaylistAPI {
	@Autowired
	private UserService userService;
	@Autowired
	private PlayListService playlistService;

	@GetMapping("/{user_id}/playlists/main")
	public PlayListEntity getMainPlaylistWithUser(@PathVariable Long user_id) throws UserNotFoundException {
			var user = userService.findOnceById(user_id);
			return playlistService.withUser(user).findOnceMainPlaylist();
	}
	@GetMapping("/playlists/{playlist_id}")
	public PlayListEntity getUserPLaylistById(@PathVariable Long user_id, @PathVariable Long playlist_id)
			throws UserNotFoundException, DuplicatePlaylistsException, PlayListNotFoundException {
		var user = userService.findOnceById(user_id);
		return playlistService.withUser(user).findOncePlaylistById(playlist_id);
	}
	@GetMapping("/{user_id}/playlists")
	public List<PlayListEntity> getUserPLaylists(@PathVariable Long user_id) throws UserNotFoundException {
		var user = userService.findOnceById(user_id);
		return playlistService.withUser(user).findAll();
	}
	@GetMapping("/playlists")
	public List<PlayListEntity> geyAllPLaylists() {
		return playlistService.findAll();
	}

	@PostMapping( value =  "/{user_id}/playlists/add" , consumes = MediaType.APPLICATION_JSON_VALUE )
	public PlayListEntity addNewPlaylist(
		@PathVariable(name  = "user_id") Long id,
		@RequestBody PlayListEntity newPlaylist)
			throws PlaylistNameIsNotValidException, UserNotFoundException {
		var user = userService.findOnceById(id);
		return playlistService.withUser(user).add(newPlaylist);
	}

	@PutMapping(value = "/playlists/{playlist_id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
	public PlayListEntity updatePlaylists(
			@PathVariable(name = "playlist_id") Long playlistId,
			@RequestBody PlayListEntity newPlaylist) 
			throws PlaylistNameIsNotValidException, PlayListNotFoundException {
		return playlistService.updateById(playlistId,newPlaylist);
	}

	@DeleteMapping(value = "/playlists/{playlist_id}")
	public void deletePlaylists( @PathVariable(name = "playlist_id") Long playlistId )
			throws PlaylistNameIsNotValidException, PlayListNotFoundException {
		 playlistService.deleteById(playlistId);
	}
}
