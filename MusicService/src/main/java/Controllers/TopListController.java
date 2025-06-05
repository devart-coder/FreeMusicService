package Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TopListController extends BasePage{
	@PostMapping("/toplist")
	public String topListSizeChange(
			@RequestParam(required = false, defaultValue = "0" )
			Integer topSize,
			Model page)
	{
		return "toplist";
	}
	
	@GetMapping("/toplist")
	public String toplist (
		@RequestParam(required = false, defaultValue = "3")
		Integer top,
		Model page ) 
	{
		return "toplist";
	}
}