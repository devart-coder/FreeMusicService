package MainApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@Configuration
@ComponentScan(
	basePackages = {
		"RestControllers"
		,"Controllers"
		,"Entities"
//		,"Repositories"
		,"Security"
	}
)
@EnableJpaRepositories(basePackages = "Repositories")
@EntityScan(basePackages = "Entities")
@SpringBootApplication
public class MusicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicServiceApplication.class, args);
	}

}
