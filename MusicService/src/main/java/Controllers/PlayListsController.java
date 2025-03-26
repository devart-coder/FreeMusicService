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

import Entities.PlayList;
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
		Authentication auth,
		Model page
	) {
		if(createButton != null) {
			if(createButton.isEmpty()) {
				page.addAttribute("createPlayListNameError","\"Name\" is empty.");
				return "playlists";
			}
			playListsRepository.save(new PlayList(createButton, auth.getName()));
		}else if( deleteButton != null && !compare(deleteButton, auth.getName() ) )
			playListsRepository.deleteById(deleteButton);
		else if( mainButton != null) 
			mainPlayListRepository.updatePlaylistNameByUsername(mainButton, auth.getName());

		page.addAttribute("mainPlayList",getMainPlayList(auth));
		page.addAttribute("playLists",getAllPlayLists(auth));		
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
	public Iterable<PlayList> getAllPlayLists( Authentication auth ) {
		return 
			playListsRepository
			.findAllByUsername( auth.getName() );
	}

	@ModelAttribute("mainPlayList")
	public String getMainPlayList( Authentication auth ) {
		return 
			mainPlayListRepository
			.findByUsername( auth.getName() )
			.getPlaylistName();
	}
	
	@ModelAttribute("user")
	public String getUsername( Authentication user ) {
		return user.getName();
	}
}
