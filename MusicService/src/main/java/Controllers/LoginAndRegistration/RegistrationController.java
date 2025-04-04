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

import DAO.PlayLists.PlayListBuilder;
import DAO.PlayLists.PlayListEntity;
import DAO.User.UserEntity;
import DAO.User.UserEntityBuilder;
import DAO.User.Settings.UserSettingsBuilder;
import Repositories.PlayListsRepository;
import Repositories.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder encoder;
	
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
		var user = userRepo.findByUsername(username);
		if(user == null) {
			try {
				var newUser = UserEntityBuilder.defaulUserWith(username, encoder.encode(password));
				userRepo.save(newUser);
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
			return "redirect:/login";
		}
		else {
			logger.error("User '"+user.getUsername()+"' exists.");
			return "register";
		}
	}
}
