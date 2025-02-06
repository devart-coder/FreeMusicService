package Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Locale.LanguageRange;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import reactor.core.publisher.Mono;

public class JangoAPI {
	private String stid="&stid=401905037";
	private String ver ="&ver=304";
	private String skipped="&skipped=1";
	private String clicked="&clicked=1";
	private String mo="&mo=false";
	private String as="&as=null";
	private String so="&so=257928";
	private String ar="&ar=130028";
	private String offset="&offset=21";
	private String cb="&cb=1734199606683";
	private String url = "https://www.jango.com/streams/info?sid=4c20529814041854e2f3ce38b66ed2c4"
			+ stid + ver + skipped + clicked + mo
			+ as + so + ar + offset + cb;
			String str = new DefaultUriBuilderFactory()
			.builder()
			.scheme("https")
			.host("www.jango.com")
			.path("/streams")
			.fragment("/info")
			.toString();
	public void test() {
		var header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.setAccept(List.of(MediaType.APPLICATION_JSON));
		header.setConnection( "keep-alive" );
		header.setAccept(List.of(MediaType.ALL));
		
		WebClient client = WebClient.create(url);
		var result = client
		.get()
		.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:133.0) Gecko/20100101 Firefox/133.0")
		.accept(MediaType.ALL,MediaType.APPLICATION_JSON)
		.header("Accept-Encoding", "gzip, deflate, br")
		.cookie("hello", "world");
		 System.out.println("Result:\n"+result.toString());
		 Mono<String> r = result.exchangeToMono(
			response ->{
			if(response.statusCode().equals(HttpStatus.OK))
				return response.bodyToMono(String.class);
			return response.createException()
			        .flatMap(Mono::error);
		});
		 System.out.println("R:\n"+r);
	}
}
