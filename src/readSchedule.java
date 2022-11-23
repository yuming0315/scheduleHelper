import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

class draw extends JPanel{
	public void paintComponent(Graphics g) {
		g.setColor(Color.orange);
		g.drawRect(5, 5, 540, 430);
		for(int i=0;i<5;i++) {
			g.drawLine(43+100*i, 5, 43+100*i, 435);
		}
		for(int i=0;i<8;i++) {
			g.drawLine(5, 40+50*i, 545, 40+50*i);
		}
	}
}


public class readSchedule extends JPanel{
	JLabel[][] J1 = new JLabel[5][8];
	JLabel[][] J2 = new JLabel[5][8];
	String day[] = {"Mon","Tue","Wed","Thu","Fri"};
	
	JLabel[] J3 = new JLabel[5];
	JLabel [] J4 = new JLabel[8];
	
	JPanel p1 = new JPanel();
	String ID;
	
	void setJLabel() {
		for(int i=0;i<J1.length;i++) {
			for(int j=0;j<J1[i].length;j++) {
					J1[i][j]=new JLabel("");
					J2[i][j]=new JLabel("");
			}
		}
		for(int i=0;i<J3.length;i++) {
			J3[i]=new JLabel(day[i]);
		}
		for(int i=0;i<J4.length;i++) {
			J4[i] = new JLabel(Integer.toString(i+1));
		}
	}
	
	readSchedule(String ID){
		this.ID=ID;
		setLayout(new GridLayout(9,6));
		setBackground(new Color(255,255,224));
		add(new JLabel());
		setJLabel();
		inputData(this.ID+File.separator+"weekEnd.txt");
		for(int i=0;i<J3.length;i++)
		{
			add(J3[i]);
		}
		for(int i=0;i<J4.length;i++)
		{
			add(J4[i]);
			for(int j=0;j<5;j++)
			{
				String str=J1[j][i].getText()+" "+J2[j][i].getText();
				add(new JLabel(str));
			}
		}
		
//		for(int i=0;i<J3.length;i++) {
//			J3[i].setBounds(80+100*i, 10, 50, 30);
//			add(J3[i]);
//		}
//		for(int i=0;i<J4.length;i++) {
//			J4[i].setBounds(20, 55+i*50, 20, 20);
//			add(J4[i]);
//		}
//		
//		int x=50,y=50;
//		for(int i=0;i<J1.length;i++) {
//			for(int j=0;j<J1[i].length;j++) {
//				J1[i][j].setBounds(x+i*100, y+j*50, 90, 20);
//				J2[i][j].setBounds(x+i*100, y+15+j*50, 90, 20);
//				add(J1[i][j]);
//				add(J2[i][j]);
//			}
//		}
//		draw d = new draw();
//		d.setBounds(0, 0, 570, 480);
//		add(d);
//		this.setBackground(new Color(255,255,224));
//		
		setSize(500,500);
		setVisible(true);
	}
	
	void inputData(String s) {
		try{
			FileReader f = new FileReader(s);
			BufferedReader f1=new BufferedReader(f);
			String str="",ddata;
			while((str=f1.readLine())!=null) {
				String[] data = str.split("Token");
				String[] dday=data[2].split(" ");
				for(int k=0;k<dday.length;k++) {
					for(int i=0;i<day.length;i++) {
							for(int j=1;j<9;j++) {
								ddata = String.format("%s%d",day[i],j);
								if(dday[k].equals(ddata)) {
									J1[i][j-1].setText(data[0]);
									J2[i][j-1].setText(data[1]);
								}
							}
						}
				}
			}
			f1.close();
			f.close();
		}
		catch(IOException e){
 			System.out.println("오류가 발생했습니다.");
 			e.printStackTrace();
 		}
	}
	
	
	
//	public static void main(String[] args) {
//		new Schedule(null,"c");
//		new readSchedule();
//		
//
//	}

}
