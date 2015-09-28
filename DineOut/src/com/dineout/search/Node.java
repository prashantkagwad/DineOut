package com.dineout.search;

public class Node {

	private String name;
	private double gScores;
	private double hScores;
	private double fScores = 0;
	private Edge[] adjEdges;
	private Node parent;

	public Node() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Node(String name, double hVal) {
		this.name = name;
		this.hScores = hVal;
	}

	public String toString() {
		return name;
	}

	public void clear() {
		this.name = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getgScores() {
		return gScores;
	}

	public void setgScores(double gScores) {
		this.gScores = gScores;
	}

	public double gethScores() {
		return hScores;
	}

	public void sethScores(double hScores) {
		this.hScores = hScores;
	}

	public double getfScores() {
		return fScores;
	}

	public void setfScores(double fScores) {
		this.fScores = fScores;
	}

	public Edge[] getAdjEdges() {
		return adjEdges;
	}

	public void setAdjEdges(Edge[] adjEdges) {
		this.adjEdges = adjEdges;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

}
