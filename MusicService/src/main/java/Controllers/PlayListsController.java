package Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Entities.PlayListEntity;
import Entities.UserEntity;
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
	) 
	{
//		if(createButton != null) {
//			if(createButton.isEmpty()) {
//				page.addAttribute("createPlayListNameError","\"Name\" is empty.");
//				return "playlists";
//			}
//			var user = new User("defaultUsername","defaultPassword");
//			playListsRepository.save(new PlayList(user));
//		}else if( deleteButton != null && !compare(deleteButton, auth.getName() ) )
//			playListsRepository.deleteById(deleteButton);
//		else if( mainButton != null) 
////			mainPlayListRepository.updatePlaylistNameByUsername(mainButton, auth.getName());
//
//		page.addAttribute("mainPlayList",getMainPlayList(auth));
//		page.addAttribute("playLists",getAllPlayLists(auth));		
		return "playlists";
	}
//	private boolean compare( Long deleteButton, String username ) {
////		return playListsRepository.findById(deleteButton)
////				.orElseThrow()
////				.getName()
////				.equals(
////					mainPlayListRepository
////					.findByUsername(username)
////					.getPlaylistname()
////				);
//	}

	@ModelAttribute("playLists")
	public Iterable<PlayListEntity> getAllPlayLists( Authentication auth ) {
		var user = new UserEntity("DefaultUser","DefaultPassword");
		user.setUsername("Devart");
		user.setPassword("{noop}Devart");
//		user.setPlaylist(List.of(new PlayListEntity()));
		return  user.getPlaylist();
//			playListsRepository
//			.findAllByUser( 1l )
//			.orElse(new User())
//			.getPlaylist();
	}

	@ModelAttribute("mainPlayList")
	public String getMainPlayList( Authentication auth ) {
//		return 
//			mainPlayListRepository
//			.findByUsername( auth.getName() )
//			.getPlaylistname();
		return "Default";
	}
	
	@ModelAttribute("user")
	public String getUsername( Authentication user ) {
		return user.getName();
	}
}
