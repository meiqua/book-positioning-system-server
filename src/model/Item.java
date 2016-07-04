package model;

import javax.persistence.*;

@Entity
@Table(name="item_inf")
public class Item {
	
	@Id @Column(name="id")
    private String id="?";

	@Override
	public String toString() {
		return "Item [author=" + author + ", thumbnailUrl=" + thumbnailUrl + ", title=" + title + ", location="
				+ location + ", state=" + state + ", id=" + id + "]";
	}
	@Column(name="author")
    private String author="?";
	
	@Column(name="thumbnailUrl")
    private String thumbnailUrl="?";
	
	@Column(name="title")
    private String title="?";
	
	@Column(name="location")
    private String location="?";
	
	@Column(name="state")
	private int state=0;
	
	public Item(String author, String thumbnailUrl, String title, String location, 
			String id) {
		super();
		this.author = author;
		this.thumbnailUrl = thumbnailUrl;
		this.title = title;
		this.location = location;
		this.id = id;
	}
	public Item() {
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id2) {
		this.id = id2;
	}

}
