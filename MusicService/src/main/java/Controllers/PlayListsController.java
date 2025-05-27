package Controllers;


import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import Handlers.ExceptionHandlerFactory;
import Playlist.DAO.PlayListEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/playlists")
public class PlayListsController extends BasePage{
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
			var handler = 
					ExceptionHandlerFactory
					.getInstance()
					.setResultType(PlayListEntity.class)
					.setPage(page)
					.setMessage("createPlayListNameError")
					.exchange();
					

			if(Objects.nonNull(createButton)) {
				client
					.post()
					.uri(user.getId()+"/playlists/add")
					.header("Authentication", getTokenType(auth) + getTokenValue(auth))
					.body(Map.of("name",createButton))
					.exchange(handler);
			}
			if( Objects.nonNull(deleteButton) ) {
				client
					.delete()
					.uri("playlists/"+deleteButton)
					.header("Authentication", getTokenType(auth) + getTokenValue(auth))
					.exchange(handler);
			} 
			if( Objects.nonNull(mainButton) ) {
				client
					.put()
					.uri("playlists/"+mainButton)
					.header("Authentication", getTokenType(auth) + getTokenValue(auth))
					.exchange(handler);
			} 
			page.addAttribute("playLists", getAllUserPlayLists(auth));
			page.addAttribute("mainPlayList", getMainPlayList(auth));
		return "playlists";
	}
	@ModelAttribute("playLists")
	public List<PlayListEntity> getAllUserPlayLists( Authentication auth ) throws Exception {
		
//		if(userId == null)
//			getUserName(auth);
			
		var handler = ExceptionHandlerFactory
				.getInstance()
				.setResultType(List.class)
				.exchange();
		
		return client
			.get()
			.uri(user.getId()+"/playlists")
			.header("Authentication", getTokenType(auth)+getTokenValue(auth))
			.exchange(handler);
	}
}
