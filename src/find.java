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
      setTitle("���̵�/��й�ȣ ã��");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(new GridLayout(3,1));
      JPanel p_NAME=new JPanel(new FlowLayout());
      p_NAME.setBackground(new Color(255,250,205));
      JLabel lbl_NAME=new JLabel("  �̸�");
      TextField tf_NAME=new TextField(20); 
      p_NAME.add(lbl_NAME); //�ǳڿ� �� ���̵�
      p_NAME.add(tf_NAME); //�ǳڿ� �ؽ�Ʈ ���̵�
      
      JPanel p_EMAIL=new JPanel(new FlowLayout());
      JLabel lbl_EMAIL=new JLabel("�̸���");
      TextField tf_EMAIL=new TextField(20);
      p_EMAIL.setBackground(new Color(255,250,205));
      p_EMAIL.add(lbl_EMAIL);
      p_EMAIL.add(tf_EMAIL);

      JPanel p_Btn=new JPanel(new FlowLayout());
		p_Btn.setBackground(new Color(255,250,205));
		ImageIcon Fi = new ImageIcon("images/ã��.png");
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
               FileReader fr2=new FileReader("ȸ�����.txt");
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
                            
                            String to = array[3];//�޴� ��� �̸���

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
                             MimeMessage message = new MimeMessage(session);//�޽���
                             message.setFrom(new InternetAddress(user));
                             message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                             // Subject
                             message.setSubject(array[0]+"���� ���̵�� ��й�ȣ �Դϴ�!!"); //���� ����
                             
                             // Text
                             message.setText(tf_NAME.getText()+"���� ���̵��"+ array[1]+"�̰�, ��й�ȣ�� "
                                   + array[2]+ "�Դϴ�."); //���� ���� //�ι�° ���� ���뿡�� ���̵�� ��� �� �����ߵ�
                             
                             // send the message
                             Transport.send(message);
                             System.out.println("���̵�� ��й�ȣ�� ���Ϸ� ���½��ϴ�!!");

                            } catch (MessagingException e) {
                             e.printStackTrace();
                            }
                        setVisible(false);
                        System.out.println(tf_NAME.getText()+ tf_EMAIL.getText());
                        
                        JOptionPane.showMessageDialog(null,array[0]+ "���� �̸��Ϸ� ���̵�� ����� ���½��ϴ�!!");
                     }
                  }
               }
            }
              catch(FileNotFoundException e) 
            { JOptionPane.showMessageDialog(null, "���̵� ��� �����Ⱑ ���� �Ͽ����ϴ�."); 
            }
             
            catch(IOException e)
            {
               JOptionPane.showMessageDialog(null, "������ ã�� �� �����ϴ�.");
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