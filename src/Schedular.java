import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class Schedular extends JDialog {
	static String ID;
	static String s;
	int year,month,s_Date;
	Memo memo=new Memo();
	JPanel cal=new JPanel(new BorderLayout());
	
	CardLayout card;
	JPanel card1,card2,card3,card4;
	JPanel p_Memo=new JPanel(new BorderLayout());
	JPanel p_Schedule=new JPanel(new BorderLayout());
	JPanel p_Del=new JPanel(new GridLayout(3,1));
	JTextArea ta_Today=new JTextArea();
	JTextArea ta_Del=new JTextArea();
	JLabel s_day=new JLabel();
	JPanel p_Day=new JPanel(new BorderLayout());
	
	boolean isAdd=false;
	
	JPanel pDate=new JPanel(new GridLayout(6,7));
	JPanel pUp=new JPanel(new FlowLayout(FlowLayout.CENTER));
	JButton btn_Prev=new JButton("◀");
	JButton btn_Next=new JButton("▶");
	JLabel lblYearMon=new JLabel();
	JButton[] group=new JButton[42];
	Calendar curMon=Calendar.getInstance();
	
	public Schedular(JFrame frame, String title, String ID)
	{
		super(frame,title,true);
		this.ID=ID;
		setSize(800,800);
		pUp.setBackground(new Color(255,255,224));
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setResizable(false);
		
		btn_Prev.setFocusPainted(false);
		btn_Prev.setBorderPainted(false);
		btn_Prev.setBackground(new Color(255,255,224));
		
		btn_Next.setFocusPainted(false);
		btn_Next.setBorderPainted(false);
		btn_Next.setBackground(new Color(255,255,224));
		
		p_Day.add(new JLabel("날짜"),BorderLayout.WEST);
		p_Day.add(s_day,BorderLayout.SOUTH);
		p_Day.setBackground(new Color(255,250,205));
		cal.setPreferredSize(new Dimension(500,500));
		card=new CardLayout();
		setLayout(card);
		card1=new JPanel(new BorderLayout());
		card2=new JPanel(new BorderLayout());
		card3=new JPanel(new BorderLayout());
		card4=new JPanel(new BorderLayout());
		add(card1,"1");
		add(card2,"2");
		add(card3,"3");
		add(card4,"4");
		
		pUp.add(btn_Prev);
		pUp.add(lblYearMon);
		pUp.add(btn_Next);
		
		
		for(int i=0;i<group.length;i++)
		{
			group[i]=new JButton("");
			group[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					JButton b=(JButton)ae.getSource();
					if(!b.getBackground().equals(new Color(255,255,224)))
					{
						setSize(900,700);//1100,800 내 노트북에서 다 안보여서 일단 줄임 ㅜㅜ
						if(Integer.parseInt(b.getText())/10==0)
						{
							if((month+1)/10==0)
							{
								s=year+"년 0"+(month+1)+"월 0"+b.getText()+"일";
							}
							else
							{
								s=year+"년 "+(month+1)+"월 0"+b.getText()+"일";
							}
						}
						else
						{
							if((month+1)/10==0)
							{
								s=year+"년 0"+(month+1)+"월 "+b.getText()+"일";
							}
							else
							{
								s=year+"년 "+(month+1)+"월 "+b.getText()+"일";
							}
						}
						s_day.setText(s);
						
						try
						{
							FileReader fr=new FileReader(ID+File.separator+s+".txt");
							BufferedReader br=new BufferedReader(fr);
							String str="";
							ta_Today.setText("");
							while((str=br.readLine())!=null)
							{
								char Important;
								String str2;
								String[] array;
								String path=ID+File.separator+s+".txt";
								BufferedReader br2=new BufferedReader(new FileReader(path));
								ta_Today.setText("");
								while((str2=br2.readLine())!=null)
								{
									array=str2.split("\\|");
									Important=(array[3].equals("true")?'★':'☆');
									ta_Today.setText(ta_Today.getText()+Important+" "+array[0]+" "+array[1]+"\r\n");
								}
							}
						}
						catch(FileNotFoundException e)
						{
							ta_Today.setText("");
						}
						catch(IOException e)
						{
							ta_Today.setText("파일을 열 수 없습니다.");
						}
						
						ta_Today.setText(ta_Today.getText()+"\r\n");
						System.out.println(curMon.get(Calendar.YEAR)+"년 ");
						System.out.println((curMon.get(Calendar.MONTH)+1)+"월 ");
						System.out.println(b.getText()+"일");
						s_Date=Integer.parseInt(b.getText());
						try
						{
							String path="학사일정"+File.separator+curMon.get(Calendar.YEAR)+"년 "+(curMon.get(Calendar.MONTH)+1)+"월 "+s_Date+"일 학사일정.txt";
							BufferedReader br=new BufferedReader(new FileReader(path));
							String s="";
							while((s=br.readLine())!=null)
							{
								ta_Today.setText(ta_Today.getText()+s+"\r\n");
							}
						}
						catch(FileNotFoundException e)
						{
							System.out.println("오늘은 학사일정이 없습니다.");
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
						
						memo.getMemo();
						p_Memo.add(p_Day,BorderLayout.NORTH);
						card2.add(cal,BorderLayout.CENTER);
						card.show(getContentPane(), "2");
					}
				}
			});
			pDate.add(group[i]);
		}

		btn_Prev.addActionListener(new btnEventHandler());
		btn_Next.addActionListener(new btnEventHandler());
		
		cal.add(pUp,BorderLayout.NORTH);
		cal.add(pDate,BorderLayout.CENTER);
		setDays(curMon);
		card1.add(cal);

		addSchedule();
		addMemo();
		card2.add(p_Memo,BorderLayout.EAST);
		card3.add(p_Schedule ,BorderLayout.EAST);
		card4.add(p_Del,BorderLayout.EAST);
		
		card.show(getContentPane(), "1");
		setVisible(true);
	}
	
	public void setDays(Calendar date)
	{
		for(int i=0;i<group.length;i++)
	      {
	         group[i].setText("");
	         group[i].setBorder(null);
	         group[i].setBackground(new Color(255,255,224));
	      }
		
		year=date.get(Calendar.YEAR);
		month=date.get(Calendar.MONTH);
		lblYearMon.setText(year+"년"+(month+1)+"월");
		
		Calendar sDay=Calendar.getInstance();   
		Calendar eDay=Calendar.getInstance();
	      
		sDay.set(year,month,1);
		eDay.set(year,month,sDay.getActualMaximum(Calendar.DATE));
		sDay.add(Calendar.DATE, -sDay.get(Calendar.DAY_OF_WEEK)+1);
		eDay.add(Calendar.DATE, 7-eDay.get(Calendar.DAY_OF_WEEK));
		
		for(int i=0;sDay.before(eDay)||sDay.equals(eDay);sDay.add(Calendar.DATE, 1))
		{
			if(i%7==0)
			{
				group[i].setForeground(Color.red);
			}
			else if(i%7==6)
			{
				group[i].setForeground(Color.blue);
			}
			
			if(sDay.get(Calendar.MONTH)!=month)
			{
				group[i].setBackground(new Color(255,255,224));
			}
			else if(sDay.get(Calendar.MONTH)==Calendar.getInstance().get(Calendar.MONTH)&&sDay.get(Calendar.DATE)==Calendar.getInstance().get(Calendar.DATE))
			{
				group[i].setBackground(Color.white);
				group[i].setBorder(BorderFactory.createLineBorder(Color.orange,3));
//				group[i].setBackground(new Color(255,250,205));
			}
			else
			{
				group[i].setBorder(null);
				group[i].setBackground(Color.white);
			}
			
			int day=sDay.get(Calendar.DATE);
			group[i++].setText(""+day);
		}
		
		for(int i=0;i<group.length;i++)
		{
			String path="학사일정"+File.separator+year+"년 "+(curMon.get(Calendar.MONTH)+1)+"월 "+group[i].getText()+"일 학사일정.txt";
			File f=new File(path);
			if(f.exists())
			{
				if(!group[i].getBackground().equals(new Color(255,255,224)))
				group[i].setBackground(new Color(255,250,200));
			}
		}
	}
	public void addMemo()
	{
		p_Memo.setPreferredSize(new Dimension(300,800));
		JPanel p=new JPanel(new BorderLayout(20, 20));
		p.setBackground(new Color(255,250,205));
		JPanel p_Today=new JPanel(new BorderLayout());
		p_Today.setBackground(new Color(255,250,205));
		p_Today.setPreferredSize(new Dimension(300,300));
		JLabel lbl_Today=new JLabel("일정");
		ta_Today.setEnabled(false);
		p_Today.add(lbl_Today,BorderLayout.NORTH);
		p_Today.add(ta_Today,BorderLayout.CENTER);
		
		ImageIcon add = new ImageIcon("images/일정추가.png");

		Image orig = add.getImage();
		Image cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		add = new ImageIcon(cha);
		
		JButton btn_Add=new JButton(add);
		btn_Add.setBorderPainted(false);
		btn_Add.setContentAreaFilled(false);
		btn_Add.setFocusPainted(false);
		
		btn_Add.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent ae)
			   {
				   p_Schedule.add(p_Day,BorderLayout.NORTH);
				   card3.add(cal,BorderLayout.CENTER);
				   card.show(getContentPane(), "3");
			   }
		});
		
		ImageIcon del = new ImageIcon("images/일정삭제.png");

		orig = del.getImage();
		cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		del = new ImageIcon(cha);
		
		JButton btn_Del=new JButton(del);
		 btn_Del.setBorderPainted(false);
		 btn_Del.setContentAreaFilled(false);
		 btn_Del.setFocusPainted(false);
		btn_Del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				addDel();
				isAdd=true;
				card4.add(cal,BorderLayout.CENTER);
				card.show(getContentPane(), "4");
			}
		});
		JPanel p_btn=new JPanel();
		p_btn.setBackground(new Color(255,250,205));
		p_btn.add(btn_Add);
		p_btn.add(btn_Del);
		p_Today.add(p_btn,BorderLayout.SOUTH);
		p.add(p_Today,BorderLayout.NORTH);
		p.add(memo,BorderLayout.SOUTH);
		p_Memo.add(p_Day,BorderLayout.NORTH);
		p_Memo.add(p,BorderLayout.CENTER);
		
	}
	public void addSchedule()
	{
		p_Schedule.setPreferredSize(new Dimension(300,800));
		JLabel lbl_Warning=new JLabel();
		
		JPanel p_Name=new JPanel(new BorderLayout());
		p_Name.setBackground(new Color(255,250,205));
		JTextField tf_Name=new JTextField();
		JLabel lbl_Name=new JLabel("이름");
		p_Name.add(lbl_Name,BorderLayout.WEST);
		p_Name.add(tf_Name,BorderLayout.SOUTH);
		
		JPanel p_Alram=new JPanel(new BorderLayout());
		p_Alram.setBackground(new Color(255,250,205));
		JLabel lbl_Alram=new JLabel("알람");
		JButton btn_AorP=new JButton("AM");
		btn_AorP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				JButton b=(JButton)ae.getSource();
				if(b.getText().equals("AM"))
				{
					b.setText("PM");
				}
				else
				{
					b.setText("AM");
				}
			}
		});
		JPanel P_Alram=new JPanel(new FlowLayout());
		P_Alram.setBackground(new Color(255,250,205));
		Choice M=new Choice();
		for(int i=0;i<12;i++)
		{
			M.add(""+(i+1));
		}
		Choice D=new Choice();
		for(int i=0;i<60;i++)
		{
			D.add(""+i);
		}
		P_Alram.add(btn_AorP);
		P_Alram.add(M);
		P_Alram.add(new JLabel("시"));
		P_Alram.add(D);
		P_Alram.add(new JLabel("분"));
		p_Alram.add(lbl_Alram,BorderLayout.WEST);
		p_Alram.add(P_Alram,BorderLayout.SOUTH);
		
		JPanel p_Repeat=new JPanel(new BorderLayout());
		p_Repeat.setBackground(new Color(255,250,205));
		JPanel P_Repeat=new JPanel(new FlowLayout());
		P_Repeat.setBackground(new Color(255,250,205));
		JLabel lbl_Repeat=new JLabel("반복 주기");
		Choice Min=new Choice();
		for(int i=1;i<=6;i++)
		{
			Min.add(""+(i*5));
		}
		
		JButton btn_Count=new JButton();
		btn_Count.setText("1");
		btn_Count.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				if(btn_Count.getText().equals("5"))
				{
					btn_Count.setText("0");
				}
				btn_Count.setText(""+(Integer.parseInt(btn_Count.getText())+1));
			}
		});
		P_Repeat.add(Min);
		P_Repeat.add(new JLabel("min"));
		P_Repeat.add(btn_Count);
		P_Repeat.add(new JLabel("count"));
		p_Repeat.add(lbl_Repeat,BorderLayout.WEST);
		p_Repeat.add(P_Repeat,BorderLayout.SOUTH);
		
		JRadioButton rbtn_Important=new JRadioButton("중요 일정");
		rbtn_Important.setBackground(new Color(255,250,205));
		JPanel p_Btn=new JPanel(new BorderLayout());
		p_Btn.setBackground(new Color(255,250,205));
		
		ImageIcon add = new ImageIcon("images/일정추가.png");
		Image orig = add.getImage();
		Image cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		add = new ImageIcon(cha);
		
		JButton btn_Save=new JButton(add);
		btn_Save.setBorderPainted(false);
		btn_Save.setContentAreaFilled(false);
		btn_Save.setFocusPainted(false);
		
		btn_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				for(int i=0;i<tf_Name.getText().length();i++)
				{
					if(tf_Name.getText().contains("|"))
					{
						lbl_Warning.setText("문자 | 는 사용할 수 없습니다.");
						return;
					}
				}
				if(tf_Name.getText().trim().equals(""))
				{
					lbl_Warning.setText("정보를 정확히 입력해주세요.");
				}
				else
				{
					String Important=rbtn_Important.isSelected()==true?"true":"false";
					String str=tf_Name.getText()+"|"+
							 btn_AorP.getText()+" "+
							 M.getSelectedItem()+" 시 "+
							 D.getSelectedItem()+" 분|"+
							 Min.getSelectedItem()+" 분 "+
							 btn_Count.getText()+" 회|"+
							 Important+"\r\n";
					try
					{
						String path=ID+File.separator+s+".txt";
						FileWriter fw=new FileWriter(path,true);
						String[] array=str.split("\\|");
						fw.write(str);
						ta_Today.setText("");
						fw.close();
					}
					catch(IOException e) {}
					for(int i=0;i<Today.al.size();i++)
					{
						Today.tt[i].interrupt();
					}
					Today.al.clear();
					Date date=new Date();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy년 MM월 dd일");
					String Path=ID+File.separator+sdf.format(date)+".txt";
					try
					{
						Today.al=new ArrayList<TestThread>();
						File f=new File(Path);
						BufferedReader buf=new BufferedReader(new FileReader(f));
						String line="";
						while((line=buf.readLine())!=null)
						{
							Today.al.add(new TestThread(line));
						}
						Today.tt=new TestThread[Today.al.size()];
						for(int i=0;i<Today.al.size();i++)
						{
							Today.tt[i]=Today.al.get(i);
							Today.tt[i].start();
						}
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
					setSize(800,800);
					tf_Name.setText("");
					btn_AorP.setText("AM");
					M.select(0);
					D.select(0);
					Min.select(0);
					rbtn_Important.setSelected(false);
					btn_Count.setText("1");
					card1.add(cal);
					card.show(getContentPane(), "1");
				}
			}
		});
		add = new ImageIcon("images/취소.png");
		orig = add.getImage();
		cha = orig.getScaledInstance(70,47, Image.SCALE_SMOOTH);
		add = new ImageIcon(cha);
		
		JButton btn_Cancel=new JButton(add);
		btn_Cancel.setBorderPainted(false);
		btn_Cancel.setContentAreaFilled(false);
		btn_Cancel.setFocusPainted(false);
		
		btn_Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				setSize(800,800);
				tf_Name.setText("");
				btn_AorP.setText("AM");
				M.select(0);
				D.select(0);
				Min.select(0);
				rbtn_Important.setSelected(false);
				btn_Count.setText("1");
				card1.add(cal);
				card.show(getContentPane(), "1");
			}
		});
		JPanel P_Btn=new JPanel(new FlowLayout());
		P_Btn.setBackground(new Color(255,250,205));
		P_Btn.add(btn_Save);
		P_Btn.add(btn_Cancel);
		p_Btn.add(P_Btn,BorderLayout.CENTER);
		p_Btn.add(lbl_Warning,BorderLayout.SOUTH);
		
		JPanel P_Schedule=new JPanel(new GridLayout(5,1));
		P_Schedule.setBackground(new Color(255,250,205));
		p_Schedule.setBackground(new Color(255,250,205));
		p_Schedule.add(p_Day,BorderLayout.NORTH);
		P_Schedule.add(p_Name);
		P_Schedule.add(p_Alram);
		P_Schedule.add(p_Repeat);
		P_Schedule.add(rbtn_Important);
		P_Schedule.add(p_Btn);
		p_Schedule.add(P_Schedule);
	}
	public void addDel()
	{
		p_Del.setPreferredSize(new Dimension(300,800));
		ta_Del.setEnabled(false);
		try
		{
			new Read_todo(ID);
			ta_Del.setText(Read_todo.txt_todo);
		}
		catch(Exception e) {}
		JPanel P_Del=new JPanel();
		P_Del.setBackground(new Color(255,250,205));
		JLabel lbl_Del=new JLabel("삭제할 일정 이름");
		JTextField tf_Del=new JTextField(20);
		JPanel p_Btn=new JPanel();
		p_Btn.setBackground(new Color(255,250,205));
		ImageIcon add = new ImageIcon("images/일정삭제.png");
		Image orig = add.getImage();
		Image cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		add = new ImageIcon(cha);
		
		JButton btn_Del=new JButton(add);
		btn_Del.setBorderPainted(false);
		btn_Del.setContentAreaFilled(false);
		btn_Del.setFocusPainted(false);
		btn_Del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				setSize(800,800);
				try
				{
					String str;
					String[] array;
					String path=ID+File.separator+s+".txt";
					System.out.println(path);
					BufferedReader br=new BufferedReader(new FileReader(path));
					FileWriter fw=new FileWriter("수정용 파일.txt");
					while((str=br.readLine())!=null)
					{
						array=str.split("\\|");
						if(array[0].equals(tf_Del.getText()))
						{
							continue;
						}
						else
						{
							fw.write(str+"\r\n");
						}
					}
					fw.close();
					br=new BufferedReader(new FileReader("수정용 파일.txt"));
					fw=new FileWriter(path);
					fw.write("");
					fw.close();
					fw=new FileWriter(path,true);
					while((str=br.readLine())!=null)
					{
						fw.write(str+"\r\n");
					}
					if((str=br.readLine())==null)
					{
						ta_Today.setText("");
					}
					fw.close();
					br.close();
					br=new BufferedReader(new FileReader(path));
					while((str=br.readLine())!=null)
					{
						ta_Today.setText(ta_Today.getText()+str+"\r\n");
					}
					
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				for(int i=0;i<Today.al.size();i++)
				{
					Today.tt[i].interrupt();
				}
				Today.al.clear();
				Date date=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy년 MM월 dd일");
				String Path=ID+File.separator+sdf.format(date)+".txt";
				try
				{
					Today.al=new ArrayList<TestThread>();
					File f=new File(Path);
					BufferedReader buf=new BufferedReader(new FileReader(f));
					String line="";
					while((line=buf.readLine())!=null)
					{
						Today.al.add(new TestThread(line));
					}
					Today.tt=new TestThread[Today.al.size()];
					for(int i=0;i<Today.al.size();i++)
					{
						Today.tt[i]=Today.al.get(i);
						Today.tt[i].start();
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				ta_Del.setText("");
				card1.add(cal,BorderLayout.CENTER);
				card.show(getContentPane(),"1");
			}
		});
		add = new ImageIcon("images/취소.png");
		orig = add.getImage();
		cha = orig.getScaledInstance(70,47, Image.SCALE_SMOOTH);
		add = new ImageIcon(cha);
		
		JButton btn_Cancel=new JButton(add);
		btn_Cancel.setBorderPainted(false);
		btn_Cancel.setContentAreaFilled(false);
		btn_Cancel.setFocusPainted(false);
		btn_Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				setSize(800,800);
				ta_Del.setText("");
				card1.add(cal,BorderLayout.CENTER);
				card.show(getContentPane(),"1");
			}
		});
		if(isAdd==false)
		{
			p_Del.add(ta_Del,BorderLayout.EAST);
			P_Del.add(lbl_Del,BorderLayout.NORTH);
			P_Del.add(tf_Del,BorderLayout.SOUTH);
			p_Del.add(P_Del);
			p_Btn.add(btn_Del);
			p_Btn.add(btn_Cancel);
			p_Del.add(p_Btn);
		}
	}
	
	class btnEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent ae)
		{
			JButton b=(JButton)ae.getSource();
			switch(b.getText())
			{
			case"◀":
			{
				curMon.add(Calendar.MONTH,-1);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(curMon.get(Calendar.MONTH));
				System.out.println("month = "+month);
				System.out.println("(curMon.get(Calendar.MONTH)+1) = "+(curMon.get(Calendar.MONTH)+1));
				
				try
				{
					BufferedReader br=new BufferedReader(new FileReader("data.txt"));
					String s="";
					while((s=br.readLine())!=null)
					{
						String[] array=s.split("@");
						if(year==Integer.parseInt(array[0]))
						{
							if((curMon.get(Calendar.MONTH)+1)==Integer.parseInt(array[1]))
							{
								String[] array2=array[2].split(" ");
								for(int i=0;i<group.length;i++)
								{
									for(int j=0;j<array2.length;j++)
									{
										if(group[i].getText().equals(array2[j])&&(group[i].getBackground().equals(Color.white)||group[i].getBackground().equals(new Color(255,250,200))))
										{
											boolean isTrue=false;
											String path="학사일정"+File.separator+Calendar.getInstance().get(Calendar.YEAR)+"년 "+(curMon.get(Calendar.MONTH)+1)+"월 "+Integer.parseInt(array2[j])+"일 학사일정.txt";
											System.out.println(path);
											BufferedReader br2=new BufferedReader(new FileReader(path));
											String str="";
											while((str=br2.readLine())!=null)
											{
												if(str.equals("학) "+array[3]))
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
						String s="";
						while((s=br.readLine())!=null)
						{
							String[] array=s.split("@");
							if(year==Integer.parseInt(array[0]))
							{
								if((curMon.get(Calendar.MONTH)+1)==Integer.parseInt(array[1]))
								{
									String[] array2=array[2].split(" ");
									for(int i=0;i<group.length;i++)
									{
										for(int j=0;j<array2.length;j++)
										{
											if(group[i].getText().equals(array2[j])&&(group[i].getBackground().equals(Color.white)||group[i].getBackground().equals(new Color(255,250,200))))
											{
												String path="학사일정"+File.separator+year+"년 "+(curMon.get(Calendar.MONTH)+1)+"월 "+Integer.parseInt(array2[j])+"일 학사일정.txt";
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
				break;
			}
			case"▶":
			{
				curMon.add(Calendar.MONTH, 1);
				System.out.println(curMon.get(Calendar.MONTH));
				System.out.println("month = "+month);
				System.out.println("(curMon.get(Calendar.MONTH)+1) = "+(curMon.get(Calendar.MONTH)+1));
				try
				{
					BufferedReader br=new BufferedReader(new FileReader("data.txt"));
					String s="";
					while((s=br.readLine())!=null)
					{
						String[] array=s.split("@");
						if(year==Integer.parseInt(array[0]))
						{
							if((curMon.get(Calendar.MONTH)+1)==Integer.parseInt(array[1]))
							{
								String[] array2=array[2].split(" ");
								for(int i=0;i<group.length;i++)
								{
									for(int j=0;j<array2.length;j++)
									{
										if(group[i].getText().equals(array2[j])&&(group[i].getBackground().equals(Color.white)||group[i].getBackground().equals(new Color(255,250,200))))
										{
											boolean isTrue=false;
											String path="학사일정"+File.separator+Calendar.getInstance().get(Calendar.YEAR)+"년 "+(curMon.get(Calendar.MONTH)+1)+"월 "+Integer.parseInt(array2[j])+"일 학사일정.txt";
											System.out.println(path);
											BufferedReader br2=new BufferedReader(new FileReader(path));
											String str="";
											while((str=br2.readLine())!=null)
											{
												if(str.equals("학) "+array[3]))
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
						String s="";
						while((s=br.readLine())!=null)
						{
							String[] array=s.split("@");
							if(year==Integer.parseInt(array[0]))
							{
								if((curMon.get(Calendar.MONTH)+1)==Integer.parseInt(array[1]))
								{
									String[] array2=array[2].split(" ");
									for(int i=0;i<group.length;i++)
									{
										for(int j=0;j<array2.length;j++)
										{
											if(group[i].getText().equals(array2[j])&&(group[i].getBackground().equals(Color.white)||group[i].getBackground().equals(new Color(255,250,200))))
											{
												String path="학사일정"+File.separator+year+"년 "+(curMon.get(Calendar.MONTH)+1)+"월 "+Integer.parseInt(array2[j])+"일 학사일정.txt";
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
				break;
			}
			}
			setDays(curMon);
		}
	}
}

