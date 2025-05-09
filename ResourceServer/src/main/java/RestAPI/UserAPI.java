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

import RestAPI.API.User.UserAPICreation;
import User.DAO.UserEntity;
import User.DAO.UserEntityBuilder;
import User.DAO.Settings.UserSettings;
import User.DAO.Settings.UserSettingsBuilder;
import User.Exceptions.UserNotFoundException;
import User.Service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/")
@NoArgsConstructor
@Slf4j
public class UserAPI implements UserAPICreation{
	@Autowired
	private UserService userService;
	//Creation
	@Override
	@PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<?> userCreationWithBody( @RequestBody UserEntity user){
		try {
			user = UserEntityBuilder.defaulUserWith(user.getUsername(), user.getPassword());
			var newUser = userService.add(user);
			log.info("User with id '"+newUser.getId()+"' was created.");
			return ResponseEntity.ok( newUser );
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("ErrorMessage: ",e.getMessage()));
		}
	}
	@Override
	@PostMapping(value = "/user")  
	public ResponseEntity<?> userCreationWithParams( 
			@RequestParam String username
			,@RequestParam String password){
		try {
			var user=UserEntityBuilder.defaulUserWith(username, password);
			var newUser = userService.add(user);
			log.info("User with id '"+user.getId()+"' was saved.");
			return ResponseEntity.ok( newUser );
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("ErrorMessage: ",e.getMessage()));
		}
	}
	//Search
	@GetMapping("/{username}/info")
	public ResponseEntity<?> userInfo(@PathVariable String username){
		try {
			var user = userService.findOnceByName(username);
			log.info("User with id '"+user.getId()+"' was created.");
			return ResponseEntity.ok(user);
		} catch ( Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("ErrorMessage: ",e.getMessage()));
		}
	}
}
