package edu.smartcity.server;

import java.io.IOException;
import java.sql.SQLException;

public class ServerMain {
	

	public static void main(String[] args) throws SQLException,  IOException, ClassNotFoundException {
		Thread serverMain = new Thread(new ServerCore());
		serverMain.start();
		
	}
}