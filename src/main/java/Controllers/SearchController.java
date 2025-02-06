package Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Entities.Artist;
import Entities.JangoAPI;
import Entities.Track;
import Entities.User;
import Entities.ZaicevNetAPI;
import Entities.ZaicevNetURL;
import Repositories.MainPlayListRepository;


@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private MainPlayListRepository mainPlaylistRepository;
	private	final String searchURL="https://zaycev.net/api/external/pages/search";
	
	@GetMapping
	private String view(  ) {
		return "search";
	}
	
	@PostMapping
	private String searching(
		@RequestParam(required = false)
		String searchButton,
		Model page
	) throws JsonMappingException, JsonProcessingException{
		var json = new ZaicevNetAPI( 
			new ZaicevNetURL(searchURL)
			.setSeachName(searchButton)
			.setTrackLimit("10")
			.setArtistLimit("5")
			.build() 
		);
		
		ObjectMapper mapper = new ObjectMapper()
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JsonNode nodesTree = mapper.readTree(json.findByName(searchButton));
		
		List<Artist> artists = new ArrayList<>(); 
		for(String id : getArtistIds(nodesTree) ) {
			if(id.equals(null)) continue;
			Artist artist = mapper.treeToValue(nodesTree.findValue("listInfo").findValue(id), Artist.class);
			artist.setArtistId(id);
			artists.add(artist);
		}

		var wrapper = json.getMusicList(getTracksJson(nodesTree));
		var map = json.getUrlList(wrapper);
		
		List<Track> tracks = new ArrayList<>();
		for(String id : getTrackIds(nodesTree)) {
			Track track = mapper.treeToValue(nodesTree.findValue("tracks").findValue("tracksInfo").findValue(id), Track.class); 
			if(id != null)
			track.setId(id);
			for(var artist : artists) {
				if(artist.getArtistName().equals(track.getArtistName()))
					track.setArtist(artist);
			}
			track.setUrl( map.get(id) );
			tracks.add(track);
		}
		//TODO::JangoTryToReralize
//		var test = new JangoAPI();
//		test.test();
		
		page.addAttribute("artists", artists);
		page.addAttribute("tracksList", tracks);
		return "search";
	}
	private String[] getArtistIds(JsonNode node) {
		var idsString = 
			node
			.findValue("artists")
			.findValue("list")
			.toString();
		return idsString.substring(1,idsString.length()-1).split(",");
	}
	private JsonNode getTracksJson(JsonNode node) {
		return 
			node
			.findValue("tracks")
			.findValue("trackIds");
	}
	private String[] getTrackIds( JsonNode node ) {
		var idsString = getTracksJson(node).toString();
		return idsString.substring(1,idsString.length()-1).split(",");
	}
	
	@ModelAttribute("user")
	private String getUsername( @AuthenticationPrincipal User user ) {
		return user.getUsername();
	}
	@ModelAttribute("mainPlaylist")
	private String getPlaylistName( @AuthenticationPrincipal User user ) {
		return mainPlaylistRepository.findByUsername(user.getUsername()).getPlaylistName();
	}
}
