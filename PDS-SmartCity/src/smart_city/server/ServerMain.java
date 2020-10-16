package smart_city.server;

import java.io.IOException;
import java.sql.SQLException;

public class ServerMain {
	static DBConnectController namaicityController;

	public static void main(String[] args) throws SQLException,  IOException, ClassNotFoundException {
		namaicityController = new DBConnectController(new TestPoolView());
		namaicityController.start();
	}
}