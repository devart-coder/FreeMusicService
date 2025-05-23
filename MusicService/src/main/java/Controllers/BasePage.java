package Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;

import org.apache.catalina.valves.rewrite.RandomizedTextRewriteMap;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestClient;

import Playlist.DAO.PlayListEntity;
import User.DAO.UserEntity;
//import Playlist.Service.PlayListService;
//import Playlist.Service.Interfaces.PlayListDetails;
//import User.DAO.UserEntity;
//import User.Service.UserService;
//import User.Service.Interfaces.UserServiceDetails;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Controller
public class BasePage {
	@Autowired
	protected OAuth2AuthorizedClientManager manager;
	@Autowired
	protected RestClient client;
	protected Long userId;
	protected OAuth2AuthorizedClient getOAuth2Client( Authentication auth ) {
		
		var request = OAuth2AuthorizeRequest
			.withClientRegistrationId("FMS")
			.principal(auth)
			.build();
		
		return manager.authorize(request);
	}
	@ModelAttribute("mainPlayList")
	protected String getMainPlayList( Authentication auth ){
			var cli = getOAuth2Client(auth);
			if(cli == null) {
				log.error("Cli is null");
				return null;
			}
			
			var token = cli.getAccessToken();
			if(token == null) {
				log.error("Token is null");
				return null;
			}
			if(userId == null)
				getUserName(auth);
			var main = client
				.get()
				.uri(userId+"/playlists/main")
				.header("Authorize",token.getTokenType().toString()+token.getTokenValue())
				.retrieve()
				.body(PlayListEntity.class);

			log.info(main.getName());
			return main.getName();
	}	
	@ModelAttribute("user")
	protected String getUserName(Authentication auth) {
		var cli = getOAuth2Client(auth);
			if(cli == null) {
				log.error("Cli is null");
				return null;
			}
			
			var token = cli.getAccessToken();
			if(token == null) {
				log.error("Token is null");
				return null;
			}

			var user = client
				.get()
				.uri("users/"+auth.getName())
				.header("Authorize",token.getTokenType().toString()+token.getTokenValue())
				.retrieve()
				.body(UserEntity.class);

			log.info(user.toString());
			userId = user.getId();
			return user.getUsername();
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
			return list.get(number).getFileName().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "bg_3.jpg";
	}
}
