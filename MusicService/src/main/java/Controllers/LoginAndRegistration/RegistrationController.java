package Controllers.LoginAndRegistration;



import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.invoker.UriBuilderFactoryArgumentResolver;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

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
			var user = client.post()
			.uri("users")
			.contentType(MediaType.APPLICATION_JSON)
			.body(Map.of("username",username,"password",password))
			.retrieve()
//			.toEntity(UserEntity.class);
			.onStatus(arg -> arg == HttpStatus.NOT_ACCEPTABLE, (request, response) -> {
				log.warn("clientResponse: StatusText: "+response.getStatusText());
				log.warn("clientResponse: Body: "+response.getBody());
			})
			.body(UserEntity.class);
			
			return "redirect:/login";
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "register";
	}
}
