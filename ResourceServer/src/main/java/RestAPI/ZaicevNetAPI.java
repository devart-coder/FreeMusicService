package RestAPI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.antlr.v4.runtime.misc.MultiMap;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.nimbusds.jose.shaded.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

//@RestController
@Controller
@Slf4j
public class ZaicevNetAPI {
	private RestClient restClient;
/*
	Scheme: 	https
	Host: 	www.zaycev.fm
	URI: 	/api/v1/recent?
	RequestParameteres: 
		channel=rock(channel name)
		limit=(limit number)
*/
	ZaicevNetAPI(){
		restClient = RestClient
		.builder()
		.baseUrl("https://abs.zaycev.fm/")
		.build();
	}

/*	Scheme:		https
 * 	Host: 		abs.zaycev.fm
 * 	URI:		/pop256k
 * */

	@GetMapping("/api/zaycev/fm/{station}/{quality}")
	public StreamingResponseBody zaychev(
		@PathVariable String station,
		@PathVariable String quality
	) throws IOException{
		try(	var stream = restClient
					.get()
					.uri(station+"/"+quality)
//					.retrieve()
//					.body(InputStream.class))
					.exchange(
						(clientRequest, clientResponse) -> {
							log.warn("Request: {}",clientRequest.getURI());
							log.warn("Request: {}",clientResponse.bodyTo(String.class));
							return clientResponse;
						}
					).getBody()
		)
		{
			
			return ( os) -> {
				readAndWrite(stream, os);
			};	
		}
	}
	private void readAndWrite(InputStream stream, OutputStream os) throws IOException {
		byte[] data = new byte[2048];
        int read = 0;
        while ((read = stream.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
	}
}
//[
// {"track":
//	{"track_id":61757
//	 ,"artist":"Emil Bulls"
//	 ,"title":"Love Will Fix It"
//	 ,"playtime":235.102041
//	 ,"itunes":null
//	 ,"images":{
//			"small":"https://radio2.zaycev.fm/artistimages/000/035/343/small.jpg"
//			,"medium":"https://radio2.zaycev.fm/artistimages/000/035/343/medium.jpg"
//			,"large":"https://radio2.zaycev.fm/artistimages/000/035/343/large.jpg"
//			,"extralarge":"https://radio2.zaycev.fm/artistimages/000/035/343/extralarge.jpg"
//			,"mega":"https://radio2.zaycev.fm/artistimages/000/035/343/mega.jpg"
//			,"original":"https://radio2.zaycev.fm/artistimages/000/035/343/original.jpg"
//			,"blurred":"https://radio2.zaycev.fm/artistimages/000/035/343/blurred.jpg"
//	 }
//	,"colors":{
//		"isBlack":null,
//		"background":"#000000"}
//	,"is_music":true}
// 	
// 	,"station_id":4
// 	,"station_alias":"rock"
// 	,"played_at":1750721353}
//		
		
