package com.dineout.data;

public class Location {

	private String name;
	private String adjLocations;
	private int id;
	private int hScore;

	public Location() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdjLocations() {
		return adjLocations;
	}

	public void setAdjLocations(String adjLocations) {
		this.adjLocations = adjLocations;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int gethScore() {
		return hScore;
	}

	public void sethScore(int hScore) {
		this.hScore = hScore;
	}

}
