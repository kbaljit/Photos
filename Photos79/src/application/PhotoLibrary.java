package application;

import java.io.*;
import java.util.ArrayList;

public class PhotoLibrary implements Serializable{
	private static final long serialVersionUID = -305922677898735351L;
	private Admin admin;
	ArrayList<User> users;
	
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	public PhotoLibrary(){
		admin = new Admin();
		users = new ArrayList<>();
	}
	
	public static void writeApp(PhotoLibrary photoLib) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
		new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(photoLib);
		oos.close();
	} 
	
	public static PhotoLibrary readApp() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
		new FileInputStream(storeDir + File.separator + storeFile));
		PhotoLibrary photoLib = (PhotoLibrary)ois.readObject();
		ois.close();
		return photoLib;
	} 
	
	public void main(String[] args) throws ClassNotFoundException, IOException{
		PhotoLibrary photoLib = PhotoLibrary.readApp();
		//add users
		User test=new User("test", "test");
		users.add(test);
		
		writeApp(photoLib);
	} 
}
