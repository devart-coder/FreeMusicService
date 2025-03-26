package MainApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
@Configuration
@ComponentScan(
	basePackages = {
		"RestControllers"
		,"Controllers"
		,"Entities"
		,"Repositories"
		,"Security"
	}
)
@EnableJdbcRepositories(basePackages = "Repositories")
@SpringBootApplication
public class MusicLocalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicLocalServiceApplication.class, args);
	}

}
