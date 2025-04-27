package Controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DAO.PlayList.PlayListBuilder;
import DAO.User.UserEntity;
import Services.PlayList.Exceptions.DuplicatePlaylistsException;
import Services.PlayList.Exceptions.PlayListNotFoundException;
import Services.PlayList.Interfaces.PlayListDetails;
import Services.User.Interfaces.UserServiceDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomePageController {
	@Autowired
	private UserServiceDetails userService;
	@Autowired
	private PlayListDetails playListDetails;
	private UserEntity u;
	
	@GetMapping
	public String home (
		@RequestParam (required = false) 
		String logout,
		Model page
	) {
		if(!Objects.isNull(logout)) 
			return "redirect:/login";
		return "home";
	}
	@ModelAttribute("mainPlayList")
	public String getMainPlayList( Authentication user ){
		try {
			u = userService.findOnceByName(user.getName());
			return playListDetails.findOnceUserMainPlaylist(u).toString();
		}catch (DuplicatePlaylistsException e) {
			log.error(e.getMessage());
			try {
				playListDetails.setOnlyDefaultPlayListAsMain(u);
			}catch (PlayListNotFoundException ex) {
				log.error(ex.getMessage());
				try {
					playListDetails.save(() -> 
						PlayListBuilder.builder()
							.setUserEntity(u)
							.build());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}catch(Exception ex) {
				log.warn(ex.getMessage());
			}
		}catch(Exception e) {
			log.warn(e.getMessage());
		}
		
		return "[Null]";
	}	
	
	@ModelAttribute("user")
	public String getUser(Authentication user) {
		return user.getName();
	}
}