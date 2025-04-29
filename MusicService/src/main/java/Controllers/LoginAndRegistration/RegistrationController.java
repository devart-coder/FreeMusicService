package Controllers.LoginAndRegistration;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import User.DAO.UserEntityBuilder;
import User.Exceptions.UserNotFoundException;
import User.Service.UserService;
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
