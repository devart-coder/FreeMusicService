package Controllers;


import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient.RequestHeadersSpec.ExchangeFunction;

import com.fasterxml.jackson.databind.ObjectMapper;

import Playlist.Check.PlaylistCheck;
import Playlist.DAO.PlayListEntity;
//import Playlist.Check.PlaylistCheck;
//import Playlist.DAO.PlayListBuilder;
//import Playlist.DAO.PlayListEntity;
//import Playlist.ErrorMessanges.PlaylistErrorMessanges;
//import Playlist.Exceptions.DuplicatePlaylistsException;
//import Playlist.Exceptions.PlayListNotFoundException;
//import Playlist.Service.Interfaces.PlayListDetails;
//import User.DAO.UserEntity;
//import User.Service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/playlists")
public class PlayListsController extends BasePage{
	private  ExchangeFunction<PlayListEntity> exceptionsHandle(Model page){
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
							log.warn("{}",node);
							if(Objects.nonNull(page))
								page.addAttribute("createPlayListNameError",node.textValue());
						}
						else 
							log.error("JsonNode have not a text type value.");
					}
					return null;
				}
				else
					return clientResponse.bodyTo(PlayListEntity.class);
			};
	}
	
	@GetMapping
	public String view() { return "playlists"; }
	
	@PostMapping
	public String playListActions(
		@RequestParam(required = false)
		String createButton,
		@RequestParam(required = false) 
		Long deleteButton,
		@RequestParam(required = false)
		Long mainButton,
		Authentication auth,
		Model page
	) throws Exception 
	{
			if(Objects.nonNull(createButton)) {
				client
					.post()
					.uri(userId+"/playlists/add")
					.header("Authentication", getTokenType(auth) + getTokenValue(auth))
					.body(Map.of("name",createButton))
					.exchange(exceptionsHandle(page));
			}
			if( Objects.nonNull(deleteButton) ) {
				client
					.delete()
					.uri("playlists/"+deleteButton)
					.header("Authentication", getTokenType(auth) + getTokenValue(auth))
					.exchange(exceptionsHandle(page));
			} 
			if( Objects.nonNull(mainButton) ) {
				client
					.put()
					.uri("playlists/"+mainButton)
					.header("Authentication", getTokenType(auth) + getTokenValue(auth))
					.exchange(exceptionsHandle(page));
			} 
			page.addAttribute("playLists", getAllUserPlayLists(auth));
			page.addAttribute("mainPlayList", getMainPlayList(auth));
		return "playlists";
	}
	@ModelAttribute("playLists")
	public List<PlayListEntity> getAllUserPlayLists( Authentication auth ) throws Exception {
		
		 ExchangeFunction<List<PlayListEntity>> lambda = 
				(clientRequest, clientResponse) -> {
					if(clientResponse.getStatusCode().is4xxClientError()) {
						try ( var is = clientResponse.getBody() )  {
							var node = new ObjectMapper()
								.readTree(is)
								.get("ErrorMessage");
							if(Objects.isNull(node)) 
								log.error("JsonNode is null.");
							if(node.isTextual()) 
								log.error(node.textValue());
							else 
								log.error("JsonNode have not a text type value.");
						}
						return null;
					}
					else
						return clientResponse.bodyTo(List.class);
			};
			
		if(userId == null)
			getUserName(auth);
		
		return client
			.get()
			.uri(userId+"/playlists")
			.header("Authentication", getTokenType(auth)+getTokenValue(auth))
			.exchange(exceptionsHandle(null));
	}
}
