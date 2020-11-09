package smart_city.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InsertCard {

	private SocketClient client = new SocketClient();

	public InsertCard(SocketClient client) throws IOException, ParseException {

		StringBuffer sb = new StringBuffer();

		// lecture du JSON afin de mettre chaque ligne en chaÃ®ne de caractÃ¨re
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("CarteVille.json");
		BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		try {
			String temp;
			while ((temp = bufferedReader2.readLine()) != null)
				sb.append(temp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String myjsonstring = sb.toString();
		JSONParser parser = new JSONParser();
		JSONArray json = (JSONArray) parser.parse(myjsonstring);
		for(int i=0; i<json.size();i++) {
		JSONObject jsonObject = (JSONObject) json.get(i);

		JSONObject obj = new JSONObject();
		obj.put("demandType", String.valueOf("INSERT_CARD"));
		obj.put("libelle", jsonObject.get("libelle"));
		obj.put("shape", jsonObject.get("shape"));
		obj.put("length", jsonObject.get("length"));
		obj.put("width", jsonObject.get("width"));
		obj.put("nb_points", jsonObject.get("nb_points"));
		obj.put("cost", jsonObject.get("cost"));
		System.out.println(obj);
		JSONObject reponse = new JSONObject();
		reponse = SocketClient.sendMessage(obj);
		System.out.println(reponse);
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		SocketClient client = new SocketClient();
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		InsertCard insertCard = new InsertCard(client);
	}
}
