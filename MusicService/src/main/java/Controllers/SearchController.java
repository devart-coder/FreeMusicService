package Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/search")
public class SearchController extends BasePage{
	@GetMapping
	private String view(  ) {
		return "search";
	}
	
	@PostMapping
	private String searching(
		@RequestParam(required = false)
		String searchButton,
		Model page
	) {
		return "search";
		
	}
	
//	@ModelAttribute("user")
//	private String getUsername( Authentication auth ) {
//		return auth.getName();
//	}
//	@ModelAttribute("mainPlaylist")
//	private String getPlaylistName( Authentication auth) {
//		return "nobody";
////		return mainPlaylistRepository.findByUsername(auth.getName()).getPlaylistname();
//	}
}
