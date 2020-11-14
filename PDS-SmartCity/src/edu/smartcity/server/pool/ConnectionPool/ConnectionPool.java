package edu.smartcity.server.pool.ConnectionPool;

import java.sql.*;
import java.util.*;

import edu.smartcity.server.pool.DatabaseProperties.DataBaseConfiguration;

//
public class ConnectionPool {
	ArrayList<Connection> connections;
	ArrayList<Connection> usedConnections;
	DataBaseConfiguration Data;
	private int sizeMax = Integer.valueOf(System.getProperty("my.prop")); 
	private int sizeMin = 1;
	private String DRIVER_NAME;
	private String URL;
	private String login;
	private String password;

	public ConnectionPool() throws SQLException {
		connections = new ArrayList<Connection>();
		usedConnections = new ArrayList<Connection>();
		Data = new DataBaseConfiguration();
		DRIVER_NAME = Data.getDriverName();
		URL = Data.getDatabaseUrl();
		login = Data.getLogin();
		password = Data.getPassword();
		Connection con = null;
		for (int i = 0; i < sizeMax; i++) {
			try {
				Class.forName(DRIVER_NAME);
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}

		}

	}

// method for creating a connection 
	public Connection createConnection() throws SQLException {
		try {
			return DriverManager.getConnection(URL, login, password);
		} catch (SQLException e) {
			throw new SQLException("Can't create connection", e);
		}

	}

	// improvement of this method : it's for the user to obtain a connection only if
	// the connections don't exceed the maximum size of the pool and
	// if the connection Pool isn't empty

	public Connection getConnection() throws SQLException {
		if (connections.isEmpty()) {
			if (usedConnections.size() < sizeMax) {
				try {
					connections.add(createConnection());
				} catch (SQLException e) {
					throw new SQLException("Can't create connection", e);

				}
			} else {
				throw new RuntimeException("Maximum pool size reached, no available connections!");
			}
		}
		Connection toGet = connections.remove(connections.size() - 1);
		usedConnections.add(toGet);

		System.out.println("====================================================");
		System.out.println("=== Voici le nombre de clients connectés : " + usedConnections.size());
		System.out.println("====================================================");
		return toGet;
	}

// 
// method to release the connections from the list of connections used and add them to the available connections.
	public synchronized boolean releaseConnection(Connection c) {
		connections.add(c);
		return usedConnections.remove(c);

	}

// method to close connections if the list of connections used isn't empty.
	public void closeConnections() throws SQLException {
		while (!usedConnections.isEmpty()) {
			releaseConnection(usedConnections.get(0));
		}
		for (Connection c : connections) {
			try {
				c.close();
			} catch (SQLException e) {
				throw new SQLException("Can't close connection", e);
			}
		}

		connections.clear();
	}

	public int getSize() {
		return connections.size();
	}

}
