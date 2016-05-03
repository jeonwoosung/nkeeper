package org.awesome.netmon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class Test {
	public static void main(String args[]) {
		File f = new File("c:/2.txt");
		
		try {
			FileInputStream fis = new FileInputStream(f);
			
			
//			byte[] b = new byte[1024*1024];
//			int i=fis.read(b);
			
			
			FileReader reader = new FileReader(f);
//			BufferedReader bReader = new BufferedReader(reader);
			
			LineNumberReader lReader = new LineNumberReader(reader);
			
			lReader.setLineNumber(3);
			System.out.println(new String(lReader.readLine()));
//			bReader.readLine();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
