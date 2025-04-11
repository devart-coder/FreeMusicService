package MainApplication;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
	classes = {TestConfig.class,MusicServiceApplication.class,PlayListJpaTesting.class}
)
class MusicServiceApplicationTests { 	

}
