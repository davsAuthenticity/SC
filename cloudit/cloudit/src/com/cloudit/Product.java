package com.cloudit;

public class Product {
	
	private int id;
	private String prodQr;
	private String productDetails;
	private String user;
	
	public Product(int id, String prodQr, String productDetails, String user) {

		this.id = id;
		this.prodQr = prodQr;
		this.productDetails = productDetails;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProdQr() {
		return prodQr;
	}

	public void setProdQr(String prodQr) {
		this.prodQr = prodQr;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
