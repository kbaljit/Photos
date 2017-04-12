package application;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private ArrayList<Album> albums;
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
		albums = new ArrayList<>();
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String newPassword){
		password = newPassword;
	}
	
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	public void addAlbum(Album album){
		albums.add(album);
	}
	
	public void deleteAlbumByIndex(int index){
		albums.remove(index);
	}
	
	public void deleteAlbum(String title){
		for(int i = 0; i < albums.size(); i++){
			if(albums.get(i).getTitle().equals(title)){
				albums.remove(i);
				return;
			}
		}
	}
}
