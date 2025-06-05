package Playlist.Service.Interfaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import Playlist.DAO.PlayListEntity;
import User.DAO.UserEntity;


public interface PlayListSearch {
//Search
	List<PlayListEntity> findAll();
}
