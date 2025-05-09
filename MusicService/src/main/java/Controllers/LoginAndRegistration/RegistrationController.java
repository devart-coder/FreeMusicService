package Controllers.LoginAndRegistration;



import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import User.DAO.UserEntity;
import User.DAO.UserEntityBuilder;
//import User.DAO.UserEntityBuilder;
//import User.Exceptions.UserNotFoundException;
//import User.Service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
	private RestClient client = RestClient.builder()
			.baseUrl("http://localhost:7070/api/")
			.build();
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
			var result = client.post()
			.uri("user")
			.contentType(MediaType.APPLICATION_JSON)
			.body(UserEntityBuilder.defaulUserWith(username, password))
			.retrieve();
			var user = result.body(UserEntity.class);
			log.warn(user.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
		}
//		try {
//			var user = userService.findOnceByName(username);
//			log.error("User with name '"+user.getUsername()+"' exists.");
//			return "register";
//		}catch(UserNotFoundException e) {
//			try {
//				userService.save(UserEntityBuilder.defaulUserWith(username, password));
//			} catch (Exception ex) {
//				log.error(ex.getMessage());
//			}
//		}catch(Exception e) {
//			log.error(e.getMessage());
//		}
//		return "redirect:/login";
		return null;
	}
}
