package Controllers;


import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import Handlers.ResponseExceptionHandlerFactory;
import Playlist.DAO.PlayListEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/playlists")
public class PlayListsController extends BasePage{
	@GetMapping
	public String view() { return "playlists"; }
	@Autowired
	protected List<PlayListEntity> playlists;
	
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
					ResponseExceptionHandlerFactory
					.getInstance()
					.setBodyType(PlayListEntity.class)
					.setModel(page)
					.setHolderName("createPlayListNameError")
					.handler(HttpStatus.NOT_ACCEPTABLE);
					

			if(Objects.nonNull(createButton)) {
				restClient
					.post()
					.uri(user.getId()+"/playlists/add")
					.header(AUTHENTICATION, getTokenValue()+ getTokenValue())
					.body(Map.of("name",createButton))
					.exchange(handler);
			}
			if( Objects.nonNull(deleteButton) ) {
				restClient
					.delete()
					.uri("playlists/"+deleteButton)
					.header(AUTHENTICATION, getTokenValue()+ getTokenValue())
					.exchange(handler);
			} 
			if( Objects.nonNull(mainButton) ) {
				restClient
					.put()
					.uri("playlists/"+mainButton)
					.header(AUTHENTICATION, getTokenValue()+ getTokenValue())
					.exchange(handler);
			} 
			page.addAttribute("playLists", getAllUserPlayLists(auth));
			page.addAttribute("mainPlayList", getMainPlayList());
		return "playlists";
	}
	@ModelAttribute("playLists")
	public List<PlayListEntity> getAllUserPlayLists( Authentication auth ) throws Exception {
//		var handler = ResponseExceptionHandlerFactory
//				.getInstance()
//				.setBodyType(List.class)
//				.handler(HttpStatus.NOT_ACCEPTABLE);
//		
//		return restClient
//			.get()
//			.uri(user.getId()+"/playlists")
//			.header(AUTHENTICATION, getTokenValue()+ getTokenValue())
//			.exchange(handler);
		return playlists;
	}
}
