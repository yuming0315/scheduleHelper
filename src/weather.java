import java.io.IOException;
import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import javax.imageio.ImageIO;
import java.awt.*;

public class weather {
   public static String txt_wtr;
   public static ImageIcon img_w;
   public weather() {
      try {
         Document doc = Jsoup.connect("https://weather.naver.com/rgn/cityWetrCity.nhn?cityRgnCd=CT008008#").get();
         //현재날씨
         Elements now = doc.select("div.fl");
         Elements now2 = doc.select("div.w_now2");
       
         
         //현재시간
         String nowTime = now.select("div.fl span").eq(0).text();
         String nowTemp = now.select("em").eq(0).text();
         
         String nowRain = now.select("strong").eq(2).text();
         String dust = now.select("span em").eq(0).text();
         
         
         Elements todayInfo = doc.select("table.tbl_weather");//오늘 정보
         String todayDate = todayInfo.select("thead span").eq(0).text();//오늘 날짜
         String todayAMTemp = todayInfo.select("tbody span.temp").eq(0).text();//오늘 오전 
         String todayPMTemp = todayInfo.select("tbody span.temp").eq(1).text();//오늘 오후 
         txt_wtr ="현재 "+nowTime+"시 기준 "+nowTemp+"\n"+"강수확률 :"+nowRain+"%"+"\n"+"미세먼지 :"+dust+ "\n"+"날짜 : " +todayDate+"\n" +"오전 : "+todayAMTemp +"'C\n오후 : "+todayPMTemp+"'C";
         
         String [] sky = nowTemp.split("℃");
         System.out.println(sky[1]);
         if(sky[1].equals("맑음")) img_w = new ImageIcon("날씨/w_l1.gif");
         else if(sky[1].equals("구름조금")) img_w = new ImageIcon("날씨/w_l2.gif");
         else if(sky[1].equals("흐림")) img_w = new ImageIcon("날씨/w_l3.gif");
         else if(sky[1].equals("비")) img_w = new ImageIcon("날씨/w_l14.gif");
         else if(sky[1].equals("구름많음")) img_w = new ImageIcon("날씨/w_l21.gif");
         else if(sky[1].equals("눈비")) img_w = new ImageIcon("날씨/w_l6.gif");
         
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   
      
   }

}