
import java.util.*;
import java.text.*;
import java.io.*;
public class Read_todo {
	public static String txt_todo;
	String ID;
	public Read_todo(String ID) {
		this.ID=ID;
		txt_todo="";
		Date D = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy�� MM�� dd��");
		String Path = ID+File.separator+sdf.format(D)+".txt";
		try {
		File f = new File(Path);
		
		FileReader filereader = new FileReader(f);
		BufferedReader buf = new BufferedReader(filereader);
		String line = "";
		while((line = buf.readLine()) != null) {
			String s[] = line.split("\\|");
			if(s[3].equals("true")) {
				txt_todo = txt_todo+"�� " + s[0] +"\r\n";
				
			}
			else txt_todo = txt_todo+"��" + s[0] +"\r\n";
		}
		buf.close();
		}catch(FileNotFoundException e) {
			txt_todo = "�� ������ ������ �����ϴ� ��";
		}catch(IOException e) {
			txt_todo = "������ �ҷ� �� �� �����ϴ�.";
		}
	}


}
