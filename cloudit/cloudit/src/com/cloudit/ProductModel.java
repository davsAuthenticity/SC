package com.cloudit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class ProductModel {
	
	DbUtil util = null;
	ArrayList<Product> productList = new ArrayList<Product>(); 
	
	public ProductModel() throws ClassNotFoundException, SQLException
	{
		util = new DbUtil();
	}
	
	public ArrayList<Product> getProducts(String user)
	{
		try {
			util.getConnection();
			
			String sql = "SELECT * FROM tblproductdata WHERE user='"+user+"';";
			ResultSet myRs = util.selectQuery(sql);
			
			while(myRs.next())
			{
				int id = myRs.getInt("id");
				String qrCode = myRs.getString("productqrcode");
				String productDetails = myRs.getString("productdetails");
				String userId = myRs.getString("user");
				
				Product qrProduct = new Product(id, qrCode, productDetails, userId);
				productList.add(qrProduct);
			}
			
			return productList;
			
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
