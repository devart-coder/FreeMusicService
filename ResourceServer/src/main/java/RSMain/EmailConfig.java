package RSMain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import Email.EmailProperties;
import Email.Service.EmailService;
import Email.Service.EmailServiceImpl;

@Configuration
public class EmailConfig {
	@Bean
	@ConfigurationProperties(prefix = "spring.mail") 
	EmailProperties emailProperties() {
		return new EmailProperties();
	}
	@Bean
	EmailService emailSender(JavaMailSender sender) {
		return new EmailServiceImpl(sender);
	}
}
