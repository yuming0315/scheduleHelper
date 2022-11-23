import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

public class Login extends JFrame {
   public Login()
   {
      //setLocationRelativeTo(null); 
      setTitle("로그인 화면");
      
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      JPanel p = new JPanel();
      p.setBackground(new Color(255,250,205));
      p.setLayout(null);
        JLabel l1= new JLabel("ID");   
        JLabel l2 = new JLabel("PW");
        add(l1); add(l2);
      
      TextField t1 = new TextField();
        TextField t2 = new TextField();
        t2.setEchoChar('*');
        add(t1); add(t2);
        
        ImageIcon Lo1 = new ImageIcon("images/로그인.png");
        Image orig = Lo1.getImage();
        Image cha = orig.getScaledInstance(60,58, Image.SCALE_SMOOTH);
        Lo1 = new ImageIcon(cha);

		JButton btn_Login=new JButton(Lo1);
		btn_Login.setBorderPainted(false);
		btn_Login.setContentAreaFilled(false);
		btn_Login.setFocusPainted(false);

      btn_Login.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae)
         {
            try
            {
               FileReader fr=new FileReader("아이디비번목록.txt");
               BufferedReader br=new BufferedReader(fr);
               String s="";
               while((s=br.readLine())!=null)
               {
                  String[] array=s.split("/");
                  if(array[0].equals(t1.getText()))
                  {
                     if(array[1].equals(t2.getText()))
                     {
                        JOptionPane.showMessageDialog(null, "로그인 성공");
                        setVisible(false);
                        System.out.println(t1.getText());
                        Today today=new Today(t1.getText());
                        return;
                     }
                  }
               }
               JOptionPane.showMessageDialog(null, "로그인 실패");
            }
            catch(FileNotFoundException e)
            {
               JOptionPane.showMessageDialog(null, "로그인 실패");
            }
            catch(IOException e)
            {
               JOptionPane.showMessageDialog(null, "파일을 찾을 수 없습니다.");
            }
         }
      });
      ImageIcon Lo2 = new ImageIcon("images/가입.png");
      orig = Lo2.getImage();
      cha = orig.getScaledInstance(60,58, Image.SCALE_SMOOTH);
      Lo2 = new ImageIcon(cha);

		JButton btn_Join=new JButton(Lo2);
		btn_Join.setBorderPainted(false);
		btn_Join.setContentAreaFilled(false);
		btn_Join.setFocusPainted(false);
      btn_Join.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae)
         {
            Join join=new Join(null);
         }
      });
      ImageIcon Lo3 = new ImageIcon("images/찾기.png");
      orig = Lo3.getImage();
      cha = orig.getScaledInstance(142,48, Image.SCALE_SMOOTH);
      Lo3 = new ImageIcon(cha);
      JButton btn_Find = new JButton(Lo3);
      btn_Find.setContentAreaFilled(false);
      btn_Find.setFocusPainted(false);
      btn_Find.setBorderPainted(false);
      btn_Find.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent ae)
         {    
            find f = new find();
         }
      });
      ImageIcon img = new ImageIcon("images/보노만세.png");
      orig = img.getImage();
      cha = orig.getScaledInstance(150,150, Image.SCALE_SMOOTH);
      img = new ImageIcon(cha);
      JLabel im = new JLabel(img);
      add(im);
      add(btn_Login); add(btn_Join); add(btn_Find);
      l1.setBounds(50, 50, 20, 20);//x,y,가로,세로
        l2.setBounds(50, 90, 20, 20);
        
        t1.setBounds(90, 50, 100, 20);
        t2.setBounds(90, 90, 100, 20);

        
        btn_Login.setBounds(200, 50, 90, 60);
        btn_Join.setBounds(50, 120, 90, 60); 
        btn_Find.setBounds(150, 120, 160, 60);
        im.setBounds(290,100,150,150);
        
        setSize(450,250);
        add(p);
      setVisible(true);
   }
}