package Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Entities.MainPlayListEntity;
import Entities.PlayListEntity;
import Entities.UserEntity;
import Repositories.MainPlayListRepository;
import Repositories.PlayListsRepository;
import Repositories.UserRepository;
import Services.PlayListService;
import lombok.extern.log4j.Log4j;


@Controller
@RequestMapping("/home")
public class HomePageController {
	private final String defaultPlayListName = "Default";
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PlayListsRepository playListRepository;
	@Autowired
	private MainPlayListRepository mainPlayListsRepository; 
	
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
		
		String username = user.getName();
		var u = userRepository.findByUsername(username);
		var p = u.getPlaylist();
//		if(p==null) {
			//TODO:PlayList:AddStaticMethod:DefaultPlayList
//			u.setPlaylist(List.of(PlayListService.withDefault()));
//		}
//		p = u.getPlaylist();
//		for(PlayList m : p) {
			logger.warn(u.toString());
//			logger.error(p==null ? "null" : p.toString());
//		}
		return "Def";
	}	
//		var mainResult = mainPlayListsRepository.findByUsername( username );
//		if(mainResult == null) {
//			var newUser = new User("DefaultUser","DefaultPassword");
//			var playList = new PlayList(newUser);
//			mainResult = new MainPlayList(playList); 
//			playListRepository.save(playList);
//			mainPlayListsRepository.save(mainResult);
//		}
//		return mainResult.getPlaylistname();
//		return "Default";
//	}	

	@ModelAttribute("user")
	public String getUser(Authentication user) {
		return user.getName();
	}
}