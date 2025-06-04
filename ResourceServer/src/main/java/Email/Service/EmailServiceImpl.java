package Email.Service;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import Email.EmailProperties;
import Email.Mail;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	@Autowired
	private final JavaMailSender sender;
	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	@Async
	public void send(Mail mail) {
		var mess = new SimpleMailMessage();
		mess.setFrom(emailProperties.getUsername());
		mess.setTo(mail.getDestinations());
		mess.setSubject(mail.getSubject());
		mess.setText(mail.getText());
		sender.send(mess);
	}

}
