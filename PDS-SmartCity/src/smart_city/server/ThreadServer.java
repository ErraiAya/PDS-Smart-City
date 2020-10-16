package smart_city.server;

import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.*;
//

public class ThreadServer extends Thread {
	private Socket clientSocket;
	public PrintWriter outJson;
	private BufferedReader inJson;
	private Connection c;
	private final String jsonFileName = "CarteVille.json";
	
	public ThreadServer(Socket socket, Connection connection) {
		this.clientSocket = socket;
		this.c = connection;

	}

	public void run() {
		try {
			outJson = new PrintWriter(clientSocket.getOutputStream(), true);
			inJson = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			do {

				// processing part of Json
				outJson = new PrintWriter(clientSocket.getOutputStream(), true);
				inJson = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String resp = inJson.readLine();
				System.out.println("----bonjour je viens de récuperer le JSON");
				System.out.println(resp);
				Object obj = JSONValue.parse(resp);
				System.out.println("----bonjour je parse le JSON");
				System.out.println(resp);
				JSONObject jsonObject = (JSONObject) obj;
				System.out.println("----bonjour je viens de parser le JSON");
				System.out.println(resp);

				obj = crud(jsonObject);
				// Once the Json had been processed, closing the socket and releasing the
				// connection
				outJson.println(obj);
				/*
				 * DataSource.releaseConnection(c); inJson.close(); outJson.close();
				 * clientSocket.close();
				 */
			} while (!clientSocket.isClosed());

		} catch (Exception e) {
			System.out.println("--------Un client s'est déconnecté de manière précipitée !-------");
			System.out.println(e.getMessage());
		}

		DBConnectController.clientsState(false);
	}

	// crud method allowing to according to customer's choice (select / insert/
	// update / delete) to do the request
	private JSONObject crud(JSONObject JsonRecu) {

		try {
			if (JsonRecu.get("demandType").equals("SELECT_CARD")) {

				PreparedStatement stmt1 = c.prepareStatement("select * from carteville");
				ResultSet rs2 = stmt1.executeQuery();

				JSONObject obj = new JSONObject();
				ArrayList<JSONObject> listCard = new ArrayList<JSONObject>();

				while (rs2.next()) {
					JSONObject carteville = new JSONObject();

					carteville.put("id", rs2.getInt("id"));
					carteville.put("libelle", rs2.getString("libelle"));
					carteville.put("shape", rs2.getString("shape"));
					carteville.put("length", rs2.getString("length"));
					carteville.put("width", rs2.getString("width"));
					carteville.put("nb_points", rs2.getString("nb_points"));
					carteville.put("cost", rs2.getString("cost"));
					listCard.add(carteville);

				}

				obj.put("listCard", listCard);
				System.out.println("voici le json envoyé avec le select All Card : ");
				// displaying the Json
				System.out.println(obj);

				return obj;
			}

			if (JsonRecu.get("demandType").equals("INSERT_CARD")) {
				System.out.println("Je suis rentre dans la requete INSERT");
				// recovery of data that the client had completed (name / first name
				String libelle = (String) JsonRecu.get("libelle");
				String shape = (String) JsonRecu.get("shape");
				System.out.println("1111");
				Double length = (Double) JsonRecu.get("length");
				Double width = (Double) JsonRecu.get("width");
				
				Long nb_points = (Long) JsonRecu.get("nb_points");
				int nb_p = nb_points.intValue(); 
				System.out.println("2222");
				Double cost = (Double) JsonRecu.get("cost");
				System.out.println("bonjour voici les donnees recu apres traitement");
				System.out.println(libelle + " " + shape + " " + length + " " + width + " " + nb_points + " " + cost);
				// testRedundancy(jsonRequest);
				PreparedStatement stmt3 = c.prepareStatement(
						"insert into CarteVille(libelle, shape,length,width,nb_points, cost) values (?,?,?,?,?,?);");
				// the request takes name and first name already retrieved
				stmt3.setString(1, libelle);
				stmt3.setString(2, shape);
				stmt3.setDouble(3, length);
				stmt3.setDouble(4, width);
				stmt3.setInt(5, nb_p);
				stmt3.setDouble(6, cost);
				// query execution
				JSONObject obj = new JSONObject();
				// if (insertion bien pass?) => executer les lignes suivantes sinon dire erreur
				if (stmt3.executeUpdate() >= 1) {
					obj.put("reponse", String.valueOf("insertion reussi"));
					obj.put("libelle", String.valueOf(libelle));
					obj.put("shape", String.valueOf(shape));
					obj.put("length", String.valueOf(length));
					obj.put("width", String.valueOf(width));
					obj.put("nb_points", String.valueOf(nb_points));
					obj.put("cost", String.valueOf(cost));
					writeJsonFile(obj);
				} else {
					obj.put("reponse", String.valueOf("erreur lors de l'insertion"));
				}
				System.out.println(obj);
				return obj;
			}


			if (JsonRecu.get("demandType").equals("DELETE_CARD")) {
				System.out.println("Je suis rentre dans la requete DELETE");
				// recovery of data that the client had completed (name / first name

				

				Long idd = (Long) JsonRecu.get("id");
				int id = idd.intValue(); 
				
				PreparedStatement stmt3 = c.prepareStatement(
						"delete from CarteVille where id = ?");
				// the request takes name and first name already retrieved
				stmt3.setInt(1, id);
				// query execution
				JSONObject obj = new JSONObject();
				// if (insertion bien pass?) => executer les lignes suivantes sinon dire erreur
				if (stmt3.executeUpdate() >= 1) {
					obj.put("reponse", String.valueOf("Suppression reussi"));
					writeJsonFile(obj);
				} else {
					obj.put("reponse", String.valueOf("erreur lors de l'insertion"));
				}
				System.out.println(obj);
				return obj;
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Case where no if is checked
		return new JSONObject();
	}


	public void writeJsonFile(JSONObject networkInserts) {
		try (FileWriter file = new FileWriter(jsonFileName)) {
			file.write(networkInserts.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testRedundancy(JSONObject jsonForInsert) {
		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(jsonFileName)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONArray networkList = (JSONArray) obj;
			System.out.println(networkList);
			// Iterate over employee array
			networkList.forEach(net -> {
				try {
					parseAndTestExist((JSONObject) net, jsonForInsert);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void parseAndTestExist(JSONObject netInserted, JSONObject netForInsert) throws JSONException {
		String libelleInserted = (String) netInserted.get("libelle");
		String libelleForInsert = (String) netForInsert.get("libelle");
		String shapeInserted = (String) netInserted.get("shape");
		String shapeForInsert = (String) netForInsert.get("shape");
		Double lengthInserted = (Double) netInserted.get("length");
		Double lengthForInsert = (Double) netForInsert.get("length");
		Double widthInserted = (Double) netInserted.get("width");
		Double widthForInsert = (Double) netForInsert.get("width");
		int nb_pointsInserted = (int) netInserted.get("nb_points");
		int nb_pointsForInsert = (int) netForInsert.get("nb_points");
		Double costInserted = (Double) netInserted.get("cost");
		Double costForInsert = (Double) netForInsert.get("cost");
		if (libelleInserted.toUpperCase().equals(libelleForInsert.toUpperCase())
				&& shapeInserted.toUpperCase().equals(shapeForInsert.toUpperCase())
				&& lengthInserted.equals(lengthForInsert) && widthInserted.equals(widthForInsert)
				&& nb_pointsInserted == nb_pointsForInsert && costInserted.equals(costForInsert)) {
		}
	}

	
}
