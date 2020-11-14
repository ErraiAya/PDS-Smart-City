package edu.smartcity.test;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class ManageTest extends Thread {
	public void run()  {
		int i = 0;
		int nbStation;
		System.out.println("***************************");
		System.out.println("***************************");
		System.out.println("Start Test");
		System.out.println("***************************");
		ReadFileTest readFileTest = new ReadFileTest();
		try {
			while (i < readFileTest.readFileTest().size()) {
				JSONObject jsonObject = (JSONObject) readFileTest.readFileTest().get(i);
				Long bud = (Long) jsonObject.get("budget");
				int budget = bud.intValue(); 
				Long cos = (Long) jsonObject.get("cost");
				int cost = cos.intValue(); 
				System.out.println("***************************");
				System.out.println("The budget allocated to city X " + (i+1));
				System.out.println(budget);
				System.out.println("***************************");
				System.out.println("The cost of the station");
				System.out.println(cost);
				System.out.println("***************************");
				System.out.println("The number of stations that will be made available");
				nbStation = Math.round(budget/cost);
				System.out.println(nbStation);
				System.out.println("***************************");

				i += 1;
				Thread.sleep(5000);

			}
		} catch (IOException | ParseException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("***************************");
		System.out.println("End Test");
		System.out.println("***************************");
		System.out.println("***************************");
		
	}
}
