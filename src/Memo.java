import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class Memo extends JPanel {	
	JTextArea ta = new JTextArea("", 10, 20);
	String ID;
	public Memo() {
		setBackground(new Color(255,250,205));
		JLabel m=new JLabel("메모장");
		setLayout(new BorderLayout()); 
		ta.setEnabled(false);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		
		ImageIcon c = new ImageIcon("images/수정.png");
		Image orig = c.getImage();
		Image cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		c = new ImageIcon(cha);
		
		ImageIcon d = new ImageIcon("images/삭제.png");
		orig = d.getImage();
		cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		d = new ImageIcon(cha);
		
		ImageIcon s = new ImageIcon("images/저장.png");
		orig = s.getImage();
		cha = orig.getScaledInstance(50,48, Image.SCALE_SMOOTH);
		s = new ImageIcon(cha);
		JButton correct = new JButton(c); // 수정버튼 
		JButton delete = new JButton(d);// 삭제버튼
		JButton save = new JButton(s); // 저장버튼
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
			System.out.println("메모 파일이 없습니다.");
		}
		catch(IOException e) 
		{
			System.out.println("입출력 오류");
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
			System.out.println("오류");
		}
	}
	public void clearMemo()
	{
		ta.setText("");
		setMemo();
	}
}