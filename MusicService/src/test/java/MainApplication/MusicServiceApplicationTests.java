package MainApplication;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MusicServiceApplicationTests {
	@Autowired
	private MockMvc mock;
	@Test
	void loginFormChecker() throws Exception {
		mock.perform(
			formLogin().user("devart").password("devart")
		)
		.andExpect( authenticated() )
		.andExpect( redirectedUrl("/home") )
		.andExpect( status().isFound() );
	}

}
