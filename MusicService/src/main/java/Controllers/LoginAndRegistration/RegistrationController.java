package Controllers.LoginAndRegistration;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import Handlers.ResponseExceptionHandlerFactory;
import User.DAO.UserEntity;
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
		String password,
		Model page
	){
		var handler = ResponseExceptionHandlerFactory
				.getInstance()
				.setBodyType(UserEntity.class)
				.setModel(page)
				.setHolderName("message")
				.handler(HttpStatus.NOT_ACCEPTABLE);

		var user = client.post()
			.uri("users")
			.contentType(MediaType.APPLICATION_JSON)
			.body(Map.of("username",username,"password",password))
			.exchange(handler);
		if(Objects.isNull(user)) {
			return "register";
		}else {
			return "redirect:/login";
		}
	}
}
