package Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;

import org.apache.catalina.valves.rewrite.RandomizedTextRewriteMap;
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
			user = userService.findOnceByName(auth.getName());
			return 
				playListDetails
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
			user = userService.findOnceByName(auth.getName());
			return user.getUsername();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "Null";
	}
	//TODO::RealizeForAdminOnly
	@ModelAttribute("background")
	public String backgroundName() {
		//RandomBackground
		//TODO::NeedAddChooseBackgroundForAdmin
		var path = Paths.get("src/main/resources/static/images/backgrounds/").toAbsolutePath();
		try {
			var list = Files.list(path)
					.filter(Files::isRegularFile)
					.toList();
			var random = new Random();
			var number = random.nextInt(0,list.size());
			log.info(list.get(number).getFileName().toString());
			return list.get(number).getFileName().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "bg_3.jpg";
	}
}
