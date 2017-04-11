package application;

import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.image.Image;


public class Photo {
	private Image image;
	private Calendar date;
	private String caption;
	private ArrayList<Tag> tags;
	
	public Photo(Image image){
		this.image = image;
		//date = new Date()
		//date.set(Calendar.MILLISECOND, 0);
	}
	
	public void setCaption(String caption){
		this.caption = caption;
	}
	
	public String getCaption(){
		return caption;
	}
	
	public boolean addTag(Tag tag){
		//check if tag already exists
		for(int i = 0; i < tags.size(); i++){
			if(tag.getTagName() == tags.get(i).getTagName() && 
					tag.getTagValue() == tags.get(i).getTagValue()){
				return false;
			}
		}
		tags.add(tag);
		return true;
	}
	
	public ArrayList<Tag> getTags(){
		return tags;
	}
	
	public boolean searchTag(Tag tag){
		for(int i = 0; i < tags.size(); i++){
			if(tags.get(i).equals(tag)){
				return true;
			}
		}
		return false;
	}
	
	public boolean searchTags(ArrayList<Tag> searchTags){
		for(int i = 0; i < searchTags.size(); i++){
			if(!(searchTag(searchTags.get(i)))) {
				return false;
			}
		}
		return true;
	}
}
