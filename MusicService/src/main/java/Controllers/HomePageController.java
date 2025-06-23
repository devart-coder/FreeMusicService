package Controllers;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Handlers.ResponseExceptionHandlerFactory;
import User.DAO.UserEntity;
//import Playlist.Service.Interfaces.PlayListDetails;
//import User.DAO.UserEntity;
//import User.Service.Interfaces.UserServiceDetails;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@NoArgsConstructor
public class HomePageController extends BasePage{
	@GetMapping("/home")
	public String logout ( @RequestParam (required = false) String logout ){
		if(Objects.nonNull(logout)) {
			user.setLastEntry(LocalDateTime.now());
			var u = restClient
			.put()
			.uri("users/"+user.getId())
			.header(AUTHENTICATION, token.getTokenHeader())
			.accept(MediaType.APPLICATION_JSON)
			.body(Map.of("lastEntry",LocalDateTime.now()))
			.retrieve()
			.body(UserEntity.class);
//			.exchange(
//					ResponseExceptionHandlerFactory
//					.getInstance()
//					.setBodyType(String.class)
//					.handler(HttpStatus.INTERNAL_SERVER_ERROR));
//			log.warn("User:LastEntry: {}",u.getLastEntry().toString());
			return "redirect:/login";
		}
			return "home";
	}

}