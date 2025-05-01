package Playlist.Service.Interfaces;

import java.util.stream.Stream;

import Playlist.Check.PlaylistCheck;
import Playlist.DAO.PlayListBuilder;
import Playlist.DAO.PlayListEntity;
import Playlist.ErrorMessanges.PlaylistErrorMessanges;
import Playlist.Exceptions.DuplicatePlaylistsException;
import Playlist.Exceptions.PlayListNotFoundException;
import User.DAO.UserEntity;
import lombok.extern.slf4j.Slf4j;

public interface PlayListDetails extends PlayListCreate,PlayListUpdate, PlayListSearch, PlayListDelete{
	@Slf4j
	public class Wrapper<T extends UserEntity > {
		private PlayListDetails playlistService;
		private  T user;
	
		public Wrapper(PlayListDetails p,T t){
			this.playlistService=p;
			this.user=t;
		}
		private Stream<PlayListEntity> mainPlaylistStream () {
			return 
				user
				.getPlaylists()
				.stream()
				.filter(p->p.getMain()==true);
		}
		public void setNewMain(String newMainPLaylist) throws Exception {
			PlaylistCheck.nameIsValid(newMainPLaylist);
			var p = findOnceMainPlaylist();
			playlistService.updateMainByEntity(false, p);
				p = user
					.getPlaylists()
					.stream()
					.filter(pl->pl.getName().equals(newMainPLaylist))
					.findFirst()
					.orElseThrow(
						() -> new Exception(
							PlaylistErrorMessanges
							.PLAYLISTS_NOT_FOUND_WITH_NAME
							.formatted(newMainPLaylist)));
				playlistService.updateMainByEntity(true, p);
		}
		public PlayListEntity findOnceMainPlaylist() throws Exception { 
			PlaylistCheck.notNull(user);
			try {
				//isOne();
				if(mainPlaylistStream().count() > 1 )
					throw new DuplicatePlaylistsException(PlaylistErrorMessanges.DUPLICATED);
				//Not found
				return 
					mainPlaylistStream()
					.findFirst()
					.orElseThrow(
						() -> new PlayListNotFoundException(
								PlaylistErrorMessanges
								.MAIN_PLAYLIST_NOT_FOUND));
			}catch (DuplicatePlaylistsException e) {
				log.error(e.getMessage());
				setDefaultPlayListAsMain();
			}catch(PlayListNotFoundException e) {
				log.error(e.getMessage());
				
				var p = PlayListBuilder.defaultPlaylist();
				p.setUser(user);
				return playlistService.save(p);
			}
			return null;
		}
		public PlayListEntity findOncePlaylist(String playlistName)throws Exception{
			PlaylistCheck.nameIsValid(playlistName);
			try {
				if(playlistStream(playlistName).count() > 1 )
					throw new DuplicatePlaylistsException(PlaylistErrorMessanges.DUPLICATED);
				return 
					playlistStream(playlistName)
					.findFirst()
					.orElseThrow(
						() -> new PlayListNotFoundException(
								PlaylistErrorMessanges
								.PLAYLISTS_NOT_FOUND_WITH_NAME
								.formatted(playlistName)));
			}catch (DuplicatePlaylistsException e) {
				log.error(e.getMessage());
				return null;
			}catch(PlayListNotFoundException e) {
				log.error(e.getMessage());
				return null;
			}
		}
		private Stream<PlayListEntity> playlistStream(String playlistName) {
			return 
				user.getPlaylists()
				.stream()
				.filter(p->p.getName().equals(playlistName));
		}
		public void  setDefaultPlayListAsMain() {
			if(PlaylistCheck.isNull(user))
				return;
			playlistService.updateMainByEntity(true, defaultPlaylistStream());
		
			user.getPlaylists()
				.stream()
				.forEach(p -> {
					if(!p.getName().equals(PlayListDetails.DEFAULT_NAME))
						playlistService.updateMainByEntity(false, p);
					}
				);
		}
		private PlayListEntity defaultPlaylistStream () {
			try {
				return 
					user
					.getPlaylists()
					.stream()
					.filter(p->p.getName().equals(PlayListDetails.DEFAULT_NAME))
					.findFirst()
					.orElseThrow(() -> new PlayListNotFoundException(
						PlaylistErrorMessanges
						.PLAYLISTS_NOT_FOUND_WITH_NAME
						.formatted(DEFAULT_NAME)));
			}catch(PlayListNotFoundException e) {
				log.warn(e.getMessage());
			}
			return null;
		}
	}
	public <T extends UserEntity > Wrapper<T> withUser(T user);
	String DEFAULT_NAME = "Default";
}
