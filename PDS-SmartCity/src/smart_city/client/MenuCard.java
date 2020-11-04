package smart_city.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class MenuCard {
	private SocketClient client;

	public MenuCard() {
		try {
			client = new SocketClient();

			client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	public void StartCRUD() throws JSONException, IOException {

		Scanner sc = new Scanner(System.in);
		System.out.println("MENU CARD");
		int choice = 0;

		while (choice < 3 && choice >= 0) {
			System.out.println("1.Afficher la liste des villes");
			System.out.println("2.Supprimer une ville");
			System.out.println("3.Fin");
			System.out.println("********************************");
			System.out.println("");

			int choix = sc.nextInt();
			choice = choix;

			switch (choix) {

			case 1:
				JSONObject obj = new JSONObject();
				obj.put("demandType", String.valueOf("SELECT_CARD"));

				JSONObject reponse;

				reponse = SocketClient.sendMessage(obj);
				System.out.println(reponse);
				System.out.println((ArrayList<JSONObject>) reponse.get("listCard"));

				System.out.println("********************");
				break;

			case 2:
				System.out.println("Veuillez saisir l'id de la ville ");
				int id = sc.nextInt();

				JSONObject obj2 = new JSONObject();
				obj2.put("demandType", String.valueOf("DELETE_CARD"));
				obj2.put("id", id);

				JSONObject reponse2 = SocketClient.sendMessage(obj2);
				System.out.println(reponse2);
				System.out.println(reponse2.get("reponse"));

				System.out.println("********************");
				break;

			case 3:
				client.stopConnection();
				System.out.println("Vous avez quitte le menu");
				System.exit(0);
			}

		}

	}

	public static void main(String[] args) throws IOException, JSONException {
		try {
			MenuCard menuCard = new MenuCard();
			menuCard.StartCRUD();
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

}
