package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Entities.PlayList;
import Entities.User;
import Repositories.MainPlayListRepository;
import Repositories.PlayListsRepository;

@Controller
@RequestMapping("/playlists")
public class PlayListsController {
	@Autowired
	private PlayListsRepository playListsRepository;
	@Autowired
	private MainPlayListRepository mainPlayListRepository;
	
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
		@AuthenticationPrincipal 
		User user,
		Model page
	) {
		if(createButton != null) {
			if(createButton.isEmpty()) {
				page.addAttribute("createPlayListNameError","\"Name\" is empty.");
				return "playlists";
			}
			playListsRepository.save(new PlayList(createButton, user.getUsername()));
		}else if( deleteButton != null && !compare(deleteButton, user.getUsername() ) )
			playListsRepository.deleteById(deleteButton);
		else if( mainButton != null) 
			mainPlayListRepository.updatePlaylistNameByUsername(mainButton, user.getUsername());

		page.addAttribute("mainPlayList",getMainPlayList(user));
		page.addAttribute("playLists",getAllPlayLists(user));		
		return "playlists";
	}
	private boolean compare( Long deleteButton, String username ) {
		return playListsRepository.findById(deleteButton)
				.orElseThrow()
				.getName()
				.equals(
					mainPlayListRepository
					.findByUsername(username)
					.getPlaylistName()
				);
	}

	@ModelAttribute("playLists")
	public Iterable<PlayList> getAllPlayLists( @AuthenticationPrincipal User user ) {
		return 
			playListsRepository
			.findAllByUsername( user.getUsername() );
	}

	@ModelAttribute("mainPlayList")
	public String getMainPlayList( @AuthenticationPrincipal User user ) {
		return 
			mainPlayListRepository
			.findByUsername( user.getUsername() )
			.getPlaylistName();
	}
	
	@ModelAttribute("user")
	public String getUsername( @AuthenticationPrincipal User user ) {
		return 
			user.getUsername();
	}
}
