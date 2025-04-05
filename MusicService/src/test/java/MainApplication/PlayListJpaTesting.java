package MainApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import Services.Implementations.PlayListsService;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PlayListJpaTesting {
	@Autowired 
	private PlayListsService service;
	
	@Test 
	public void someTest() {

	}
	
	
	
}
