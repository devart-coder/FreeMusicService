package Playlist.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Playlist.Check.PlaylistCheck;
import Playlist.DAO.PlayListBuilder;
import Playlist.DAO.PlayListEntity;
import Playlist.ErrorMessanges.PlaylistErrorMessanges;
import Playlist.Exceptions.DuplicatePlaylistsException;
import Playlist.Exceptions.PlayListNotFoundException;
import Playlist.Repository.PlayListsRepository;
import Playlist.Service.Interfaces.PlayListDetails;
import User.DAO.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlayListService implements PlayListDetails {
	@Autowired
	private PlayListsRepository playListRepos;
	public class Wrapper<T extends UserEntity > {
		private PlayListDetails playlistService;
		private  T user;
		public Wrapper(PlayListDetails p,T t){
			this.playlistService=p;
			this.user=t;
		}
		private Stream<PlayListEntity> playlistStream(Long id) {
			return
			user.getPlaylists()
			.stream()
			.filter(p->p.getId().equals(id));
		}
		private Stream<PlayListEntity> mainPlaylistStream () {
			return 
				user
				.getPlaylists()
				.stream()
				.filter(p->p.getMain()==true);
		}
		private Stream<PlayListEntity> playlistStream(String playlistName) {
			return 
				user.getPlaylists()
				.stream()
				.filter(p->p.getName().equals(playlistName));
		}
		private PlayListEntity defaultPlaylist () {
				return 
					user
					.getPlaylists()
					.stream()
					.filter(p->p.getName().equals(PlayListDetails.DEFAULT_NAME))
					.findAny()
					.orElseGet(() -> {
						var d = PlayListBuilder.defaultPlaylist();
						if(user.getPlaylists().add(d)==false) {
							log.error("' "+DEFAULT_NAME+" ' playlist was not add to user.");
							return null;
						}
						log.warn("Default playlist was created,add to user and set as main.");
						return d;
					});
		}
		public void setAsMain(String newMainPLaylist) throws Exception {
			PlaylistCheck.nameIsValid(newMainPLaylist);
			var p = findOnceMainPlaylist();
			playlistService.updateMainByEntity(false, p);
			p =	playlistStream(newMainPLaylist)
					.findAny()
					.orElseThrow(
						() -> new PlayListNotFoundException(
							PlaylistErrorMessanges
							.PLAYLISTS_NOT_FOUND_WITH_NAME
							.formatted(newMainPLaylist)));
			playlistService.updateMainByEntity(true, p);
		}
		public void setAsMain(Long id) throws PlayListNotFoundException {
			if(PlaylistCheck.idIsValid(id)==false)
				return;
			var p = findOnceMainPlaylist();
			playlistService.updateMainByEntity(false, p);
			p =	playlistStream(id)
					.findFirst()
					.orElseThrow(
						() -> new PlayListNotFoundException(
							PlaylistErrorMessanges
							.PLAYLIST_NOT_FOUND_WITH_ID
							.formatted(id)));
			playlistService.updateMainByEntity(true, p);
		}
		
		public PlayListEntity findOnceMainPlaylist() {
			PlaylistCheck.notNull(user);
			if(mainPlaylistStream().count() > 1 ) 
				setDefaultPlayListAsMain();
			
				return 
					mainPlaylistStream()
					.findFirst()
					.orElseGet(()->{
						var p = PlayListBuilder.defaultPlaylist();
						log.warn("Set default playlists as main.");
						return playlistService.add(p);
					});
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
		
		public void  setDefaultPlayListAsMain() {
			if(PlaylistCheck.isNull(user))
				return;
			playlistService.updateMainByEntity(true, defaultPlaylist());
		
			user.getPlaylists()
				.stream()
				.forEach(p -> {
					if(!p.getName().equals(PlayListDetails.DEFAULT_NAME))
						playlistService.updateMainByEntity(false, p);
					}
				);
		}
		
		public List<PlayListEntity> findAll() {
			return user.getPlaylists();
		}
		public List<PlayListEntity> findAll(Comparator<PlayListEntity> c) {
//			var list = playListRepos.findAllByUser(user);
//			list.sort(c);
//			return list;
			return null;
		}
		public PlayListEntity add(PlayListEntity newPlaylist) {
			
			
			return null;
		}
	}
	// Create
	@Override
	public PlayListEntity add(PlayListEntity newPlayList){
		PlaylistCheck.notNull(newPlayList);
		// TODO::AddCheckOnExists
		return playListRepos.save(newPlayList);
	}

	@Override
	public PlayListEntity add(Supplier<? extends PlayListEntity> newPlayList){
		return add(newPlayList.get());
	}

	@Override
	public List<PlayListEntity> addAll(Iterable<PlayListEntity> newPlayList) {
		PlaylistCheck.notNull(newPlayList);
		// TODO::AddContainCheckByNull
		return playListRepos.saveAll(newPlayList);
	}

	// Find
	//ForAdmins
	@Override
	public List<PlayListEntity> findAll() {
		return playListRepos.findAll();
	}
	// Update
	@Override
	public void updateNameByEntity(String newName, PlayListEntity playlist) {
		playlist.setName(newName);
		updateNameById(newName, playlist.getId());
	}

	@Override
	public void updateMainByEntity(Boolean newMain, PlayListEntity playlist) {
		playlist.setMain(newMain);
		updateMainById(newMain, playlist.getId());
	}

	@Override
	public void updateSizeByEntity(Long newSize, PlayListEntity playlist) {
		playlist.setSize(newSize);
		updateSizeById(newSize, playlist.getId());
	}

	@Override
	public void updateNameById(String newName, Long playlistId) {
		try {
			playListRepos.updateNameById(newName, playlistId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void updateMainById(Boolean newMain, Long playlistId) {
		try {
			playListRepos.updateMainById(newMain, playlistId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void updateSizeById(Long newSize, Long playlistId) {
		try {
			playListRepos.updateSizeById(newSize, playlistId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void updateMainByName(Boolean newMain, String name) {
		try {
			playListRepos.updateMainByName(newMain, name);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void updateSizeByName(Long newSize, String name) {
		try {
			playListRepos.updateSizeByName(newSize, name);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	// Delete
	@Override
	public void delete(PlayListEntity playlist) {
		if (PlaylistCheck.notNull(playlist))
			playListRepos.delete(playlist);
	}

	@Override
	public void deleteById(Long id) {
		if (PlaylistCheck.idIsValid(id))
			playListRepos.deleteById(id);
	}
	public <T extends UserEntity> Wrapper<T> withUser(T user) {
		return new Wrapper<T>(this, user);
	}
	
	
}