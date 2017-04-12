package application;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Photo implements Serializable{
	private File image;
	private Calendar date;
	private String caption;
	private ArrayList<Tag> tags;
	
	public Photo(File image){
		this.image = image;
		date = Calendar.getInstance();
		Date d = new Date(image.lastModified());
		date.setTime(d);
		date.set(Calendar.MILLISECOND, 0);
		tags = new ArrayList<>();
	}
	public File getImage(){
		return this.image;
	}
	public void setCaption(String caption){
		this.caption = caption;
	}
	
	public String getCaption(){
		return caption;
	}
	
	public Calendar getDate(){
		return date;
	}
	
	public String getDateString(){
		return (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.DAY_OF_MONTH) + "/" +
				date.get(Calendar.YEAR) + "";
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
	public void setTags(ArrayList<Tag> Tag){
		this.tags=Tag;
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
