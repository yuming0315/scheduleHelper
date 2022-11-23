import java.awt.Color;
import java.io.*;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class TestThread extends Thread {
	String[] AP= {"AM","PM"};
	String name;
	String AorP;
	int Hour,Minute;
	int Min,Count;
	Calendar dest;
	Calendar Alram;
	
	public TestThread(String line)
	{
		String[] array=line.split("\\|");
		name=array[0];
		String[] array2=array[1].split(" ");
		AorP=array2[0];
		Hour=Integer.parseInt(array2[1]);
		Minute=Integer.parseInt(array2[3]);
		
		array2=array[2].split(" ");
		Min=Integer.parseInt(array2[0]);
		Count=Integer.parseInt(array2[2]);
		dest=Calendar.getInstance();
		dest.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE), Hour, Minute);
		Alram=Calendar.getInstance();
		Alram.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE), Hour, Minute);
	}
	
	public void run()
	{
		try
		{
			Alram.add(Alram.MINUTE, -(Min*(Count-1)));
			while(true)
			{
				System.out.println("���� : "+name+" �ð� : "+AorP+" "+dest.get(dest.HOUR)+"�� "+dest.get(dest.MINUTE)+"��, ���� : "+AP[(Calendar.getInstance().get(Calendar.AM_PM))]+" "+Calendar.getInstance().get(Calendar.HOUR)+"�� "+Calendar.getInstance().get(Calendar.MINUTE)+"�� "+Calendar.getInstance().get(Calendar.SECOND)+"��, �˶� : "+Alram.get(Calendar.HOUR)+"�� "+Alram.get(Calendar.MINUTE)+"��");
				System.out.println(AP[Calendar.getInstance().get(Calendar.AM_PM)]);
				System.out.println(AorP);
				if((AP[(Calendar.getInstance().get(Calendar.AM_PM))].equals(AorP)))
				{
					if(Alram.get(Calendar.HOUR)==Calendar.getInstance().get(Calendar.HOUR)&&
						Alram.get(Calendar.MINUTE)==Calendar.getInstance().get(Calendar.MINUTE)&&
						Calendar.getInstance().get(Calendar.SECOND)==0)
					{
						try
						{
							AudioInputStream stream = AudioSystem.getAudioInputStream(new File("���θ���.wav")); 
							Clip clip = AudioSystem.getClip(); 
							clip.open(stream); 
							clip.start();
						}
						catch(Exception e) {}
						JFrame f=new JFrame("�˶�");
						f.setAlwaysOnTop(true);
						f.getContentPane().setBackground(new Color(255,255,224));
						f.add(new JLabel(name));
						f.setSize(200,200);
						f.setVisible(true);
						Thread.sleep(2000);
						Alram.add(Alram.MINUTE,Min);
						f.dispose();
					}
					else if(Alram.get(Calendar.HOUR)<Calendar.getInstance().get(Calendar.HOUR)||
							(Alram.get(Calendar.HOUR)==Calendar.getInstance().get(Calendar.HOUR)&&
							Alram.get(Calendar.MINUTE)<Calendar.getInstance().get(Calendar.MINUTE)))
					{
						Alram.add(Alram.MINUTE, Min);
					}
					if(dest.get(Calendar.HOUR)<Calendar.getInstance().get(Calendar.HOUR))
					{
						System.out.println("���� "+name+" ����");
						return;
					}
					else if(dest.get(Calendar.HOUR)==Calendar.getInstance().get(Calendar.HOUR)&&
							dest.get(Calendar.MINUTE)<=Calendar.getInstance().get(Calendar.MINUTE))
					{
						System.out.println("���� "+name+" ����");
						return;
					}
				}
				else if(AorP.equals("AM")&&AP[Calendar.getInstance().get(Calendar.AM_PM)].equals("PM"))
				{
					System.out.println("���� "+name+" ����");
					return;
				}
				Thread.sleep(1000);
			}
		}
		catch (InterruptedException e) 
		{
			System.out.println("���� ���� Ȥ�� ������ �̷����");
		}
		
	}
}