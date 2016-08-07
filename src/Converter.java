import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Converter {
	public static void main(String args[]) throws IOException{
		File f= new File("/home/specialcase/ya/MINSU.TXT");
		FileInputStream fis = new FileInputStream(f);
		int i=0;
		
		StringBuffer b  = new StringBuffer();
		while(i!=-1){
			i=fis.read();
			b.append((char)i);
		}
		
		String s = new String(b.toString().getBytes("utf-8"));
//		String s = new String(b.toString().getBytes("euckr"));
		System.out.println(s);
	}
}
