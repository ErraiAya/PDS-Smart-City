package edu.smartcity.test;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class ManageDimensionTest extends Thread {
	public void run()  {
		int i = 0;
		System.out.println("***************************");
		System.out.println("***************************");
		System.out.println("Start Dimension Test");
		System.out.println("***************************");
		ReadFileDimensionTest readFileTest = new ReadFileDimensionTest();
		try {
			while (i < readFileTest.readFileTest().size()) {
				JSONObject jsonObject = (JSONObject) readFileTest.readFileTest().get(i);
				Long leng = (Long) jsonObject.get("length");
				int length = leng.intValue(); 
				Long wid = (Long) jsonObject.get("width");
				int width = wid.intValue(); 
				System.out.println("***************************");
				System.out.println("The length of city");
				System.out.println(length);
				System.out.println("***************************");
				System.out.println("The width of city");
				System.out.println(width);
				
				if(length<width) {

					System.out.println("***************************");
					System.out.println("The length must be greater than the width");
					
				}else {

					System.out.println("***************************");
					System.out.println("The dimension is right");
					
				}
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
