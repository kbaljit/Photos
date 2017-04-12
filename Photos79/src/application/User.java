package application;

import java.io.*;
import java.util.ArrayList;

/**
 * 
 * @author Baljit Kaur
 * @author Milan Patel
 *
 *	Represents a photo library user
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private ArrayList<Album> albums;
	
	/**
	 * Initializes a user with given username and password
	 * @param username User's username
	 * @param password User's password
	 */
	public User(String username, String password){
		this.username = username;
		this.password = password;
		albums = new ArrayList<>();
	}
	
	/**
	 * Returns user's username
	 * @return User's username
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * Returns user's password
	 * @return User's password
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Sets new password
	 * @param newPassword User's new password
	 */
	public void setPassword(String newPassword){
		password = newPassword;
	}
	
	/**
	 * Returns user's albums
	 * @return User's albums
	 */
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	/**
	 * Adds new album
	 * @param album New album in user's account
	 */
	public void addAlbum(Album album){
		albums.add(album);
	}
	
	/**
	 * Deletes an album at given index
	 * @param index Index at which the album is located
	 */
	public void deleteAlbumByIndex(int index){
		albums.remove(index);
	}
	
	/**
	 * Deletes an album with the given title
	 * @param title Title of album to be deleted
	 */
	public void deleteAlbum(String title){
		for(int i = 0; i < albums.size(); i++){
			if(albums.get(i).getTitle().equals(title)){
				albums.remove(i);
				return;
			}
		}
	}
}
