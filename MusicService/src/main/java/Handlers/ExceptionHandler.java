package Handlers;

import java.util.Objects;

import org.springframework.ui.Model;
import org.springframework.web.client.RestClient.RequestHeadersSpec.ExchangeFunction;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandler<T> {
	private Model page;
	private String message = "error";
	
	private Class<T> classType;
	public ExceptionHandler(Class<T> type) {
		setClassType(type);
	}
	protected Class<T> getClassType() {
		return classType;
	}
	private void setClassType(Class<T> classType) {
		this.classType = classType;
	}

	public ExchangeFunction<T> exchange(){
		return 
			(clientRequest, clientResponse) -> {
				if(clientResponse.getStatusCode().is4xxClientError()) {
					try ( var is = clientResponse.getBody() )  {
						var node = new ObjectMapper()
							.readTree(is)
							.get("ErrorMessage");
						if(Objects.isNull(node)) 
							log.error("JsonNode is null.");
						if(node.isTextual()) { 
							if(Objects.nonNull(getPage()))
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
	public Model getPage() {
		return page;
	}

	public ExceptionHandler<T> setPage(Model page) {
		this.page = page;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ExceptionHandler<T> setMessage(String message) {
		this.message = message;
		return this;
	}
}
