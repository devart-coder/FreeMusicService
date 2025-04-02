package Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Repositories.PlayListsRepository;
import Repositories.UserRepository;

@Controller
@RequestMapping("/home")
public class HomePageController {
	private final String defaultPlayListName = "Default";
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PlayListsRepository playListRepository;
	
	@GetMapping
	public String home (
		@RequestParam (required = false) 
		String logout,
		Model page
	) {
		if(logout != null) 
			return "redirect:/login";
		return "home";
	}
	@ModelAttribute("mainPlayList")
	public String getMainPlayList( Authentication user ) {
		var u = userRepository.findByUsername(user.getName());
		var p = u.getPlaylists();
		//TODO::PLayLists:AddCheckByNull
		for(var playlist : p) {
			if(playlist.isMain())
				return playlist.getName();
		}
		return "[Null]";
	}	
	
	@ModelAttribute("user")
	public String getUser(Authentication user) {
		return user.getName();
	}
}