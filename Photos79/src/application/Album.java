package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Album implements Serializable{
	private String title;
	private ArrayList<Photo> photos;
	
	public Album(String title){
		this.title = title;
		photos = new ArrayList<>();
	}
	
	public void addPhoto(Photo photo){
		photos.add(photo);
	}
	
	public String getTitle(){
		return title;
	}
	public ArrayList<Photo> getPhotos(){
		return this.photos;
	}
	
	public int getNumPhotos(){
		return photos.size();
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

	public void setTitle(String text) {
		this.title = text;
	}
	
	public String dateRange(){
		Calendar min = photos.get(0).getDate();
		Calendar max = photos.get(0).getDate();
		int minP = -1;
		int maxP = -1;
		for(int i = 1; i < photos.size(); i++){
			if(photos.get(i).getDate().before(min)){
				min = photos.get(i).getDate();
				minP = i;
			}
			if(photos.get(i).getDate().after(max)){
				max = photos.get(i).getDate();
				maxP = i;
			}
		}
		
		return photos.get(minP).getDateString() + " - "+ photos.get(maxP).getDateString();
	}
}
