package RestAPI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Playlist.DAO.PlayListEntity;
import Playlist.Service.PlayListService;
import Playlist.Service.Interfaces.PlayListDetails;
import User.Service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/")
@NoArgsConstructor
@Slf4j
public class PlaylistAPI {
	//getPlaylistEntityById;
	//Example:
		// 'http://localhost:7070/api/{"username"}/playlists/main'
		// 'http://localhost:7070/api/{"username"}/playlists/update?name='name'&main=true'
		// 'http://localhost:7070/api/{"username"}/playlists'
		// 'http://localhost:7070/api/{"username"}/playlists/make?main="playlistNameOrId"'

		// POST 'http://localhost:7070/api/{"user_id"}/playlists?name=""'
	@Autowired
	private UserService userService;
	@Autowired
	private PlayListService playlistService;

	@GetMapping("{username}/playlists/main")
	public String getMainPlaylistWithUser(@PathVariable String username) {
		try {
			var user = userService.findOnceByName(username);
			return playlistService.withUser(user).findOnceMainPlaylist().getName();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}
	@GetMapping("{username}/playlists")
	public List<String> getUserPLaylists(@PathVariable String username) {
		try {
			var user = userService.findOnceByName(username);
			return playlistService.withUser(user).findAll().stream().map(p->p.getName()).toList();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}
}
