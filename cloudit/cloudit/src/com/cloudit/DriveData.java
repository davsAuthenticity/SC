package com.cloudit;

public class DriveData {
	
	int id;
	String viewLink;
	String downLink;
	String user;
	
	public DriveData()
	{
		
	}
	
	public DriveData(int id, String viewLink, String downLink, String user) {
		
		this.id = id;
		this.viewLink = viewLink;
		this.downLink = downLink;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id= id;
	}
	
	public String getViewLink() {
		return viewLink;
	}
	
	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}
	
	public String getDownLink() {
		return downLink;
	}
	
	public void setDownLink(String downLink) {
		this.downLink = downLink;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	

}
