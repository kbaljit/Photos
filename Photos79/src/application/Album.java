package application;

import java.util.ArrayList;
import java.util.Calendar;

public class Album {
	private String title;
	private ArrayList<Photo> photos;
	
	public Album(String title){
		this.title = title;
	}
	
	public void addPhoto(Photo photo){
		photos.add(photo);
	}
	
	public String getTitle(){
		return title;
	}
	
	public ArrayList<Photo> getPhotosByTags(ArrayList<Tag> tags){
		ArrayList<Photo> matches = new ArrayList<>();
		
		for(int i = 0; i < photos.size(); i++){
			if(photos.get(i).searchTags(tags)){
				matches.add(photos.get(i));
			}
		}
		return matches;
	}
	
	public ArrayList<Photo> getPhotosByDate(Calendar date1, Calendar date2){
		ArrayList<Photo> matches = new ArrayList<>();
		
		for(int i = 0; i < photos.size(); i++){
			if((photos.get(i).getDate().after(date1) && photos.get(i).getDate().before(date2)) ||
					(photos.get(i).getDate().compareTo(date1) == 0) || 
					(photos.get(i).getDate().compareTo(date2) == 0)){
				matches.add(photos.get(i));
			}
		}
		return matches;
	}
}
