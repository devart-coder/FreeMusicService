package Controllers;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DAO.PlayList.PlayListBuilder;
import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;
import Repositories.PlayListsRepository;
import Repositories.UserRepository;
import Security.SecureUser;
import Services.PlayList.PlayListService;
import Services.PlayList.Interfaces.PlayListDetails;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/playlists")
@Slf4j
public class PlayListsController {
	@Autowired
	private PlayListDetails playListsService;
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
	) throws Exception 
	{
		var user = userRepository.findByUsername(auth.getName());
		playListsService.setUser(user);
		if(createButton != null) {
			if(createButton.isEmpty()) {
				page.addAttribute("createPlayListNameError","\"Name\" is empty.");
				return "playlists";
			}
			try {
				playListsService.save( ()->
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
				playListsService.deleteByIdWithNotMainNotDefaultName(deleteButton, "Default");
			} catch (Exception e) {
				//TODO:SendErrorToTheModel
				log.error(e.getMessage());
			}
		}
		if( mainButton != null) {
			try {
				playListsService.setNewMain(user,mainButton);
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
		playListsService.findAllByUserId()
		var p = userRepository
			.findByUsername(auth.getName())
			.getPlaylists();
		p.sort((o1, o2) -> Boolean.compare(o2.getMain(), o1.getMain() ) );
		return p;
	}

	@ModelAttribute("mainPlayList")
	public String getMainPlayList( Authentication  auth ) throws Exception {
		var user = userRepository.findByUsername(auth.getName());
		return playListsService.findOnceMainPlaylist(user).getName();
	}
	
	@ModelAttribute("user")
	public String getUsername( Authentication  user ) {
		return user.getName();
	}
}
