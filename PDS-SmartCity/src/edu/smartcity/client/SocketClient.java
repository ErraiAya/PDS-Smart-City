package edu.smartcity.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SocketClient {

	private static Socket socketClient;
	private static PrintWriter outJson;
	private static BufferedReader inJson;

	public void startConnection(String ip, int port) throws IOException {
		socketClient = new Socket(ip, port);
		outJson = new PrintWriter(socketClient.getOutputStream(), true);
		inJson = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
	}

	public static JSONObject sendMessage(JSONObject JsonMsg) throws IOException {
		outJson.println(JsonMsg);
		String resp = inJson.readLine();
		System.out.println("==================> En String :  " + resp);
		Object obj = JSONValue.parse(resp);
		JSONObject jsonObject = (JSONObject) obj;
		System.out.println("==================> En JSON : " + jsonObject);
		return jsonObject;
	}

	public static void stopConnection() throws IOException {
		inJson.close();
		outJson.close();
		socketClient.close();
	}
}