package Controllers.LoginAndRegistration;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Entities.User;
import Repositories.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {
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
			var newUser = new User();
			newUser.setUsername(username);
			newUser.setPassword(passwordEncoder.encode(password));
			newUser.setCreatedAt(new Date());
			newUser.setRole("ROLE_USER");
			newUser.setEnabled(true);
			userRepo.save(newUser);
			return "redirect:/login";
		}
		else {
			System.out.println("User '"+user.getUsername()+"' exists.");
			return "register";
		}
	}
}
