package Email;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mail {
	private String[] to;
	private String subject;
	private String body;
}
