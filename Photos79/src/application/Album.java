package application;

import java.util.ArrayList;

public class Album {
	private String title;
	private ArrayList<Photo> photos;
	
	public Album(String title){
		this.title = title;
	}
	
	public void addPhoto(Photo photo){
		photos.add(photo);
	}
	
	/*
	public Photo getPhotoByName(){
		for(int i = 0; i < photos.size(); i++){
			//if(photos.get(i).)
		}
	}
	
	public ArrayList<Photo> getPhotosByTag(String tag){
		
	}
	
	public ArrayList<Photo> getPhotosByDate(String date){
		
	}*/
}
