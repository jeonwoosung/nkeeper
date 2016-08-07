package org.awesome.netmon.filter;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONTest {
	public static void main(String args[]) throws JsonGenerationException, JsonMappingException, IOException{

		ArrayList user = new ArrayList();
		user.add("weffwe");
		user.add("weffwe");
		user.add("weffwe");
		
		
		
		
		
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in file
//		mapper.writeValue(new File("c:\\user.json"), user);

		//Object to JSON in String
		String s = mapper.writeValueAsString(user);
		System.out.println(s);
	}
}
