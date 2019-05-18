package com.cloudit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddDriveDataModel {
	
	DbUtil util = null;
	String uId = null;
	String vLink = null;
	String dLink = null;
	String mediaLink = null;
	String selfLink = null;
	String directLink = null;
	
	ArrayList<DriveData> driveDataList = null; 
	ArrayList<UploadFileData> cloudDataList = null;
	
	public AddDriveDataModel()
	{
		util = new DbUtil();
	}
	
	public AddDriveDataModel(String userId, String viewLink, String downLink)
	{
		util = new DbUtil();
		this.uId = userId;
		this.vLink = viewLink;
		this.dLink = downLink;
	}
	
	public AddDriveDataModel(String userId, String mediaLink, String selfLink, String directLink)
	{
		util = new DbUtil();
		this.uId = userId;
		this.mediaLink = mediaLink;
		this.selfLink = selfLink;
		this.directLink = directLink;
	}

	public int addDriveData() throws SQLException, ClassNotFoundException
	{
		util.getConnection();
				
		int insertedRows = 0;
				
		/* Update the tbluserdata by saving the qrcode as a string */
		String sql = "INSERT INTO tbldrivedata (viewLink, downLink, user) VALUES ('"+this.vLink+"', '"+this.dLink+"', '"+this.uId+"');";
		insertedRows = util.updateQuery(sql);
				
		if(insertedRows>0)
		{
			System.out.println("Successfully inserted the drive data to the database!");
		}
				
		util.closeConnection();	
		return insertedRows;
	}
	
	public ArrayList<DriveData> getData(String user)
	{
		try {
			util.getConnection();
			
			driveDataList = new ArrayList<DriveData>();
			
			String sql = "SELECT * FROM tbldrivedata WHERE user='"+user+"';";
			ResultSet myRs = util.selectQuery(sql);
			
			while(myRs.next())
			{
				int id = myRs.getInt("id");
				String viewLink = myRs.getString("viewLink");
				String downLink = myRs.getString("downLink");
				String userId = myRs.getString("user");
				
				DriveData driveData = new DriveData(id, viewLink, downLink, userId);
				driveDataList.add(driveData);
			}
			
			return driveDataList;
			
		}catch(SQLException e)
		{
			e.printStackTrace();
			return null;
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public int addGoogleCloudData(String user) throws SQLException, ClassNotFoundException
	{
		util.getConnection();
				
		int insertedRows = 0;
				
		/* Update the tbluserdata by saving the qrcode as a string */
		String sql = "INSERT INTO tbldrivedata (mediaLink, selfLink, directLink, user) VALUES ('"+this.mediaLink+"', '"+this.selfLink+"', '"+this.directLink+"', '"+user+"');";
		insertedRows = util.updateQuery(sql);
				
		if(insertedRows>0)
		{
			System.out.println("Successfully inserted the google cloud data to the database!");
		}
				
		util.closeConnection();	
		return insertedRows;
	}
	
	public ArrayList<UploadFileData> getCloudData(String user)
	{
		try {
			util.getConnection();
			
			cloudDataList = new ArrayList<UploadFileData>();
			
			String sql = "SELECT * FROM tbldrivedata WHERE user='"+user+"';";
			ResultSet myRs = util.selectQuery(sql);
			
			while(myRs.next())
			{
				int id = myRs.getInt("id");
				String mediaLink = myRs.getString("mediaLink");
				String selfLink = myRs.getString("selfLink");
				String directLink = myRs.getString("directLink");
				String userId = myRs.getString("user");
				
				UploadFileData ufd = new UploadFileData(id, userId, mediaLink, selfLink, directLink);
				cloudDataList.add(ufd);
			}
			
			return cloudDataList;
			
		}catch(SQLException e)
		{
			e.printStackTrace();
			return null;
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			return null;
		}
	}
}
