package Main;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationServerApplicationTests {

	@Autowired
	private MockMvc mock;
//	
//	@Test
//	public void userLoginTest() throws Exception {
//		mock.perform(
//			formLogin("/login")
//			.user("devart")
//			.password("devart")
//		)
//		.andExpect(redirectedUrl("/"))
//		.andExpect(status().isFound());
//	}
//	@Test
//	public void adminLoginTest() throws Exception {
//		mock.perform(
//			formLogin("/login")
//			.user("admin")
//			.password("admin")
//		)
//		.andExpect(redirectedUrl("/"))
//		.andExpect(status().isFound());
//	}
}
