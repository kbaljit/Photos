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
	
	public ArrayList<Photo> getPhotosByTags(ArrayList<Tag> tags){
		ArrayList<Photo> matches = new ArrayList<>();
		
		for(int i = 0; i < photos.size(); i++){
			if(photos.get(i).searchTags(tags)){
				matches.add(photos.get(i));
			}
		}
		return matches;
	}
	
	/*public ArrayList<Photo> getPhotosByDate(String date){
		
	}*/
}
