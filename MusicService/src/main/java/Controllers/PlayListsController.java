package Controllers;


import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@GetMapping
	public String view() { 
		return "playlists"; 
	}
	
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
			var tokenType = getOAuth2Client(auth).getAccessToken().getTokenType();
			var token = getOAuth2Client(auth).getAccessToken().getTokenValue();
			if(Objects.nonNull(createButton)) {
				client
					.post()
					.uri(userId+"/playlists/add")
					.header("Authentication", tokenType.toString()+token.toString())
					.body(Map.of("name",createButton))
					.exchange((clientRequest, clientResponse) -> {
						if(clientResponse.getStatusCode().is4xxClientError()) {
							try ( var is = clientResponse.getBody() )  {
								//TODO:AddChecks
								var errorMessage = new ObjectMapper()
									.readTree("ErrorMessage")
									.textValue();
								page.addAttribute("createPlayListNameError", errorMessage);
							}
							return null;
						}
						else
							return clientResponse.bodyTo(PlayListEntity.class);
					});
			}
			if( Objects.nonNull(deleteButton) ) {
				client
					.delete()
					.uri("playlists/"+deleteButton)
					.header("Authentication", tokenType.toString()+token.toString())
					.exchange((clientRequest, clientResponse) -> {
						if(clientResponse.getStatusCode().is4xxClientError()) {
							try ( var is = clientResponse.getBody() )  {
								//TODO:AddChecks
								var errorMessage = new ObjectMapper()
									.readTree("ErrorMessage")
									.textValue();
								//TODO:RemakeAttributeName
								page.addAttribute("createPlayListNameError", errorMessage);
							}
						}
						return null;
					});
			} 
			if( Objects.nonNull(mainButton) ) {
				client
					.put()
					.uri("playlists/"+mainButton)
					.header("Authentication", tokenType.toString()+token.toString())
					.exchange((clientRequest, clientResponse) -> {
						if(clientResponse.getStatusCode().is4xxClientError()) {
							try ( var is = clientResponse.getBody() )  {
								//TODO:AddChecks
								var errorMessage = new ObjectMapper()
									.readTree("ErrorMessage")
									.textValue();
								//TODO:RemakeAttributeName
								page.addAttribute("createPlayListNameError", errorMessage);
							}
							return null;
						}
						else
							return clientResponse.bodyTo(PlayListEntity.class);
					});
			} 
			page.addAttribute("playLists", getAllUserPlayLists(auth));
			page.addAttribute("mainPlayList", getMainPlayList(auth));
		return "playlists";
	}
	@ModelAttribute("playLists")
	public List<PlayListEntity> getAllUserPlayLists( Authentication auth ) throws Exception {
		var tokenType = getOAuth2Client(auth).getAccessToken().getTokenType();
		var token = getOAuth2Client(auth).getAccessToken().getTokenValue();
		if(userId == null)
			getUserName(auth);
		return client
			.get()
			.uri(userId+"/playlists")
			.header("Authentication", tokenType.toString()+token.toString())
			.retrieve()
			.body(List.class);
	}
	
}
