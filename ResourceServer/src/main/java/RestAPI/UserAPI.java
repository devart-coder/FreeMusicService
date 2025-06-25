package RestAPI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Email.EmailProperties;
import Email.Mail;
import Email.Service.EmailService;
import Playlist.Check.PlaylistCheck;
import User.DAO.UserEntity;
import User.DAO.UserEntityBuilder;
import User.Exceptions.PasswordNotValidException;
import User.Exceptions.UserDuplicateException;
import User.Exceptions.UserNotFoundException;
import User.Exceptions.UsernameNotValidException;
import User.Service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/users")
@NoArgsConstructor
public class UserAPI {
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailProperties emailProperties;
	
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)  
	public UserEntity userCreation( @RequestBody UserEntity user) throws Exception{
		var newUser = userService.create(user);

		var mail = new Mail();
		mail.setDestinations(emailProperties.getDestinations());
		mail.setSubject( "New user was registered." );
		mail.setText("* User [\"id\":%d,\"name\":\"%s\"] was created at '%s'.\nAll users: %d\n"
			.formatted(
				newUser.getId()
				,newUser.getUsername()
				,newUser.getCreatedBy()
				,userService.findAll().size() )
		);
		emailService.sendSimpleMessage(mail);
		return newUser;
	}
	
	@GetMapping("{username}")
	public UserEntity userInfo(@PathVariable String username) throws Exception{
		return userService.findOnceByName(username);
	}
	
	@GetMapping
//	@PreFilter("hasRole(\"ADMIN\")")
	public List<UserEntity> allUsersInfo() throws Exception{
		return userService.findAll();
	}

	@PutMapping(value = "{user_id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserEntity userUpdate(
			@PathVariable Long user_id,
			@RequestBody UserEntity newUser)
				throws Exception 
	{
		log.info("UserAPI:Put:Body {}", newUser.toString());
		return userService.updateByUserId(user_id,newUser);
	}	
	
	@DeleteMapping("{user_id}")
//	@PreFilter("hasRole(\"ADMIN\")")
	public void userDelete(@PathVariable Long user_id) 
		throws Exception
	{
		userService.deleteById(user_id);
		var mail = new Mail();
		mail.setDestinations(emailProperties.getDestinations());
		mail.setSubject( "New user was registered." );
		mail.setText("* User with [\"id\":%d] was deleted."
			.formatted(user_id));
	}
}
