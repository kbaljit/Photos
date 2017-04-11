package application;

import java.io.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.LoginController;

public class PhotoLibrary implements Serializable{
	private static final long serialVersionUID = 1L;
	private Admin admin;
	ArrayList<User> users;
	
	public static final String storeFile = "users.bin";
	
	public PhotoLibrary(){
		admin = new Admin();
		users = new ArrayList<>();
	}

	public static void writeApp(PhotoLibrary photoLib) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
		new FileOutputStream(storeFile));
		oos.writeObject(photoLib);
		oos.close();
	} 
	
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
	
	public ArrayList<User> getUsers(){
		return users;
	}
	public void setUsers(ArrayList<User> U){
		this.users=U;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		PhotoLibrary photoLib = PhotoLibrary.readApp();

		
		writeApp(photoLib);
	} 
}
