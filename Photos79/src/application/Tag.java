package application;

public class Tag {
	private String tagName;
	private String tagValue;
	
	public Tag(String tagName, String tagValue){
		this.tagName = tagName;
		this.tagValue = tagValue;
	}
	
	public String getTagName(){
		return tagName;
	}
	
	public String getTagValue(){
		return tagValue;
	}
	
	public void setTagName(String newTagName){
		this.tagName = newTagName;
	}
	
	public void setTagValue(String newTagValue){
		this.tagName = newTagValue;
	}
}
