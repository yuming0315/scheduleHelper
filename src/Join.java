import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Join extends JDialog {
	boolean isChecked=false;
	public Join(JFrame frame)
	{
		super(frame);
		setTitle("회원가입 화면");
		JPanel p = new JPanel();
		p.setBackground(new Color(255,250,205));
	  	JLabel l1= new JLabel("이름");	
        JLabel l2 = new JLabel("아이디");
        JLabel l3= new JLabel("패스워드");
        JLabel l4 = new JLabel("이메일");
        JLabel l5 = new JLabel("주소");
        add(l1); add(l2); add(l3); add(l4); add(l5);
        TextField t1 = new TextField();
        TextField t2 = new TextField();
        TextField t3 = new TextField();
        TextField t4 = new TextField();
        TextField t5 = new TextField();
        add(t1); add(t2); add(t3); add(t4); add(t5);
        t3.setEchoChar('*');
        ImageIcon jo = new ImageIcon("images/가입.png");
        Image orig = jo.getImage();
        Image cha = orig.getScaledInstance(45,43, Image.SCALE_SMOOTH);
        jo = new ImageIcon(cha);
        JButton j1 = new JButton(jo);
        j1.setBorderPainted(false);
        j1.setContentAreaFilled(false);
        j1.setFocusPainted(false);
        j1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae)
        	{
        		try
        		{
        			if(t1.getText().trim().equals("")||
        			   t2.getText().trim().equals("")||
        			   t3.getText().trim().equals("")||
        			   t4.getText().trim().equals("")||
        			   t5.getText().trim().equals(""))
        			{
        				JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다.");
        			}
        			else if(isChecked==true)
        			{
        				BufferedWriter bw_ID=new BufferedWriter(new FileWriter("회원명단.txt",true));
        				bw_ID.write(t1.getText()+"/"+t2.getText()+"/"+t3.getText()+"/"+t4.getText()+"/"+t5.getText()+"\r\n");
        				bw_ID.close();
        				BufferedWriter bw_Member=new BufferedWriter(new FileWriter("아이디비번목록.txt",true));
        				bw_Member.write(t2.getText()+"/"+t3.getText()+"\r\n");
        				bw_Member.close();
        				
        				String path=t2.getText();
        				File Folder=new File(path);
        				if(!Folder.exists())
        				{
        					try
        					{
        						Folder.mkdir();
        						System.out.println(t2.getText()+"폴더 생성");
        					}
        					catch(Exception e) {}
        				}
        				else
        				{
        					System.out.println("폴더가 이미 생성되어 있습니다.");
        				}
        				JOptionPane.showMessageDialog(null, "회원가입을 축하합니다!");
        				  String host     = "smtp.naver.com";
        				  final String user   = "jung00328";
        				  final String password  = "javasibal18";
        				  
        				  String to = t4.getText();//받는 사람 이메일

        				  // Get the session object
        				  Properties props = new Properties();
        				  props.put("mail.smtp.host", host);
        				  props.put("mail.smtp.auth", "true");

        				  Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
        				   protected PasswordAuthentication getPasswordAuthentication() {
        				    return new PasswordAuthentication(user, password);
        				   }
        				  });
        				  
        				  // Compose the message
        				  try {
        				   MimeMessage message = new MimeMessage(session);//메시지
        				   message.setFrom(new InternetAddress(user));
        				   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        				   // Subject
        				   message.setSubject("회원가입"); //메일 제목
        				   
        				   // Text
        				   message.setText(t1.getText()+"님 회원 가입 되었습니다."); //메일 내용 //두번째 문제 내용에는 아이디랑 비번 을 보내야됨
        				   
        				   // send the message
        				   Transport.send(message);
        				   System.out.println("메일을 보냈습니다!!");

        				  } catch (MessagingException e) {
        				   e.printStackTrace();
        				  }
        				dispose();
        			}
        			else
        			{
        				JOptionPane.showMessageDialog(null, "아이디 중복 체크를 해주세요.");
        			}
        		}
        		catch(Exception e)
        		{
        			JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다.");
        		}
        		
        	}
        });
        
        ImageIcon jo2 = new ImageIcon("images/취소.png");
        orig = jo2.getImage();
        cha = orig.getScaledInstance(68,46, Image.SCALE_SMOOTH);
        jo2 = new ImageIcon(cha);
        JButton j2 = new JButton(jo2);
        j2.setBorderPainted(false);
        j2.setContentAreaFilled(false);
        j2.setFocusPainted(false);
        j2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae)
        	{
        		dispose();
        	}
        });
        ImageIcon jo3 = new ImageIcon("images/중복체크.png");
        orig = jo3.getImage();
        cha = orig.getScaledInstance(60,47, Image.SCALE_SMOOTH);
        jo3 = new ImageIcon(cha);
        JButton j3 = new JButton(jo3);
        j3.setBorderPainted(false);
        j3.setContentAreaFilled(false);
        j3.setFocusPainted(false);
        j3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae)
        	{
        		try
        		{
        			FileReader fr=new FileReader("아이디비번목록.txt");
        			BufferedReader br=new BufferedReader(fr);
        			String s;
        			while((s=br.readLine())!=null)
        			{
        				String[] array=s.split("/");
        				if(array[0].equals(t2.getText()))
        				{
        					JOptionPane.showMessageDialog(null, "이미 사용중인 아이디입니다.");
        					return;
        				}
        			}
        			isChecked=true;
        			JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
        		}
        		catch(FileNotFoundException e)
        		{
        			isChecked=true;
        			JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
        		}
        		catch(IOException e)
        		{
        			JOptionPane.showMessageDialog(null, "파일을 열 수 없습니다.");
        		}
        		
        	}
        });
        add(j1); add(j2); add(j3);
        l1.setBounds(40, 10, 40, 40);
        l2.setBounds(40, 50, 40, 40);
        l3.setBounds(40,90,60,40);
        l4.setBounds(40, 130, 40, 40);
        l5.setBounds(40, 170, 60, 40);
 
        t1.setBounds(120, 10, 150, 30);
        t2.setBounds(120, 50, 150, 30);
        t3.setBounds(120, 90, 150, 30);
        t4.setBounds(120, 130, 100, 30);
        t5.setBounds(120, 180, 150, 30);

        j1.setBounds(90, 215, 80, 60);
        j2.setBounds(190, 220, 80, 50); 
        j3.setBounds(280, 40, 60, 60);
        
        
        add(p);
		setSize(400,320);
		setVisible(true);
	}
}