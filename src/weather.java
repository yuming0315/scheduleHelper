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
         //���糯��
         Elements now = doc.select("div.fl");
         Elements now2 = doc.select("div.w_now2");
       
         
         //����ð�
         String nowTime = now.select("div.fl span").eq(0).text();
         String nowTemp = now.select("em").eq(0).text();
         
         String nowRain = now.select("strong").eq(2).text();
         String dust = now.select("span em").eq(0).text();
         
         
         Elements todayInfo = doc.select("table.tbl_weather");//���� ����
         String todayDate = todayInfo.select("thead span").eq(0).text();//���� ��¥
         String todayAMTemp = todayInfo.select("tbody span.temp").eq(0).text();//���� ���� 
         String todayPMTemp = todayInfo.select("tbody span.temp").eq(1).text();//���� ���� 
         txt_wtr ="���� "+nowTime+"�� ���� "+nowTemp+"\n"+"����Ȯ�� :"+nowRain+"%"+"\n"+"�̼����� :"+dust+ "\n"+"��¥ : " +todayDate+"\n" +"���� : "+todayAMTemp +"'C\n���� : "+todayPMTemp+"'C";
         
         String [] sky = nowTemp.split("��");
         System.out.println(sky[1]);
         if(sky[1].equals("����")) img_w = new ImageIcon("����/w_l1.gif");
         else if(sky[1].equals("��������")) img_w = new ImageIcon("����/w_l2.gif");
         else if(sky[1].equals("�帲")) img_w = new ImageIcon("����/w_l3.gif");
         else if(sky[1].equals("��")) img_w = new ImageIcon("����/w_l14.gif");
         else if(sky[1].equals("��������")) img_w = new ImageIcon("����/w_l21.gif");
         else if(sky[1].equals("����")) img_w = new ImageIcon("����/w_l6.gif");
         
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   
      
   }

}