package Entities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//Action[2]:
//curl 'https://zaycev.net/api/external/track/filezmeta' 
//-X POST 
//-H 'Content-Type: application/json;charset=utf-8' 
//--data-raw '{"trackIds":["11588042","11588201","11588429","11588617","11589103","11590892","24698692","24698851","24698853","24926928"],"subscription":false}'

//Action[3]:
//curl 'https://zaycev.net/api/external/track/play/d03ad36c8e7d4e0b' 



import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ZaicevNetAPI {
	private RestTemplate rest=new RestTemplate();
	private	final String searchURL;
	private String basedUrl= "https://zaycev.net/api/external/track/filezmeta";
	
	public ZaicevNetAPI( String url ) { this.searchURL=url; }
	
	public String findByName(String name) {
		return rest.getForObject(searchURL, String.class);
	}
	
	public List<TrackWrapper> getMusicList( JsonNode tracks) throws JsonMappingException, JsonProcessingException{
		Map<String, JsonNode > converter = new HashMap<>();
		converter.put("trackIds", tracks);
	
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Map<String, JsonNode >> entity = new HttpEntity<>(converter, header);
		ResponseEntity<String> response = rest.postForEntity(basedUrl, entity, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		JsonNode nodesTree = mapper.readTree(response.getBody()).findValue("tracks");
		List<TrackWrapper> wrapperList = mapper.treeToValue(nodesTree, new TypeReference<List<TrackWrapper>>() { });
		return wrapperList;
	}
	public Map<String,String> getUrlList(List<TrackWrapper> list) {
		Map<String,String> urlMap = new HashMap<>(list.size());
		for(var track : list) {
			String url = rest.getForObject(
					"https://zaycev.net/api/external/track/play/"
					+track.getStreamCode()
					, JsonNode.class
				)
				.findValue("url")
				.toString(); 
			urlMap.put(track.getId(),url.substring(1,url.length()-1) );
		}
		return urlMap;
	} 
}
