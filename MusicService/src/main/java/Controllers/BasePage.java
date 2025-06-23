package Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import FMSMain.TokenHeader;
import Handlers.ResponseExceptionHandlerFactory;
import Playlist.DAO.PlayListEntity;
import User.DAO.UserEntity;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Controller
public class BasePage {
	protected final String AUTHENTICATION = "Authentication";
	
	@Autowired
	protected RestClient restClient;
	
	@Autowired
	protected UserEntity user;
	
	@Autowired
	protected PlayListEntity main;
	
	@Autowired
	protected TokenHeader token;

	@ModelAttribute("main_playlist")
	protected PlayListEntity mainPlaylist() {
		return main;
	}

	@ModelAttribute("user")
	protected String getUserName() {
		return user.getUsername();
	}
	
	
	// TODO::RealizeForAdminOnly
	@ModelAttribute("background")
	public String backgroundName() {
		// RandomBackground
		// TODO::NeedAddChooseBackgroundForAdmin
		var path = Paths.get("src/main/resources/static/images/backgrounds/").toAbsolutePath();
		try {
			var list = Files.list(path).filter(Files::isRegularFile).toList();
			var random = new Random();
			var number = random.nextInt(0, list.size());
			return list.get(number).getFileName().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "bg_3.jpg";
	}
}
