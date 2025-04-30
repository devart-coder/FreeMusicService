package Controllers;


import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Playlist.Check.PlaylistCheck;
import Playlist.DAO.PlayListBuilder;
import Playlist.DAO.PlayListEntity;
import Playlist.ErrorMessanges.PlaylistErrorMessanges;
import Playlist.Exceptions.DuplicatePlaylistsException;
import Playlist.Exceptions.PlayListNotFoundException;
import Playlist.Service.Interfaces.PlayListDetails;
import User.DAO.UserEntity;
import User.Service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/playlists")
public class PlayListsController extends BasePage{
	@GetMapping
	public String view() { return "playlists"; }
	
	@PostMapping
	public String playListActions(
		@RequestParam(required = false)
		String createButton,
		@RequestParam(required = false) 
		Long deleteButton,
		@RequestParam(required = false) 
		String mainButton,
		Authentication auth,
		Model page
	) throws Exception 
	{
		var user = userService.findOnceByName(auth.getName());
		try {
			if(Objects.nonNull(createButton)) {
				try {
				createPlaylist(user,createButton);
				}catch(Exception e) {
					page.addAttribute("createPlayListNameError", e.getMessage());
					return "playlists";
				}
			}
			if( Objects.nonNull(deleteButton) ) 
				playListDetails.deleteById(deleteButton);
			if( Objects.nonNull(mainButton) ) 
				playListDetails.setNewMain(mainButton);
		}catch(Exception e) {
			//TODO:SendErrorToTheModel
			log.error(e.getMessage());
		}
		page.addAttribute("mainPlayList",getMainPlayList(auth));
		page.addAttribute("playLists",getAllUserPlayLists(auth));		
		return "playlists";
	}
	@ModelAttribute("playLists")
	public Iterable<PlayListEntity> getAllUserPlayLists( Authentication auth ) throws Exception {
		var user = userService.findOnceByName(auth.getName());
		playListDetails.setUser(user);
		
		var p = playListDetails.findAllUserPlaylists();
		p.sort((o1, o2) -> Boolean.compare(o2.getMain(), o1.getMain() ) );
		
		return p;
	}
	private void createPlaylist(UserEntity user,String createButton) throws Exception {
			PlaylistCheck.nameIsValid(createButton);
			playListDetails.save( 
				PlayListBuilder.builder()
				.setName(createButton)
				.setUserEntity(user)
				.build() );
	}
}
