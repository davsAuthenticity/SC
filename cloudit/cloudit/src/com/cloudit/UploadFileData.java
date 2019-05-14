package com.cloudit;

public class UploadFileData {

	private int id;
	private String userId;
	private String mediaLink;
	private String selfLink;
	private String directUrl;
	
	public UploadFileData()
	{
		
	}
	
	public UploadFileData(int id, String userId, String mediaLink, String selfLink, String directUrl) {
		
		this.id = id;
		this.userId = userId;
		this.mediaLink = mediaLink;
		this.selfLink = selfLink;
		this.directUrl = directUrl;
	}
	
	public UploadFileData(String userId, String mediaLink, String selfLink, String directUrl) {
		
		this.userId = userId;
		this.mediaLink = mediaLink;
		this.selfLink = selfLink;
		this.directUrl = directUrl;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMediaLink() {
		return mediaLink;
	}

	public void setMediaLink(String mediaLink) {
		this.mediaLink = mediaLink;
	}

	public String getSelfLink() {
		return selfLink;
	}

	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}

	public String getDirectUrl() {
		return directUrl;
	}

	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}
	
}