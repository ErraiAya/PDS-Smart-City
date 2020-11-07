package smart_city.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class SelectCardByName {

	private SocketClient client;
	private ArrayList<JSONObject> reponseServ;

	public SelectCardByName(String name, SocketClient client) throws IOException, JSONException {
		JSONObject obj = new JSONObject();
		obj.put("demandType", String.valueOf("SELECT_CITY"));
		obj.put("libelle", name);
		JSONObject reponse;
		reponse = SocketClient.sendMessage(obj);
		System.out.println(reponse);
		reponseServ = (ArrayList<JSONObject>) reponse.get("city");
	}

	public ArrayList<JSONObject> getReponseServ() {
		return this.reponseServ;
	}

	public static void main(String[] args) throws IOException, JSONException {
		Scanner sc = new Scanner(System.in);
		String ville = sc.next();
		SocketClient client = new SocketClient();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		System.out.println("Please enter the name :");
		SelectCardByName s = new SelectCardByName(ville, client);
		System.out.println(s.getReponseServ());
	}

}
