package com.dineout.jena;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.dineout.data.Item;
import com.dineout.data.Location;
import com.dineout.data.Resturant;
import com.dineout.data.User;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class DataExtractor {

	public DataExtractor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * getUserData() - Function to extract user data from owl files stored
	 * online
	 * 
	 * @return user - User Obj which stores all the data releated to user
	 */
	public User getUserData() {

		// TODO - getUserData()

		String fileName = System.getProperty("user.dir")
				+ "\\SPARQL_QUERIES\\getUserData.q";
		FileReader file = null;
		BufferedReader reader = null;
		String line = "";
		String queryString = "";
		User user = new User();

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				queryString += line + "\n";
			}

			// System.out.println(queryString);

			String source = "http://www.utdallas.edu/~pdk130030/owl/user.rdf#";

			// create a model which doesn't use a reasoner
			OntModel model = ModelFactory
					.createOntologyModel(OntModelSpec.OWL_MEM);
			model.read(source, "RDF/XML");

			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			// List<String> vars = results.getResultVars();
			// PrefixMapping pm = query.getPrefixMapping();

			// ResultSetFormatter.out(System.out, results);

			while (results.hasNext()) {

				QuerySolution qs = results.nextSolution();

				user.setName(qs.get("Name").asLiteral().getValue().toString());
				user.setPref(qs.get("Pref").asLiteral().getValue().toString());
				user.setLatitude(Double.parseDouble(qs.get("Lat").asLiteral()
						.getValue().toString()));
				user.setLongitude(Double.parseDouble(qs.get("Long").asLiteral()
						.getValue().toString()));

				// System.out
				// .println("---------------------------------------------");
				//
				// for (int i = 0; i < vars.size(); i++) {
				//
				// String var = vars.get(i).toString();
				// RDFNode g = qs.get(var);
				//
				// if (g.isURIResource())
				// System.out.println(var + "\t"
				// + pm.shortForm(g.asNode().getURI().toString()));
				// else
				// System.out.println(var + "\t"
				// + g.asLiteral().getValue());
				//
				// }
			}

			// System.out.println(user.getName());
			// System.out.println(user.getPref());
			// System.out.println(user.getLatitude());
			// System.out.println(user.getLongitude());

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}

	/**
	 * getItemData() - Function to extract item data from owl files stored
	 * online
	 * 
	 * @return - array list of items
	 */
	public ArrayList<Item> getItemData() {

		// TODO - getItemData()

		String fileName = System.getProperty("user.dir")
				+ "\\SPARQL_QUERIES\\getItemData.q";
		FileReader file = null;
		BufferedReader reader = null;
		String line = "";
		String queryString = "";
		ArrayList<Item> items = new ArrayList<Item>();

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				queryString += line + "\n";
			}

			// System.out.println(queryString);

			String source = "http://www.utdallas.edu/~pdk130030/owl/resturant.rdf#";

			// create a model which doesn't use a reasoner
			OntModel model = ModelFactory
					.createOntologyModel(OntModelSpec.OWL_MEM);
			model.read(source, "RDF/XML");

			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			// List<String> vars = results.getResultVars();
			// PrefixMapping pm = query.getPrefixMapping();

			while (results.hasNext()) {

				Item tempItem = new Item();
				QuerySolution qs = results.nextSolution();

				tempItem.setName(qs.get("Name").asLiteral().getValue()
						.toString());
				tempItem.setType(qs.get("Type").asLiteral().getValue()
						.toString());
				tempItem.setContents(qs.get("Contents").asLiteral().getValue()
						.toString());
				tempItem.setPrice(Double.parseDouble(qs.get("Price")
						.asLiteral().getValue().toString()));
				tempItem.setServedAt(qs.get("Served_At").asLiteral().getValue()
						.toString());
				tempItem.setCategory(qs.get("Category").asLiteral().getValue()
						.toString());

				items.add(tempItem);

				// System.out
				// .println("---------------------------------------------");
				//
				// for (int i = 0; i < vars.size(); i++) {
				//
				// String var = vars.get(i).toString();
				// RDFNode g = qs.get(var);
				//
				// if (g.isURIResource())
				// System.out.println(var + "\t"
				// + pm.shortForm(g.asNode().getURI().toString()));
				// else
				// System.out.println(var + "\t"
				// + g.asLiteral().getValue());
				//
				// }
			}

			// ResultSetFormatter.out(System.out, results);

			// for (int itr = 0; itr < items.size(); itr++) {
			//
			// System.out.println(items.get(itr).getName());
			// System.out.println(items.get(itr).getType());
			// System.out.println(items.get(itr).getContents());
			// System.out.println(items.get(itr).getPrice());
			// System.out.println(items.get(itr).getServedAt());
			// System.out.println("\n");
			// }

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return items;
	}

	/**
	 * getResturantData() - Function to extract resturant data from owl files
	 * stored online
	 * 
	 * @return array list of resturants
	 */
	public ArrayList<Resturant> getResturantData() {

		// TODO - getResturantData()

		String fileName = System.getProperty("user.dir")
				+ "\\SPARQL_QUERIES\\getResturantData.q";
		FileReader file = null;
		BufferedReader reader = null;
		String line = "";
		String queryString = "";
		ArrayList<Resturant> resturants = new ArrayList<Resturant>();

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				queryString += line + "\n";
			}

			// System.out.println(queryString);

			String source = "http://www.utdallas.edu/~pdk130030/owl/resturant.rdf#";

			// create a model which doesn't use a reasoner
			OntModel model = ModelFactory
					.createOntologyModel(OntModelSpec.OWL_MEM);
			model.read(source, "RDF/XML");

			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			// List<String> vars = results.getResultVars();
			// PrefixMapping pm = query.getPrefixMapping();

			while (results.hasNext()) {

				Resturant tempResturant = new Resturant();
				QuerySolution qs = results.nextSolution();

				tempResturant.setName(qs.get("Name").asLiteral().getValue()
						.toString());
				tempResturant.setCuisine(qs.get("Cuisine").asLiteral()
						.getValue().toString());
				tempResturant.setAmbience(qs.get("Ambience").asLiteral()
						.getValue().toString());
				tempResturant.setPrice(Integer.parseInt(qs.get("Price")
						.asLiteral().getValue().toString()));
				tempResturant.setLatitude(Double.parseDouble(qs.get("Lat")
						.asLiteral().getValue().toString()));
				tempResturant.setLongitude(Double.parseDouble(qs.get("Long")
						.asLiteral().getValue().toString()));

				resturants.add(tempResturant);

				// System.out
				// .println("---------------------------------------------");
				//
				// for (int i = 0; i < vars.size(); i++) {
				//
				// String var = vars.get(i).toString();
				// RDFNode g = qs.get(var);
				//
				// if (g.isURIResource())
				// System.out.println(var + "\t"
				// + pm.shortForm(g.asNode().getURI().toString()));
				// else
				// System.out.println(var + "\t"
				// + g.asLiteral().getValue());
				//
				// }
			}

			// ResultSetFormatter.out(System.out, results);

			// for (int itr = 0; itr < resturants.size(); itr++) {
			//
			// System.out.println(resturants.get(itr).getName());
			// System.out.println(resturants.get(itr).getCuisine());
			// System.out.println(resturants.get(itr).getAmbience());
			// System.out.println(resturants.get(itr).getPrice());
			// System.out.println(resturants.get(itr).getLatitude());
			// System.out.println(resturants.get(itr).getLongitude());
			// System.out.println("\n");
			// }

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resturants;
	}

	/**
	 * getLocationData() - Function to extract location data from owl files
	 * stored online
	 * 
	 * @return - array list of locations
	 */
	public ArrayList<Location> getLocationData() {

		// TODO - getLocationData()

		String fileName = System.getProperty("user.dir")
				+ "\\SPARQL_QUERIES\\getLocationData.q";
		FileReader file = null;
		BufferedReader reader = null;
		String line = "";
		String queryString = "";
		ArrayList<Location> locations = new ArrayList<Location>();

		try {

			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				queryString += line + "\n";
			}

			// System.out.println(queryString);

			String source = "http://www.utdallas.edu/~pdk130030/owl/location.rdf#";

			// create a model which doesn't use a reasoner
			OntModel model = ModelFactory
					.createOntologyModel(OntModelSpec.OWL_MEM);
			model.read(source, "RDF/XML");

			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();

			// List<String> vars = results.getResultVars();
			// PrefixMapping pm = query.getPrefixMapping();

			// ResultSetFormatter.out(System.out, results);

			while (results.hasNext()) {

				Location tempLocation = new Location();
				QuerySolution qs = results.nextSolution();

				tempLocation.setName(qs.get("Name").asLiteral().getValue()
						.toString());
				tempLocation.setAdjLocations(qs.get("Adj").asLiteral()
						.getValue().toString());
				tempLocation.sethScore(Integer.parseInt(qs.get("hScore")
						.asLiteral().getValue().toString()));
				tempLocation.setId(Integer.parseInt(qs.get("ID").asLiteral()
						.getValue().toString()));

				locations.add(tempLocation);

				// System.out
				// .println("---------------------------------------------");
				//
				// for (int i = 0; i < vars.size(); i++) {
				//
				// String var = vars.get(i).toString();
				// RDFNode g = qs.get(var);
				//
				// if (g.isURIResource())
				// System.out.println(var + "\t"
				// + pm.shortForm(g.asNode().getURI().toString()));
				// else
				// System.out.println(var + "\t"
				// + g.asLiteral().getValue());
				//
				// }
			}

			// for (int itr = 0; itr < locations.size(); itr++) {
			//
			// System.out.println(locations.get(itr).getId());
			// System.out.println(locations.get(itr).getName());
			// System.out.println(locations.get(itr).getAdjLocations());
			// System.out.println(locations.get(itr).gethScore());
			// System.out.println("\n");
			// }

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (file != null) {
				try {
					reader.close();
					file.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return locations;
	}
}
