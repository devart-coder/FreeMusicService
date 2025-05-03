package Main;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import User.Service.UserService;

@RestController
@RequestMapping("/api/")
public class DemoController {
	@Autowired
	private UserService userService;
	@GetMapping("user")
	public Object demo(Authentication auth) {
		try {
			return userService.findOnceByName(auth.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return auth.getName();
	}
}
