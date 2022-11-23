import java.awt.*;
import javax.swing.*;


public class border  extends JFrame{
public border() {

	setTitle("â");
	JPanel t= new JPanel(new GridLayout(1,2));
	JPanel p= new JPanel();
	p.setBackground(Color.pink);
	JPanel li = new JPanel(new BorderLayout());
	li.setBackground(Color.black);
	JLabel l = new JLabel("�찡");
	li.add(l, BorderLayout.CENTER);
	t.add(t);
	t.add(li);
	add(t);
	setSize(300,300);
	setVisible(true);
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		border dljif = new border();
	}

}
