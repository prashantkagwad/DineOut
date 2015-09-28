package com.dineout.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

import com.dineout.data.Item;
import com.dineout.data.Location;
import com.dineout.data.Resturant;
import com.dineout.data.User;
import com.dineout.jena.DataExtractor;

public class DineOut {

	// flag to search for item properties if true, else resturant properties
	public static boolean itemBasedSearch = false;

	// heuristics preference
	public static String heuristics = "";

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		try {

			Character inputChar_itemBasedSearch = ' ';
			Character inputChar_userCategory = ' ';
			Character inputChar_heuristics = ' ';

			if (args.length < 3) {

				System.err
						.println("Execute DineOut as "
								+ "follows:\n\njava DineOut "
								+ "< itemFlag - T(True)/F(False) > <userCategory - V(Veg)/N(Non-Veg) > < heuristics - D(Dist)/T(Time) > < userPreferences - Coffee||Wi-Fi >\n");
				System.exit(1);
			}

			else if (args.length == 3 || args.length == 4) {

				boolean iflag = false, cflag = false, hflag = false;
				String msg = "";
				inputChar_itemBasedSearch = args[0].toLowerCase().charAt(0);
				inputChar_userCategory = args[1].toLowerCase().charAt(0);
				inputChar_heuristics = args[2].toLowerCase().charAt(0);

				// System.out.println(inputChar_itemBasedSearch);
				// System.out.println(inputChar_userCategory);
				// System.out.println(inputChar_heuristics);

				if (inputChar_itemBasedSearch.compareTo('t') == 0
						|| inputChar_itemBasedSearch.compareTo('f') == 0) {
					iflag = false;
				} else {
					iflag = true;
					msg = msg
							+ "\tFor itemFlag use 't' for True and 'f' for False\n";
				}

				if (inputChar_userCategory.compareTo('v') == 0
						|| inputChar_userCategory.compareTo('n') == 0) {
					cflag = false;
				} else {
					cflag = true;
					msg = msg
							+ "\tFor userCategory use 'v' for Veg and 'n' for Non-Veg\n";
				}

				if (inputChar_heuristics.compareTo('d') == 0
						|| inputChar_heuristics.compareTo('t') == 0) {
					hflag = false;
				} else {
					hflag = true;
					msg = msg
							+ "\tFor heuristics use 'd' for Distance and 't' for Time\n";
				}

				if (iflag || cflag || hflag) {
					System.out
							.println("Execute DineOut as "
									+ "follows:\n\njava DineOut "
									+ "< itemFlag - T(True)/F(False) > <userCategory - V(Veg)/N(Non-Veg) > < heuristics - D(Dist)/T(Time) > < userPreferences - Coffee||Wi-Fi >\n"
									+ msg);

					System.exit(1);
				}
			}

			if (inputChar_itemBasedSearch.compareTo('t') == 0)
				itemBasedSearch = true;
			if (inputChar_itemBasedSearch.compareTo('f') == 0)
				itemBasedSearch = false;

			String userCategory = "";
			if (inputChar_userCategory.compareTo('v') == 0)
				userCategory = "Veg";
			if (inputChar_userCategory.compareTo('n') == 0)
				userCategory = "Non-Veg";

			heuristics = "";
			if (inputChar_heuristics.compareTo('d') == 0)
				heuristics = "Dist";
			if (inputChar_heuristics.compareTo('t') == 0)
				heuristics = "Time";

			String preferences = "";
			if (args.length == 4) {
				if (!(args[3].isEmpty() || args[3] == ""))
					preferences = args[3];
			}

			// System.out.println("itemBasedSearch : " + itemBasedSearch);
			// System.out.println("userCategory : " + userCategory);
			// System.out.println("heuristics : " + heuristics);
			// System.out.println("preferences : " + preferences);

			// User user = dataExtractor.getUserData();

			User user = new User();
			user.setName("John Doe");
			user.setCategory(userCategory); // this is mandatory -
											// "Veg"/"Non-Veg"
			user.setPref(preferences);
			user.setLocationID(13); // location 13 is home [current user
									// location]
			user.setHeuristics(heuristics);
			user.setLatitude(32.3984073);
			user.setLongitude(-96.9385439);

			dineOut(user);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * dineOut(User user) - Function to match food items and resturants with the
	 * user preferences.
	 * 
	 * @param user
	 *            - user obj that contains all the details about the user
	 */
	public static void dineOut(User user) {

		// TODO - dineOut(User user)

		System.out.println("itemBasedSearch : " + itemBasedSearch);
		System.out.println("Category : " + user.getCategory());
		System.out.println("Heuristics : " + heuristics);

		try {
			DataExtractor dataExtractor = new DataExtractor();

			StringTokenizer userPref = new StringTokenizer(user.getPref()
					.toString(), "||");

			// get items data from rdf/owl using jena
			ArrayList<Item> items = dataExtractor.getItemData();
			ArrayList<Item> matchedItems = new ArrayList<Item>();

			// get resturant data from rdf/owl using jena
			ArrayList<Resturant> resturants = dataExtractor.getResturantData();
			ArrayList<Resturant> matchedResturants = new ArrayList<Resturant>();

			if (userPref.countTokens() > 0) {

				// user has preferences

				System.out.print("User Preferences : ");
				while (userPref.hasMoreTokens()) {

					String pref = userPref.nextToken();

					if (itemBasedSearch) {

						// match the user pref with item contents

						for (int itemCount = 0; itemCount < items.size(); itemCount++) {

							Item item = items.get(itemCount);

							// first : match the item category with user
							// category -
							// "Veg"/"Non-Veg"
							if (item.getCategory().equalsIgnoreCase(
									user.getCategory())) {

								StringTokenizer contents = new StringTokenizer(
										item.getContents(), "||");

								boolean flag = false;

								// second : match the item type with user pref -
								// "Salad||Pizza||Pasta"
								if (item.getType().equalsIgnoreCase(pref)) {

									flag = true;

								} else {

									// third : match the item contents with user
									// pref

									while (contents.hasMoreTokens()) {

										if (contents.nextToken().toString()
												.equalsIgnoreCase(pref)) {
											flag = true;
											break;
										}
									}
								}

								if (flag) {

									// if any of the above matched item
									// properties
									// to user pref, include it

									matchedItems.add(item);

									// System.out.println("ITEM >>" +
									// item.getServedAt()
									// + "<<");

									// match for the resturant that serves the
									// item
									for (int resturantCount = 0; resturantCount < resturants
											.size(); resturantCount++) {

										Resturant resturant = resturants
												.get(resturantCount);

										// System.out.println("REST >>"
										// + resturant.getName() + "<<");

										if (resturant
												.getName()
												.trim()
												.equalsIgnoreCase(
														item.getServedAt()
																.trim())) {
											matchedResturants.add(resturant);
										}
									}
								}
							}
						}
					} else {

						// match the user pref with restruant cuisine or
						// ambience

						for (int resturantCount = 0; resturantCount < resturants
								.size(); resturantCount++) {

							Resturant resturant = resturants
									.get(resturantCount);

							if (resturant.getAmbience().toLowerCase()
									.contains(pref.toLowerCase())
									|| resturant.getCuisine().toLowerCase()
											.contains(pref.toLowerCase())) {
								matchedResturants.add(resturant);
							}
						}
					}

					System.out.print(pref + " ");
				}

			} else {

				// user has no preferences
				System.out.println("User Preferences : None");

				if (itemBasedSearch) {
					// no user pref : match only the category

					for (int itemCount = 0; itemCount < items.size(); itemCount++) {

						Item item = items.get(itemCount);

						// first : match the item category with user category -
						// "Veg"/"Non-Veg"

						if (item.getCategory().equalsIgnoreCase(
								user.getCategory())) {

							// if any of the above matched item properties
							// to user pref, include it

							matchedItems.add(item);

							// System.out.println("ITEM >>" + item.getServedAt()
							// + "<<");

							// match for the resturant that serves the item

							for (int resturantCount = 0; resturantCount < resturants
									.size(); resturantCount++) {

								Resturant resturant = resturants
										.get(resturantCount);
								// System.out.println("REST >>"
								// + resturant.getName() + "<<");
								if (resturant
										.getName()
										.trim()
										.equalsIgnoreCase(
												item.getServedAt().trim())) {
									matchedResturants.add(resturant);
								}
							}
						}
					}

				} else {

					// match all resturants that serve user
					// category[Veg/Non-Veg] food

					// matchedItems = items;
					// matchedResturants = resturants;

					for (int itemCount = 0; itemCount < items.size(); itemCount++) {

						Item item = items.get(itemCount);

						// first : match the item category with user category -
						// "Veg"/"Non-Veg"

						if (item.getCategory().equalsIgnoreCase(
								user.getCategory())) {

							// System.out.println("ITEM >>" + item.getServedAt()
							// + "<<");

							// match for the resturant that serves the item

							for (int resturantCount = 0; resturantCount < resturants
									.size(); resturantCount++) {

								Resturant resturant = resturants
										.get(resturantCount);
								
								// System.out.println("REST >>"
								// + resturant.getName() + "<<");
								
								if (resturant
										.getName()
										.trim()
										.equalsIgnoreCase(
												item.getServedAt().trim())) {
									matchedResturants.add(resturant);
								}
							}
						}
					}
				}
			}

			// print items details
			System.out.println("\n\nMatched Items : ");
			System.out
					.println("==============================================================================================================");
			if (matchedItems.size() < 1) {
				System.out.println("None\n");
			} else {
				for (int itr = 0; itr < matchedItems.size(); itr++) {
					System.out.println((itr + 1) + ". "
							+ matchedItems.get(itr).getName()
							+ "\n\tServed At : "
							+ matchedItems.get(itr).getServedAt()
							+ "\n\tContents  : ["
							+ matchedItems.get(itr).getContents() + "]\n");
				}
			}
			System.out
					.println("==============================================================================================================");

			// print resturants details
			System.out.println("\n\nMatched Resturants : ");
			System.out
					.println("==============================================================================================================");
			if (matchedResturants.size() < 1) {
				System.out.println("None\n");
			} else {
				for (int itr = 0; itr < matchedResturants.size(); itr++) {
					System.out.println((itr + 1) + ". "
							+ matchedResturants.get(itr).getName()
							+ "\n\tCuisine  : ["
							+ matchedResturants.get(itr).getCuisine()
							+ "]\n\tAmbience : ["
							+ matchedResturants.get(itr).getAmbience() + "]\n");
				}
			}
			System.out
					.println("==============================================================================================================");
			System.out.println("\n");

			// print path from current location to resturants
			ArrayList<Location> locations = dataExtractor.getLocationData();

			System.out.println("Path to Resturants : ");
			System.out
					.println("==============================================================================================================");
			for (int itrR = 0; itrR < matchedResturants.size(); itrR++) {

				List<Node> nodes = locationToNode(locations);

				for (int itrL = 0; itrL < locations.size(); itrL++) {

					// System.out.println("rest : "
					// + matchedResturants.get(itrR).getName() + " || loc : "
					// + locations.get(itrL).getName());

					if (matchedResturants.get(itrR).getName()
							.equalsIgnoreCase(locations.get(itrL).getName())) {

						Node userLocation = nodes.get(user.getLocationID() - 1);
						Node resturantLocation = nodes.get(locations.get(itrL)
								.getId() - 1);

						// based on heuristics diff searches are called
						if (user.getHeuristics().equalsIgnoreCase("Dist"))
							aStarSearch(resturantLocation, userLocation);
						else if (user.getHeuristics().equalsIgnoreCase("Time"))
							greedyBestFirstSearch(resturantLocation,
									userLocation);

						List<Node> path = printPath(nodes.get(user
								.getLocationID() - 1));

						System.out.println((itrR + 1) + ". Path from "
								+ userLocation.getName() + " to "
								+ resturantLocation.getName() + " :\n\t" + path
								+ "\n");
						break;
					}
				}
			}
			System.out
					.println("==============================================================================================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * locationToNode(ArrayList<Location> locations) - Function to convert
	 * resturant locations to node structure for further processing.
	 * 
	 * @param locations
	 *            - location of resturant
	 * @return list of node
	 */
	public static List<Node> locationToNode(ArrayList<Location> locations) {

		// TODO - locationToNode(ArrayList<Location> locations)

		List<Node> nodes = new ArrayList<Node>();

		for (int itr = 0; itr < locations.size(); itr++) {
			Node tempNode = new Node(locations.get(itr).getName(), locations
					.get(itr).gethScore());
			nodes.add(tempNode);
		}

		for (int itr = 0; itr < locations.size(); itr++) {

			String adj = locations.get(itr).getAdjLocations();
			StringTokenizer st = new StringTokenizer(adj, "||");

			// System.out.println(adj + "\t\t" + st.countTokens());

			if (st.countTokens() > 0) {

				Edge[] adjEdges = new Edge[st.countTokens()];

				int counter = 0;
				while (st.hasMoreTokens()) {

					String[] map = st.nextToken().toString().split(":");

					if (map.length == 2) {

						// System.out.println(map[0] + ":" + map[1]);
						adjEdges[counter] = new Edge(nodes.get(Integer
								.parseInt(map[0]) - 1),
								Double.parseDouble(map[1]));
						counter++;
					}
				}

				nodes.get(itr).setAdjEdges(adjEdges);
			}
			// System.out.println();
		}
		return nodes;
	}

	/**
	 * printPath(Node target) - Function to list the path to a target node
	 * 
	 * @param target
	 *            - Node to which the path is printed
	 * @return
	 */
	public static List<Node> printPath(Node target) {

		// TODO - printPath(Node target)

		List<Node> path = new ArrayList<Node>();

		for (Node node = target; node != null; node = node.getParent()) {
			path.add(node);
		}

		// Collections.reverse(path);

		return path;
	}

	/**
	 * aStarSearch(Node src, Node dest) - Function to find shorest path from src
	 * node to dest node using informed search (a star algorithm)
	 * 
	 * @param src
	 *            - Source Node
	 * @param dest
	 *            - Destination Node
	 */
	public static void aStarSearch(Node src, Node dest) {

		// TODO - aStarSearch(Node src, Node dest)
		// Informed Search Algorithm Implementation

		Set<Node> exploredNodes = new HashSet<Node>();

		PriorityQueue<Node> queue = new PriorityQueue<Node>(20,
				new Comparator<Node>() {

					// override the compare method
					public int compare(Node node1, Node node2) {

						if (node1.getfScores() > node2.getfScores()) {
							return 1;
						}

						else if (node1.getfScores() < node2.getfScores()) {
							return -1;
						}

						else {
							return 0;
						}
					}
				});

		// cost from Source[src]
		src.setgScores(0);

		queue.add(src);

		boolean found = false;

		while ((!queue.isEmpty()) && (!found)) {

			// The node in having the lowest fScore value
			Node current = queue.poll();

			exploredNodes.add(current);

			// dest found
			if (current.getName().equals(dest.getName())) {
				found = true;
			}

			// check every child of current node
			for (Edge edge : current.getAdjEdges()) {
				Node child = edge.getTarget();
				double cost = edge.getCost();
				double tempgScores = current.getgScores() + cost;
				double tempfScores = tempgScores + child.gethScores();

				// if child node has been evaluated and the newer fScore is
				// higher, skip

				if ((exploredNodes.contains(child))
						&& (tempfScores >= child.getfScores())) {
					continue;
				}

				// else if child node is not in queue or newer fScore is lower

				else if ((!queue.contains(child))
						|| (tempfScores < child.getfScores())) {

					child.setParent(current);
					child.setgScores(tempgScores);
					child.setfScores(tempfScores);

					if (queue.contains(child)) {
						queue.remove(child);
					}

					queue.add(child);
				}
			}
		}
	}

	public static void greedyBestFirstSearch(Node src, Node dest) {

		// TODO - greedyBestFirstSearch(Node src, Node dest)
		// Informed Search Algorithm Implementation - for second heuristic

		Set<Node> explored = new HashSet<Node>();

		PriorityQueue<Node> queue = new PriorityQueue<Node>(20,
				new Comparator<Node>() {

					// override the compare method
					public int compare(Node node1, Node node2) {
						if (node1.getfScores() > node2.getfScores()) {
							return 1;
						}

						else if (node1.getfScores() < node2.getfScores()) {
							return -1;
						}

						else {
							return 0;
						}
					}
				});

		// cost from source [src]
		src.setgScores(0);

		queue.add(src);

		boolean found = false;

		while ((!queue.isEmpty()) && (!found)) {

			// the node in having the lowest fScore value
			Node current = queue.poll();

			explored.add(current);

			// dest found
			if (current.getName().equals(dest.getName())) {
				found = true;
			}

			// check every child of current node
			for (Edge edge : current.getAdjEdges()) {

				Node child = edge.getTarget();

				// Uncommenting below code will add g scores making it AStar
				// search
				// double cost = edge.getCost();
				// double tempgScores = current.getgScores() + cost;
				// double tempgScores = cost;
				double tempfScores = child.gethScores();

				// if child node has been evaluated and the newer fScore is
				// higher, skip

				if ((explored.contains(child))
						&& (tempfScores >= child.getfScores())) {
					continue;
				}

				// else if child node is not in queue or newer fScore is lower

				else if ((!queue.contains(child))
						|| (tempfScores < child.getfScores())) {

					child.setParent(current);
					child.setgScores(0.00);
					child.setfScores(tempfScores);

					if (queue.contains(child)) {
						queue.remove(child);
					}

					queue.add(child);
				}
			}
		}
	}
}
