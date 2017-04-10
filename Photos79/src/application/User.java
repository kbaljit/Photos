package application;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable{
	private static final long serialVersionUID = 3652302789390595455L;
	private String username;
	private String password;
	private ArrayList<Album> albums;
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
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
}
