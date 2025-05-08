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

//import Playlist.Service.Interfaces.PlayListDetails;
//import User.DAO.UserEntity;
//import User.Service.Interfaces.UserServiceDetails;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/home")
@NoArgsConstructor
public class HomePageController extends BasePage{
	@GetMapping
	public String home (
		@RequestParam (required = false) 
		String logout
	) {
		return Objects.nonNull(logout) ? "redirect:/login" : "home";
	}
}