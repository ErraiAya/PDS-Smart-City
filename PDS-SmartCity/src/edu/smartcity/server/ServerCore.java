package edu.smartcity.server;

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

import edu.smartcity.server.pool.ConnectionPool.DataSource;

public class ServerCore implements Runnable {
	
	private DataSource userModel;
	private Scanner sc = new Scanner(System.in);
	private ServerSocket socketServeur;
	private Socket socketClient;
	private static int numberOfConnectedClients = 0;

	public ServerCore() throws SQLException, ClassNotFoundException {
		userModel = new DataSource();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
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
				
				// a connection is assigned to the client
				co.add(DataSource.getConnection());
				
				// c = DataSource.getConnection();
				System.out.println("Size of the pool: " + DataSource.getSize());
				System.out.println("Number of connection asked: " + co.size());
				c = co.get(0);
				System.out.println("Size of the pool: " + DataSource.getSize());
				System.out.println("Bonjour je vais traiter votre demande");
				// instanciation of thread client (with a socket and a connection)
				ThreadServer client = new ThreadServer(socketClient, c);
				client.start();
				DataSource.releaseConnection(c);
			}catch (Exception ex) {
				System.err.println(ex.getMessage());
				// path of the exception
				// ex.printStackTrace();
			}
		}
	}

}
