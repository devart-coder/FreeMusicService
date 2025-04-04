package Controllers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DAO.PlayLists.PlayListBuilder;
import DAO.PlayLists.PlayListEntity;
import DAO.User.UserEntity;
import Repositories.PlayListsRepository;
import Repositories.UserRepository;
import Services.Implementations.PlayListsService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/playlists")
public class PlayListsController {
//	@Autowired
	private PlayListsService playListsService = new PlayListsService();
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
			var playlist = PlayListBuilder.builder()
				.setName(mainButton)
				.setUserEntity(user)
				.build();
			user.getPlaylists().add(playlist);//deleteFromUser
			playListsService.save(playlist);//addFromPLayList
		}
		else if( deleteButton != null ) {
//			if( user.getPlaylists()
//					.removeIf(p->p
//						.getId()
//						.equals(deleteButton)
//						&&!p.getName().equals("Default")) 
//				)//removeFromUser
//				playListsRepository.deleteById(deleteButton);//removeFromDB
		}
		else if( mainButton != null) {
//			for(var p : user.getPlaylists()) {
//				if(p.isMain()) {
//					try {
//						p.setMain(false);
//						playListsRepository.setMainById(false, p.getId());
//					}catch(Exception e) {
//						e.printStackTrace();
//					}
//				}
//				if(p.getName().equals(mainButton)) {
//					try {
//						p.setMain(true);
//						playListsRepository.setMainById(true, p.getId());
//					}catch(Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
		} 
		page.addAttribute("mainPlayList",getMainPlayList(auth));
		page.addAttribute("playLists",getAllUsersPlayLists(auth));		
		return "playlists";
	}
	@ModelAttribute("playLists")
	public Iterable<PlayListEntity> getAllUsersPlayLists( Authentication auth ) {
		var user = userRepository.findByUsername(auth.getName());
		//TODO::User:AddCheckByNull
		return user.getPlaylists().stream()
				.sorted((x,y) -> Boolean.compare(y.getMain(), x.getMain()))
				.toList();	
	}

	@ModelAttribute("mainPlayList")
	public String getMainPlayList( Authentication auth ) {
		var context = SecurityContextHolder.getContext();
		System.out.println(context.getAuthentication());
		var user = userRepository.findByUsername(auth.getName());
		//TODO:User:AddCheckByNull
		var playLists = user.getPlaylists();
		for(var playList : playLists) {
			if(playList.getMain())
				return playList.getName();
		}
		return "[Null]";
	}
	
	@ModelAttribute("user")
	public String getUsername( Authentication user ) {
		return user.getName();
	}
}
