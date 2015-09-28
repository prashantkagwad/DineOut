package com.dineout.search;

public class Edge {

	private double cost;
	private Node target;

	public Edge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Edge(Node targetNode, double costVal) {
		target = targetNode;
		cost = costVal;
	}

	public double getCost() {
		return cost;
	}

	public Node getTarget() {
		return target;
	}

}
