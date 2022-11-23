import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Today extends JFrame {
   CardLayout card;                           //카드 레이아웃 사용을 위함
   JPanel today, menu;
   JTextArea ta_Todo;
   JTextArea ta_wtr;
   static ArrayList<TestThread> al;
   static TestThread[] tt;
   public String ID;
   JPanel T_Sheet;
   public Today(String ID)
   {
	  super("하루 일정");
	  //이것도
	  new school_Info();
	  this.ID=ID;
      card=new CardLayout();
      setLayout(card);
      setSize(900,900);
      setResizable(false);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      makeToday();
      makeMenu();
      Date D=new Date();
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy년 MM월 dd일");
	  String Path=ID+File.separator+sdf.format(D)+".txt";
	  try
	  {
		  al=new ArrayList<TestThread>();
		  File f=new File(Path);
		  BufferedReader buf=new BufferedReader(new FileReader(f));
		  String line="";
		  while((line=buf.readLine())!=null)
		  {
			  al.add(new TestThread(line));
		  }
		  tt=new TestThread[al.size()];
		  for(int i=0;i<al.size();i++)
		  {
			  tt[i]=al.get(i);
			  tt[i].start();
		  }
	  }
	  catch(FileNotFoundException e)
	  {
		  System.out.println("파일을 찾을 수 없습니다.");
	  }
	  catch(IOException e)
	  {
		  e.printStackTrace();
	  }
	  // 다시 넣어야 함 제발
	  try
		{
			BufferedReader br=new BufferedReader(new FileReader("data.txt"));
			String str="";
			System.out.println(Calendar.getInstance().get(Calendar.YEAR));
			while((str=br.readLine())!=null)
			{
				String[] array=str.split("@");
				System.out.println(array[0]);
				if(Calendar.getInstance().get(Calendar.YEAR)==Integer.parseInt(array[0]))
				{
					if((Calendar.getInstance().get(Calendar.MONTH)+1)==Integer.parseInt(array[1]))
					{
						String[] array2=array[2].split(" ");
						for(int i=1;i<=31;i++)
						{
							for(int j=0;j<array2.length;j++)
							{
								if(i==Integer.parseInt(array2[j]))
								{
									boolean isTrue=false;
									String path="학사일정"+File.separator+Calendar.getInstance().get(Calendar.YEAR)+"년 "+(Calendar.getInstance().get(Calendar.MONTH)+1)+"월 "+Integer.parseInt(array2[j])+"일 학사일정.txt";
									System.out.println(path);
									BufferedReader br2=new BufferedReader(new FileReader(path));
									String str2="";
									while((str2=br2.readLine())!=null)
									{
										if(str2.equals("학) "+array[3]))
										{
											isTrue=true;
											break;
										}
									}
									if(isTrue==false)
									{
										FileWriter fw=new FileWriter(path,true);
										fw.write("학) "+array[3]+"\r\n");
										fw.close();        							  
									}
								}
							}
						}
					}
				}
			}
			br.close();
		}
		catch(IOException e)
		{
			try
			{
				BufferedReader br=new BufferedReader(new FileReader("data.txt"));
				String str="";
				while((str=br.readLine())!=null)
				{
					String[] array=str.split("@");
					if(Calendar.getInstance().get(Calendar.YEAR)==Integer.parseInt(array[0]))
					{
						if((Calendar.getInstance().get(Calendar.MONTH)+1)==Integer.parseInt(array[1]))
						{
							String[] array2=array[2].split(" ");
							for(int i=1;i<=31;i++)
							{
								for(int j=0;j<array2.length;j++)
								{
									if(i==Integer.parseInt(array2[j]))
									{
										String path="학사일정"+File.separator+Calendar.getInstance().get(Calendar.YEAR)+"년 "+(Calendar.getInstance().get(Calendar.MONTH)+1)+"월 "+Integer.parseInt(array2[j])+"일 학사일정.txt";
										System.out.println(path+"..");
										File f=new File(path);
										FileWriter fw=new FileWriter(path,true);
										fw.write("학) "+array[3]+"\r\n");
										fw.close();
									}
								}
							}
						}
					}
				}
				br.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	  //이것도 넣어라 제발
	  Path = "학사일정"+File.separator+Calendar.getInstance().get(Calendar.YEAR)+"년 "
	             +(Calendar.getInstance().get(Calendar.MONTH)+1)+"월 "+Calendar.getInstance().get(Calendar.DATE)+"일"+" 학사일정.txt";
	  System.out.println(Path);
	  try
	  {
		  BufferedReader br=new BufferedReader(new FileReader(Path));
		  String s="";
		  String res="";
		  System.out.println(Path);
		  while((s=br.readLine())!=null)
		  {
			  res+=s+"\r\n";
		  }
		  System.out.println(res);
		  ta_Todo.setText(Read_todo.txt_todo+"\r\n"+res);
	  }
	  catch(IOException e) {e.printStackTrace();}
   }
   
   public void makeToday()
   {
      today=new JPanel(new BorderLayout());
      JPanel Todo=new JPanel(new BorderLayout());
      Todo.setPreferredSize(new Dimension(300,900));   //Todo를 200*500으로 사이즈 설정
      //setPreferredSize(preferredSize); 레이아웃의 크기보다 우선적으로 크기를 설정
      //preferredSize -> 자료형은 Dimension, Dimension의 크기만큼 사이즈를 설정
      new Read_todo(ID);
      ta_Todo=new JTextArea(Read_todo.txt_todo);
      new weather();
      ta_wtr = new JTextArea(weather.txt_wtr);
      
      
      Color blu = new Color(255,255,224);
      Color grn = new Color(255,250,205);
      ta_Todo.setBackground(blu);
      ta_wtr.setBackground(blu);
     
      ta_Todo.setEnabled(false);
      ta_wtr.setEnabled(false);
      
      
      JLabel lbl_Todo=new JLabel("   오늘 일정");
      Todo.setBackground(grn);
      Todo.add(lbl_Todo,BorderLayout.NORTH);
      JPanel j1 = new JPanel();
      j1.setBackground(grn);
      JPanel j2 = new JPanel();
      j2.setBackground(grn);
      JPanel j3 = new JPanel();
      j3.setBackground(grn);
      JPanel j4 = new JPanel();
      j4.setBackground(grn);
      JPanel j5 = new JPanel();
      j5.setBackground(grn);
      JPanel j6 = new JPanel();
      j6.setBackground(grn);
  

      Todo.add(j1,BorderLayout.WEST); 
      Todo.add(j2,BorderLayout.SOUTH);
      Todo.add(j3,BorderLayout.EAST); 
      Todo.add(ta_Todo,BorderLayout.CENTER);
      today.add(Todo,BorderLayout.WEST);

      
      JPanel p=new JPanel(new BorderLayout());
      JPanel Weather=new JPanel(new BorderLayout());
      
      JPanel w_l = new JPanel(new FlowLayout());		//그림 + 텍스트
      w_l.setBackground(blu);
      Weather.setPreferredSize(new Dimension(400,150));
      JLabel lbl_Weather=new JLabel("   오늘 날씨");
      Weather.setBackground(grn);
      Weather.add(lbl_Weather,BorderLayout.NORTH);
    
      
      JLabel img = new JLabel(weather.img_w);
      JPanel v = new JPanel(new BorderLayout());
      v.setBackground(blu);
      v.add(img, BorderLayout.CENTER);
      w_l.add(ta_wtr);
      w_l.add(v);
      
      
      Weather.add(j4,BorderLayout.EAST);
      Weather.add(j5,BorderLayout.WEST);
      Weather.add(j6,BorderLayout.SOUTH);
      Weather.add(w_l, BorderLayout.CENTER);
      p.add(Weather,BorderLayout.NORTH);
     
      T_Sheet=new JPanel(new BorderLayout());
      T_Sheet.setPreferredSize(new Dimension(650,650));
      JLabel lbl_Tsheet=new JLabel("   시간표");
      T_Sheet.add(new readSchedule(ID));
      T_Sheet.setBackground(grn);
      
      T_Sheet.add(lbl_Tsheet,BorderLayout.NORTH);
      
 
      p.add(T_Sheet,BorderLayout.CENTER);
      ImageIcon poro1 = new ImageIcon("images/포로리1.png");
      ImageIcon poro2 = new ImageIcon("images/포로리2.png");
      
      Image orig = poro1.getImage();
      Image orig2 = poro2.getImage();
      Image cha = orig.getScaledInstance(80,108, Image.SCALE_SMOOTH);
      Image cha2 = orig2.getScaledInstance(80,108, Image.SCALE_SMOOTH);
      poro1 = new ImageIcon(cha);
      poro2 = new ImageIcon(cha2);
      
      JButton btn_Next=new JButton(poro1);
      btn_Next.setBorderPainted(false);
      btn_Next.setContentAreaFilled(false);
      btn_Next.setFocusPainted(false);
      btn_Next.setRolloverIcon(poro2);
      btn_Next.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae)
         {
            card.next(getContentPane());               //보여주고 있는 레이아웃 다음의 레이아웃으로 넘어감
            setTitle("메뉴바");
         }
      });
      JPanel p1=new JPanel(new BorderLayout());
      p1.setBackground(grn);
      
      p1.add(btn_Next,BorderLayout.EAST);
      
      p.add(p1,BorderLayout.SOUTH);
     
      today.add(p,BorderLayout.CENTER);
      add(today,"하루일정");
   }
   public void makeMenu()
   {
      menu=new JPanel(new BorderLayout());
      JPanel p=new JPanel(new BorderLayout());
      p.setBackground(new Color(255,250,205));
      ImageIcon nuburi1 = new ImageIcon("images/너부리1.png");
      ImageIcon nuburi2 = new ImageIcon("images/너부리2.png");
      Image orig = nuburi1.getImage();
      Image orig2 = nuburi2.getImage();
      Image cha = orig.getScaledInstance(80,81, Image.SCALE_SMOOTH);
      Image cha2 = orig2.getScaledInstance(80,81, Image.SCALE_SMOOTH);
      nuburi1 = new ImageIcon(cha);
      nuburi2 = new ImageIcon(cha2);
      
      JButton btn_Prev=new JButton(nuburi1);
      btn_Prev.setRolloverIcon(nuburi2);
      btn_Prev.setBorderPainted(false);
      btn_Prev.setContentAreaFilled(false);
      btn_Prev.setFocusPainted(false);
      btn_Prev.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae)
         {
        	 T_Sheet.add(new readSchedule(ID));
        	 new Read_todo(ID);
        	 ta_Todo.setText(Read_todo.txt_todo);
        	 new weather();
             ta_wtr.setText(weather.txt_wtr);
             String Path = "학사일정"+File.separator+Calendar.getInstance().get(Calendar.YEAR)+"년 "
             +(Calendar.getInstance().get(Calendar.MONTH)+1)+"월 "+Calendar.getInstance().get(Calendar.DATE)+"일"+" 학사일정.txt";
             System.out.println(Path);
             try
             {
            	 BufferedReader br=new BufferedReader(new FileReader(Path));
                 String s="";
                 String res="";
                 System.out.println(Path);
                 while((s=br.readLine())!=null)
                 {
                	 res+=s+"\r\n";
                 }
                 System.out.println(res);
                 ta_Todo.setText(Read_todo.txt_todo+"\r\n"+res);
             }
             catch(IOException e) {e.printStackTrace();}
        	 card.previous(getContentPane());            //보여주고 있는 레이아웃 이전의 레이아웃으로 넘어감
        	 setTitle("하루 일정");
         }
      });
      btn_Prev.setPreferredSize(new Dimension(80,80));
      p.add(btn_Prev,BorderLayout.WEST);
      menu.add(p,BorderLayout.SOUTH);
      
      JPanel p_btn=new JPanel(new GridLayout(1,2,5,5));
      p_btn.setBackground(new Color(255,250,205));
      ImageIcon bono1 = new ImageIcon("images/보노1.png");
      ImageIcon bono2 = new ImageIcon("images/보노2.png");
      orig = bono1.getImage();
      orig2 = bono2.getImage();
      cha = orig.getScaledInstance(300,336, Image.SCALE_SMOOTH);
      cha2 = orig2.getScaledInstance(300,340, Image.SCALE_SMOOTH);
      bono1 = new ImageIcon(cha);
      bono2 = new ImageIcon(cha2);
      
      ImageIcon bono1_1 = new ImageIcon("images/보노1-1.png");
      ImageIcon bono2_1 = new ImageIcon("images/보노2-1.png");
      orig = bono1_1.getImage();
      orig2 = bono2_1.getImage();
      cha = orig.getScaledInstance(300,334, Image.SCALE_SMOOTH);
      cha2 = orig2.getScaledInstance(300,337, Image.SCALE_SMOOTH);
      bono1_1 = new ImageIcon(cha);
      bono2_1 = new ImageIcon(cha2);
      
      JButton btn_Calendar=new JButton("",bono1);
      btn_Calendar.setRolloverIcon(bono1_1);
      
      btn_Calendar.setBorderPainted(false);
      btn_Calendar.setContentAreaFilled(false);
      btn_Calendar.setFocusPainted(false);
      btn_Calendar.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae)
    	  {
    		  Schedular sc=new Schedular(null,"캘린더",ID);
    	  }
      });
      JButton btn_Schedule=new JButton("",bono2);
      btn_Schedule.setRolloverIcon(bono2_1);
      btn_Schedule.setBorderPainted(false);
      btn_Schedule.setContentAreaFilled(false);
      btn_Schedule.setFocusPainted(false);
      btn_Schedule.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent ae)
    	  {
    		  Schedule scd=new Schedule(null,ID);
    	  }
      });
      p_btn.add(btn_Calendar);
      p_btn.add(btn_Schedule);
      menu.add(p_btn,BorderLayout.CENTER);
      
      add(menu,"메뉴바");
      setVisible(true);
   }
}