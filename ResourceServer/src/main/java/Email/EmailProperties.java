package Email;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EmailProperties {
//	private String host;
	private String username;
//	private String password;
//	private String protocol;
	private String[] destinations;
//	private int port;
}
