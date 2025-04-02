package Controllers.LoginAndRegistration;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Entities.PlayListEntity;
import Entities.UserEntity;
import Repositories.PlayListsRepository;
import Repositories.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	) {
		var user = userRepo.findByUsername(username);
		if(user == null) {
			var newUser = new UserEntity( username, passwordEncoder.encode(password));
			userRepo.save(newUser);
			return "redirect:/login";
		}
		else {
			logger.error("User '"+user.getUsername()+"' exists.");
			return "register";
		}
	}
}
