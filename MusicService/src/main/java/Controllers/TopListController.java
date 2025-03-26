package Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TopListController {
//	@Autowired
//	private final LoggedUserManagementService logService;
//	@Autowired
//	private PlayListsInterface playListsService;
	//DI
//	public TopListController( LoggedUserManagementService lums)
//	{
//		this.logService=lums;
//	}
	
	@PostMapping("/toplist")
	public String topListSizeChange(
			@RequestParam(required = false, defaultValue = "0" )
			Integer topSize,
			Model page)
	{
//		logg.info("PostMethodDetected");
//		logg.info("topSize: " + topSize);
//		page.addAttribute("toplist",topListService.getBySize(topSize));
		return "toplist";
	}
	
	@GetMapping("/toplist")
	public String toplist (
		@RequestParam(required = false, defaultValue = "3")
		Integer top,
		Model page ) 
	{
		//TODO: Replace list to DataBase work;
//		List<SongsList> list = null;
		//TODO:Replace toplist by to MySQL  
//		page.addAttribute("toplist", list);
//		page.addAttribute("user",logService.getUsername());
//		page.addAttribute("playLists", playListsService.findAll());
		return "toplist";
	}
	
//	@ModelAttribute("playLists")
//	public List<PlayList> showAllPlaylists(){
//		return (List<PlayList>)playListsService.findAll();
//	}
}