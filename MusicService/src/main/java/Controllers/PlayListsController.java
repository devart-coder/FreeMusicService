package Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Playlist.DAO.PlayListBuilder;
import Playlist.DAO.PlayListEntity;
import Playlist.Exceptions.DuplicatePlaylistsException;
import Playlist.Exceptions.PlayListNotFoundException;
import Playlist.Service.Interfaces.PlayListDetails;
import User.DAO.UserEntity;
import User.Service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/playlists")
@Slf4j
public class PlayListsController {
	@Autowired
	private PlayListDetails playListsService;
	@Autowired
	private UserService userService;
	private UserEntity user;
	
	@GetMapping
	public String view( Model page) { return "playlists"; }
	
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
		playListsService.setUser(user);
		if(createButton != null) {
			if(createButton.isEmpty()) {
				page.addAttribute("createPlayListNameError","\"Name\" is empty.");
				return "playlists";
			}
			try {
				playListsService.save( 
					PlayListBuilder.builder()
					.setName(createButton)
					.setUserEntity(user)
					.build() );
			} catch (Exception e) {
				//TODO:SendErrorToTheModel
				log.error(e.getMessage());
			}
		}
		if( deleteButton != null ) {
			try {
				playListsService.deleteById(deleteButton);
			} catch (Exception e) {
				//TODO:SendErrorToTheModel
				log.error(e.getMessage());
			}
		}
		if( mainButton != null) {
			try {
				playListsService.setNewMain(mainButton);
			}catch(Exception e) {
				//TODO:SendErrorToTheModel
				log.error(e.getMessage());
			}
		} 
		page.addAttribute("mainPlayList",getMainPlayList(auth));
		page.addAttribute("playLists",getAllUserPlayLists(auth));		
		return "playlists";
	}
	@ModelAttribute("playLists")
	public Iterable<PlayListEntity> getAllUserPlayLists( Authentication auth ) throws Exception {
		var user = userService.findOnceByName(auth.getName());
		playListsService.setUser(user);
		
		var p = playListsService.findAllUserPlaylists();
		p.sort((o1, o2) -> Boolean.compare(o2.getMain(), o1.getMain() ) );
		
		return p;
	}

	@ModelAttribute("mainPlayList")
	public String getMainPlayList( Authentication  auth ) {
		try {
			var user = userService.findOnceByName(auth.getName());
			playListsService.setUser(user);
			return playListsService.findOnceUserMainPlaylist(user).getName();
		}catch(DuplicatePlaylistsException ex) {
			log.error(ex.getMessage());
			try {
				playListsService.setOnlyDefaultPlayListAsMain(user);
			} catch (PlayListNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e) {
				log.warn(e.getMessage());
			}

		}catch(Exception e) {
			log.warn(e.getMessage());
		}
		return "Null";
	}
	
	@ModelAttribute("user")
	public String getUsername( Authentication  user ) {
		return user.getName();
	}
}
