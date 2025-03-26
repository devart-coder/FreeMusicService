package Main;

//package Main;

//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.web.servlet.MockMvc;
//
//@SpringBootTest
//class AuthorizationServerApplicationTests {
//
//	@Autowired
//	private MockMvc mock;
//	@Autowired
//	private Authentication auth;
//	@Test
//	public void authHttpBasicTest() throws Exception {
//		mock.perform(get("/login").with(httpBasic("devart", "devart")))
//		.andExpect(status().isOk());
//	}
//	@Test
//	public void authFormLoginTest() throws Exception {
//		mock.perform(formLogin("/login").user("devart").password("devart"))
//		.andExpect(redirectedUrl("/"))
//		.andExpect(status().isFound());
//	}
//}
