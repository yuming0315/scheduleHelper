import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class Memo extends JPanel {	
	JTextArea ta = new JTextArea("", 10, 20);
	String ID;
	public Memo() {
		setBackground(new Color(255,250,205));
		JLabel m=new JLabel("�޸���");
		setLayout(new BorderLayout()); 
		ta.setEnabled(false);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		
		ImageIcon c = new ImageIcon("images/����.png");
		Image orig = c.getImage();
		Image cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		c = new ImageIcon(cha);
		
		ImageIcon d = new ImageIcon("images/����.png");
		orig = d.getImage();
		cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		d = new ImageIcon(cha);
		
		ImageIcon s = new ImageIcon("images/����.png");
		orig = s.getImage();
		cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		s = new ImageIcon(cha);
		JButton correct = new JButton(c); // ������ư 
		JButton delete = new JButton(d);// ������ư
		JButton save = new JButton(s); // �����ư
		correct.setBorderPainted(false);
		correct.setContentAreaFilled(false);
		correct.setFocusPainted(false);
		
		delete.setBorderPainted(false);
		delete.setContentAreaFilled(false);
		delete.setFocusPainted(false);
		
		save.setBorderPainted(false);
		save.setContentAreaFilled(false);
		save.setFocusPainted(false);
		
		JPanel p_btn=new JPanel();
		p_btn.setBackground(new Color(255,250,205));
		p_btn.add(correct);
		p_btn.add(delete);
		p_btn.add(save);
		add(m,BorderLayout.NORTH);
		add(new JScrollPane(ta),BorderLayout.CENTER);
		add(p_btn,BorderLayout.SOUTH);
		save.setEnabled(false);
		setSize(300,350);
		setVisible(true);
		correct.addActionListener(new ActionListener() {  
			@Override 
			public void actionPerformed(ActionEvent e) { 
				ta.setEnabled(true);
				correct.setEnabled(false);
				save.setEnabled(true);
			} 
		});
		save.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) { 
				ta.setEnabled(false);
				setMemo();
				save.setEnabled(false);
				correct.setEnabled(true);
			 } 
		});
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearMemo();
			}
		});
	}
	public void getMemo()
	{
		this.ID=Schedular.ID;
		ta.setText("");
		String path=ID+File.separator+Schedular.s+" memo.txt";
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(path));
			String str;
			while((str=br.readLine())!=null)
			{
				ta.setText(ta.getText()+str+"\r\n");
			}
			br.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("�޸� ������ �����ϴ�.");
		}
		catch(IOException e) 
		{
			System.out.println("����� ����");
		}
	}
	public void setMemo()
	{
		try
		{
			this.ID=Schedular.ID;
			String path=ID+File.separator+Schedular.s+" memo.txt";
			BufferedWriter bw=new BufferedWriter(new FileWriter(path,false));
			bw.write(ta.getText());
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println("����");
		}
	}
	public void clearMemo()
	{
		ta.setText("");
		setMemo();
	}
}