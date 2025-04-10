package MainApplication;

import org.junit.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;

@ComponentScan(
	basePackages = {
		"RestControllers"
		,"Controllers"
		,"DAO"
		,"Security"
		,"Services"
	}
)
@EnableJpaRepositories(basePackages = "Repositories")
@EntityScan(basePackages = "DAO")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class PlayListServiceTests {
//	private PlayListJpaTesting jpaTest = new PlayListJpaTesting();
	
	@Test
	public void test() throws Exception {
//		jpaTest.saveEntityTest();
//		jpaTest.saveEntityWithNotSavedExceptionTest();
	}
}
