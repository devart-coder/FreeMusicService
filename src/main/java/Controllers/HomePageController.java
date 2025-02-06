package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Entities.MainPlayList;
import Entities.PlayList;
import Entities.User;
import Repositories.MainPlayListRepository;
import Repositories.PlayListsRepository;


@Controller
@RequestMapping("/home")
public class HomePageController {
	private final String defaultPlayListName = "Default";

	@Autowired
	private PlayListsRepository playListRepository;
	@Autowired
	private MainPlayListRepository mainPlayListsRepository; 
	
	@GetMapping
	public String home (
		@RequestParam (required = false) 
		String logout,
		@AuthenticationPrincipal User user,
		Model page
	) {
		if(logout != null) 
			return "redirect:/login";
		return "home";
	}
	@ModelAttribute("mainPlayList")
	public String getMainPlayList( @AuthenticationPrincipal User user ) {
		String username = user.getUsername();
		var mainResult = mainPlayListsRepository.findByUsername( username );
		if(mainResult == null) {
			var playList = new PlayList(defaultPlayListName, username);
			mainResult = new MainPlayList(playList); 
			playListRepository.save(playList);
			mainPlayListsRepository.save(mainResult);
		}
		return mainResult.getPlaylistName();
	}	

	@ModelAttribute("user")
	public String getUser(@AuthenticationPrincipal User user) {
		return user.getUsername();
	}
}