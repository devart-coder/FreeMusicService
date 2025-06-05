package Email;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mail {
	private String[] destinations;
	private String subject;
	private String text;
}
