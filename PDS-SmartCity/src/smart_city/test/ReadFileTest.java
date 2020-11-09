package smart_city.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import smart_city.client.SocketClient;

public class ReadFileTest {
	public JSONArray readFileTest() throws IOException, ParseException {
		StringBuffer sb = new StringBuffer();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Test.json");
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
		return json;
	}

}
