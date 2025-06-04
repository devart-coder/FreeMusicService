package Email;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailProperties {
	private String host;
	private String username;
	private String password;
	private String protocol;
	private int port;
}
