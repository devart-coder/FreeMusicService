package RestAPI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import User.DAO.UserEntity;
import User.Exceptions.UserNotFoundException;
import User.Service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/")
@NoArgsConstructor
@Slf4j
public class UserAPI {
	@Autowired
	private UserService userService;
	//Actions:
		//AddUser
		//DeleteUser
		//Update:
			//Username
			//Password
		//FindUser
	//Examples:
		//POST 'localhost:7070/api/users/add?username="username"&password="password"'
		//GET 'localhost:7070/api/"username"'
//	@GetMapping("/{username}/info")
//	public UserEntity userInfo(@PathVariable String username) throws UserNotFoundException {
//		try {
//			return userService.findOnceByName(username);
//		} catch (UserNotFoundException e) {
//			log.error(e.getMessage());
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message: ",e.getMessage()));
//		}
//		return null;
//	}
}
