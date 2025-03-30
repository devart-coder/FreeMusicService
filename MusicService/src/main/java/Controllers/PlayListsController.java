package Controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import Repositories.PlayListsRepository;
import Repositories.UserRepository;

@Controller
@RequestMapping("/playlists")
public class PlayListsController {
	@Autowired
	private PlayListsRepository playListsRepository;
	@Autowired
	private UserRepository userRepository;
	
	
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
		var user = userRepository.findByUsername(auth.getName());
		if(createButton != null) {
			if(createButton.isEmpty()) {
				page.addAttribute("createPlayListNameError","\"Name\" is empty.");
				return "playlists";
			}
			var playList = new PlayListEntity();
			playList.setMain(false);
			playList.setName(createButton);
			user.getPlaylist().add(playList);
			playListsRepository.save(playList);
		}
		else if( deleteButton != null ) {
			if( user.getPlaylist().removeIf(p->p.getId().equals(deleteButton)&&!p.getName().equals("Default")) )
				playListsRepository.deleteById(deleteButton);
		}
		else if( mainButton != null) {
//			mainPlayListRepository.updatePlaylistNameByUsername(mainButton, auth.getName());
		} 
		page.addAttribute("mainPlayList",getMainPlayList(auth));
		page.addAttribute("playLists",getAllPlayLists(auth));		
		return "playlists";
	}
	@ModelAttribute("playLists")
	public Iterable<PlayListEntity> getAllPlayLists( Authentication auth ) {
		var user = userRepository.findByUsername(auth.getName());
		return user.getPlaylist();	
	}

	@ModelAttribute("mainPlayList")
	public String getMainPlayList( Authentication auth ) {
		var user=userRepository.findByUsername(auth.getName());
		var playLists = user.getPlaylist();
		String mainName = "Null";
		for(var playList : playLists) {
			if(playList.isMain())
				return playList.getName();
		}
		return mainName;
	}
	
	@ModelAttribute("user")
	public String getUsername( Authentication user ) {
		return user.getName();
	}
}
