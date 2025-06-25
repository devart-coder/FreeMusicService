package Controllers;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RadioControler extends BasePage{
	@GetMapping("/radio")
	public String radio() {
		return "radio";
	}
	@ModelAttribute("stations")
	public Map<String,String> stationName() {
		var map = new TreeMap<String, String>(String::compareTo );
		map.putAll(Map.of(
				"NewRock","rock256k"
				,"Rap","rap256k"
				,"Rnb","rnb256k"
				,"Bass","bass256k"
				,"Pop","pop256k"
				,"Club","club256k"
				,"Disco","disco256k"
				,"Shanson","shanson256k"
				,"Rus","rus256k"
				,"Relax","relax256k"
				));
		map.putAll(Map.of(
				"Zaychata","zaychata256k"
				,"Metal","metal256k"
				,"Love","love256k"
				,"Rurock","rurock256k"
				,"Folk","folk256k"
				,"Classic","classic256k"
		));
		return map;
	}
}
