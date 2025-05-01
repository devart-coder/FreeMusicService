package Controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;

import Playlist.Service.PlayListService;
import Playlist.Service.Interfaces.PlayListDetails;
import User.DAO.UserEntity;
import User.Service.UserService;
import User.Service.Interfaces.UserServiceDetails;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class BasePage {
	@Autowired
	protected PlayListService playListDetails;
	@Autowired
	protected UserServiceDetails userService;
	protected UserEntity user;
	@ModelAttribute("mainPlayList")
	protected String getMainPlayList( Authentication auth ){
		try {
			if(Objects.isNull(user))
				user = userService.findOnceByName(auth.getName());
			return playListDetails
					.withUser(user)
					.findOnceMainPlaylist()
					.getName();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "Null";
	}	
	@ModelAttribute("user")
	protected String getUserName(Authentication auth) {
		try {
			if(Objects.isNull(user))
				user = userService.findOnceByName(auth.getName());
			return user.getUsername();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "Null";
	}
}
