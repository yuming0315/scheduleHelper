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
public class find extends JFrame {
   public find()
   {   
      setTitle("아이디/비밀번호 찾기");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(new GridLayout(3,1));
      JPanel p_NAME=new JPanel(new FlowLayout());
      p_NAME.setBackground(new Color(255,250,205));
      JLabel lbl_NAME=new JLabel("  이름");
      TextField tf_NAME=new TextField(20); 
      p_NAME.add(lbl_NAME); //판넬에 라벨 아이디
      p_NAME.add(tf_NAME); //판넬에 텍스트 아이디
      
      JPanel p_EMAIL=new JPanel(new FlowLayout());
      JLabel lbl_EMAIL=new JLabel("이메일");
      TextField tf_EMAIL=new TextField(20);
      p_EMAIL.setBackground(new Color(255,250,205));
      p_EMAIL.add(lbl_EMAIL);
      p_EMAIL.add(tf_EMAIL);

      JPanel p_Btn=new JPanel(new FlowLayout());
		p_Btn.setBackground(new Color(255,250,205));
		ImageIcon Fi = new ImageIcon("images/찾기.png");
	    Image orig = Fi.getImage();
	    Image cha = orig.getScaledInstance(132,38, Image.SCALE_SMOOTH);
	    Fi = new ImageIcon(cha);
		JButton btn_FIND=new JButton(Fi);
		btn_FIND.setContentAreaFilled(false);
		btn_FIND.setFocusPainted(false);
		btn_FIND.setBorderPainted(false);
      btn_FIND.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae)
         {
            try
            {
               FileReader fr2=new FileReader("회원명단.txt");
               BufferedReader br2=new BufferedReader(fr2);
               String s2="";
               while((s2=br2.readLine())!=null)
               {
                  String[] array=s2.split("/");
                  if(array[0].equals(tf_NAME.getText()))
                  {
                     if(array[3].equals(tf_EMAIL.getText()))
                     {
                        String host     = "smtp.naver.com";
                            final String user   = "jung00328";
                            final String password  = "javasibal18";
                            
                            String to = array[3];//받는 사람 이메일

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
                             message.setSubject(array[0]+"님의 아이디와 비밀번호 입니다!!"); //메일 제목
                             
                             // Text
                             message.setText(tf_NAME.getText()+"님의 아이디는"+ array[1]+"이고, 비밀번호는 "
                                   + array[2]+ "입니다."); //메일 내용 //두번째 문제 내용에는 아이디랑 비번 을 보내야됨
                             
                             // send the message
                             Transport.send(message);
                             System.out.println("아이디와 비밀번호를 메일로 보냈습니다!!");

                            } catch (MessagingException e) {
                             e.printStackTrace();
                            }
                        setVisible(false);
                        System.out.println(tf_NAME.getText()+ tf_EMAIL.getText());
                        
                        JOptionPane.showMessageDialog(null,array[0]+ "님의 이메일로 아이디와 비번을 보냈습니다!!");
                     }
                  }
               }
            }
              catch(FileNotFoundException e) 
            { JOptionPane.showMessageDialog(null, "아이디 비번 보내기가 실패 하였습니다."); 
            }
             
            catch(IOException e)
            {
               JOptionPane.showMessageDialog(null, "파일을 찾을 수 없습니다.");
            }
         }
      });
      p_Btn.add(btn_FIND);
      add(p_NAME);
      add(p_EMAIL);
      add(p_Btn);
      setSize(200,200);
      setVisible(true);
   }
}