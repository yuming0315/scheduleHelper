import java.util.*;
import java.io.*;
import javax.swing.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class academic{
	public static String tq = "";
	public academic() {
		try {	
			Document deu = Jsoup.connect("https://www.deu.ac.kr/www/academic_calendar").get();
			Elements ds = deu.select("div.card");		//1-12월 달력 전체

			Elements d3 = ds.select("li");
			tq = d3.text();
	
				
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
}
