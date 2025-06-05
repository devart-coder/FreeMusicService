package Email.Service;

import Email.Mail;

public interface EmailService {
	void sendSimpleMessage(Mail messange);
	void sendHtmlMessage(Mail messange);
}
