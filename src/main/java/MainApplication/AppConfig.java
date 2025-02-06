package MainApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@ComponentScan(
	basePackages = {
		"RestControllers",
		"Controllers",
		"Entities",
		"MainApplication",
		"Repositories",
		"Services"
	}
)
@EnableJdbcRepositories(basePackages = "Repositories")
public class AppConfig{ }
