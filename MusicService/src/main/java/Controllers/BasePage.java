package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;

import Playlist.Service.Interfaces.PlayListDetails;
import User.DAO.UserEntity;
import User.Service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class BasePage {
	@Autowired
	protected PlayListDetails playListDetails;
	@Autowired
	protected UserService userService;
	
	@ModelAttribute("mainPlayList")
	protected String getMainPlayList( Authentication user ){
		try {
			var u = userService.findOnceByName(user.getName());
			return playListDetails.findOnceUserMainPlaylist(u).getName();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "Null";
	}	
	@ModelAttribute("user")
	protected String getUser(Authentication user) {
		try {
			return userService.findOnceByName(user.getName()).getUsername();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "Null";
	}
}
