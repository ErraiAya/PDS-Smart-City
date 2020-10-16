package smart_city.client;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.simple.JSONObject;


public class SelectCard {

	private SocketClient client = new SocketClient();
	private ArrayList<JSONObject> reponseServ;
	public SelectCard() throws IOException, JSONException {
		JSONObject obj = new JSONObject();
		client = new SocketClient();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		obj.put("demandType", String.valueOf("SELECT_CARD"));
		
		JSONObject reponse;
		
		reponse = SocketClient.sendMessage(obj);
		System.out.println(reponse);
		reponseServ = (ArrayList<JSONObject>) reponse.get("listCard");

	}
	
	public ArrayList<JSONObject> getReponseServ() {
		return this.reponseServ;
	}
	
	public static void main(String [] args) throws IOException, JSONException {
		SelectCard s = new SelectCard();
		System.out.println(s.getReponseServ());
	}

}
