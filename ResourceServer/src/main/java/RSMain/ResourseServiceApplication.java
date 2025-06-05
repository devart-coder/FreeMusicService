package RSMain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(
	basePackages = {
		"RSMain"	
		,"RestAPI"
		,"ResourceServiceMain"
		,"User"
		,"Playlist"
	}
)
@EnableJpaRepositories(basePackages = {
		"User"
		,"Playlist"})
@EntityScan(basePackages = {
		"User"
		,"Playlist"})
@SpringBootApplication
public class ResourseServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResourseServiceApplication.class, args);
	}
}
