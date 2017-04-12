package application;

import java.io.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.LoginController;

/**
 * 
 * @author Baljit Kaur
 * @author Milan Patel
 *
 * Represents a photo library with one admin and multiple users
 * Serializes and saves user data
 */
public class PhotoLibrary implements Serializable{
	private static final long serialVersionUID = 1L;
	private Admin admin;
	ArrayList<User> users;
	
	public static final String storeFile = "users.bin";
	
	/**
	 * Initializes object with an admin and empty list of users
	 */
	public PhotoLibrary(){
		admin = new Admin();
		users = new ArrayList<>();
	}

	/**
	 * Writes to binary file to save PhotoLibrary instance and user data
	 * @param photoLib instance of PhotoLibrary
	 * @throws IOException
	 */
	public static void writeApp(PhotoLibrary photoLib) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
		new FileOutputStream(storeFile));
		oos.writeObject(photoLib);
		oos.close();
	} 
	
	/**
	 * Reads from binary file and retrieves saved date
	 * @return PhotoLibrary instance
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static PhotoLibrary readApp() throws IOException, ClassNotFoundException {
		File fs = new File(storeFile);
		if(fs.length() == 0){
			return new PhotoLibrary();
		}
		ObjectInputStream ois = new ObjectInputStream(
		new FileInputStream(storeFile));
		PhotoLibrary photoLib = (PhotoLibrary)ois.readObject();
		ois.close();
		return photoLib;
	} 
	
	/**
	 * Returns all users in the library
	 * @return All users
	 */
	public ArrayList<User> getUsers(){
		return users;
	}
	
	/**
	 * Sets users to given list of users
	 * @param U List of users
	 */
	public void setUsers(ArrayList<User> U){
		this.users=U;
	}
}
