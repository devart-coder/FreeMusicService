package RestAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		// 'http://localhost:7070/api/{"user_id"}/playlists/main'
		// POST 'http://localhost:7070/api/{"user_id"}/playlists?name=""'
	@Autowired
	private UserService userService;
	@Autowired
	private PlayListService playlistService;

//	@GetMapping("{user_id}/playlists/main")
//	public String getMainPlaylistWithUser(
//			@PathVariable(name = "user_id") Long userId
////			,Authentication auth
//			) {
////		if(auth!=null)
////			log.warn(auth.getName());
//		try {
//		var user = userService.findOnceById(userId);
//		return playlistService.withUser(user).findOnceMainPlaylist().getName();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	@GetMapping("{username}/playlists/main")
	public String getMainPlaylistWithUser(
			@PathVariable String username,
			Authentication auth) {
		try {
			if(auth!=null)
				log.warn(auth.getName());
			var user = userService.findOnceByName(username);
			return playlistService.withUser(user).findOnceMainPlaylist().getName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
