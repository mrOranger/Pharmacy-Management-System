package com.pharmacyManagementSystem.model;

public class Product implements Comparable<Product>{
	
	private String productName;
	private float total;
	private int quantity;
	
	public Product(){
		this.productName = new String();
		this.total = 0;
		this.quantity = 0;
	}
	
	public Product(String productName, float total, int quantity){
		this.productName = productName;
		this.total = total;
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int compareTo(Product arg0) {
		if(this.getProductName().equals(arg0.getProductName())){
			if(this.getQuantity() > arg0.getQuantity()){
				return 1;
			}
			if(this.getQuantity() < arg0.getQuantity()){
				return -1;
			}
			return 0;
		}else{
			return this.getProductName().compareTo(arg0.getProductName());	
		}
	}

	@Override
	public String toString(){
		return this.productName + " " + this.getQuantity() + " " + this.getTotal();  
	}
	
}
