package com.csc.devops.jsonscript;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadJsonDataScript {
	public String name1;
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readJsonDataScript() throws IOException {
		JSONParser parser = new JSONParser();
		try {
		    JSONArray a = (JSONArray) parser.parse(new FileReader("/var/tmp/kimJson.txt"));
		    Boolean i = true;
		    for (Object o : a)
		    {
			   if(i) {
			      JSONObject gitCommitData = (JSONObject) o;
			      JSONObject msgData = (JSONObject) gitCommitData.get("commit");
			      name1 = (String) msgData.get("message");
			      System.out.println(name1);
			      i = false;
		       }
		    }
		    a.clear();
		} catch (RuntimeException e) {
		    throw e;
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return name1;
	}

}
