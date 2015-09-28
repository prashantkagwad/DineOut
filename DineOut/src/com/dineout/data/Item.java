package com.dineout.data;

public class Item {

	private String name;
	private String type;
	private String contents;
	private double price;
	private String servedAt;
	private String category;

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getServedAt() {
		return servedAt;
	}

	public void setServedAt(String servedAt) {
		this.servedAt = servedAt;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
