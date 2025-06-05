package Handlers;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.client.RestClient.RequestHeadersSpec.ExchangeFunction;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseExceptionHandler<T> {
	private Model page;
	private String message = "error";
	
	private Class<T> classType;
	public ResponseExceptionHandler(Class<T> type) {
		setClassType(type);
	}
	protected Class<T> getClassType() {
		return classType;
	}
	private void setClassType(Class<T> classType) {
		this.classType = classType;
	}

	public ExchangeFunction<T> handler(HttpStatus status){
		return 
			(clientRequest, clientResponse) -> {
				if(clientResponse.getStatusCode().isSameCodeAs(status)) {
					try ( var is = clientResponse.getBody() )  {
						var node = new ObjectMapper()
							.readTree(is)
							.get("ErrorMessage");
						if(Objects.isNull(node)) 
							log.error("JsonNode is null.");
						if(node.isTextual()) { 
							if(Objects.nonNull(page))
								page.addAttribute(message,node.textValue());
						}
						else 
							log.error("JsonNode have not a text type value.");
					}
					return null;
				}
				else
					return clientResponse.bodyTo(getClassType());
			};
	};
	public Model getModel() {
		return page;
	}

	public ResponseExceptionHandler<T> setModel(Model page) {
		this.page = page;
		return this;
	}

	public String getHolderName() {
		return message;
	}

	public ResponseExceptionHandler<T> setHolderName(String message) {
		this.message = message;
		return this;
	}
}
