package Controllers;


import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
	
	@Qualifier(value = "getUserPlaylists")
	@Autowired
	protected List<PlayListEntity> playlists;

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
		Model page
	) {
			var handler = 
					ResponseExceptionHandlerFactory
					.getInstance()
					.setBodyType(PlayListEntity.class)
					.setModel(page)
					.setHolderName("create_error")
					.handler(HttpStatus.NOT_ACCEPTABLE);
					

			if(Objects.nonNull(createButton)) {
				restClient
					.post()
					.uri(user.getId()+"/playlists/add")
					.header(AUTHENTICATION, token.getTokenHeader())
					.body(Map.of("name",createButton))
					.exchange(handler);
			}
			if( Objects.nonNull(deleteButton) ) {
				restClient
					.delete()
					.uri("playlists/"+deleteButton)
					.header(AUTHENTICATION, token.getTokenHeader())
					.exchange(handler);
			} 
			if( Objects.nonNull(mainButton) ) {
				restClient
					.put()
					.uri("playlists/"+mainButton)
					.body(Map.of("main",true))
					.header(AUTHENTICATION, token.getTokenHeader())
					.exchange(handler);
			} 
		return "playlists";
	}
	@ModelAttribute("playlists")
	public List<PlayListEntity> getAllUserPlayLists( ){
		return playlists;
	}
}
