package RestAPI;

import java.time.LocalDate;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import User.DAO.UserEntity;
import User.Exceptions.UserNotFoundException;
import User.Exceptions.UsernameNotValidException;
import User.Service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/")
@NoArgsConstructor
public class UserAPI {
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)  
	public UserEntity userCreation( @RequestBody UserEntity user) throws Exception{
		return  userService.create(user) ;
	}
	//Search
	@GetMapping("/user")
	public UserEntity userInfo(@RequestParam String username) throws UserNotFoundException, UsernameNotValidException{
		var user = userService.findOnceByName(username);
		log.info("User with id '"+user.getId()+"' was created.");
		return user;
	}
}
