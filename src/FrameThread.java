import javax.swing.*;

public class FrameThread extends Thread {
	public FrameThread()
	{
		try
		{
			JFrame f=new JFrame("�˶�");
			f.setSize(200,200);
			f.setVisible(true);
			Thread.sleep(2000);
			f.dispose();
			return;
		}
		catch(Exception e) {}
	}
}
