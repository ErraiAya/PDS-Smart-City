package smart_city.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONException;
import smart_city.connection_pool.DataSource;

public class DBConnectController extends Thread {
	
	private DataSource userModel;
	private TestPoolView shsView;
	private Scanner sc = new Scanner(System.in);
	private ServerSocket socketServeur;
	private Socket socketClient;
	private static int numberOfConnectedClients = 0;

	public DBConnectController(TestPoolView v) throws SQLException, ClassNotFoundException {
		userModel = new DataSource();
		shsView = v;
	}

	public void start()  {
		String rep = "";
		List<Connection> co = new ArrayList();
		Connection c = null;

		System.out.println("Bonjour, Bienvenue sur votre serveur!");

		// Socket creation on the server side
			try {
				socketServeur = new ServerSocket(6666);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		System.out.println("====================================================");
		System.out.println("=== Voici le nombre de clients connectés : " + numberOfConnectedClients);
		System.out.println("====================================================");

		while (true) {

			try {
				// Waiting for customer connection
				socketClient = socketServeur.accept();
				DBConnectController.clientsState(true);
				// a connection is assigned to the client
				co.add(DataSource.getConnection());
				// c = DataSource.getConnection();
				shsView.printScreen("Size of the pool: " + DataSource.getSize());
				shsView.printScreen("Number of connection asked: " + co.size());
				c = co.get(0);
				shsView.printScreen("Size of the pool: " + DataSource.getSize());
				System.out.println("Bonjour je vais traiter votre demande");
				// instanciation of thread client (with a socket and a connection)
				ThreadServer client = new ThreadServer(socketClient, c);
				client.start();
			}catch (Exception ex) {
				System.err.println(ex.getMessage());
				// path of the exception
				// ex.printStackTrace();
			}
		}

	}

	public static synchronized void clientsState(boolean isNewConnection) {
		numberOfConnectedClients = (isNewConnection) ? (numberOfConnectedClients + 1) : (numberOfConnectedClients - 1);
		System.out.println("====================================================");
		System.out.println("=== Voici le nombre de clients connectés : " + numberOfConnectedClients);
		System.out.println("====================================================");

	}

}
