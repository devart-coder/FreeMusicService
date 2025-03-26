package Main;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {
	@GetMapping("/whoami")
	public Object demo() {
		var jwt=SecurityContextHolder
		.getContext()
		.getAuthentication();
		return Map.of("Message","Hello, "+jwt.getName());
	}
}
