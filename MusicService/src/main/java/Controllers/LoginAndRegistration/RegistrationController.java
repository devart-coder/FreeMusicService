package Controllers.LoginAndRegistration;

import static DAO.User.UserEntityBuilder.builder;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DAO.PlayList.PlayListBuilder;
import DAO.PlayList.PlayListEntity;
import DAO.User.UserEntity;
import DAO.User.UserEntityBuilder;
import DAO.User.Settings.UserSettingsBuilder;
import Repositories.PlayListsRepository;
import Repositories.UserRepository;
import Services.User.UserService;
import Services.User.Exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String registerForm() {
		return "register";
	}
	@PostMapping
	public String authProcess( 
		@RequestParam(required = false)
		String username,
		@RequestParam(required = false)
		String password
	){
		try {
			var user = userService.findOnceByName(username);
			log.error("User with name '"+user.getUsername()+"' exists.");
			return "register";
		}catch(UserNotFoundException e) {
			try {
				userService.save(UserEntityBuilder.defaulUserWith(username, password));
			} catch (Exception ex) {
				log.error(ex.getMessage());
			}
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return "redirect:/login";
	}
}
