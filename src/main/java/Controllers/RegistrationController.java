package Controllers;

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
	private final UserRepository userRepo;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder){
		this.userRepo=userRepo;
		this.passwordEncoder=passwordEncoder;
	}
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
		var user = new User(username, passwordEncoder.encode(password));
		user.setCreationTime(new Date());
		System.out.println("FromPOST: " +user );
		userRepo.save(user);
		return "redirect:/login";
	}
}
