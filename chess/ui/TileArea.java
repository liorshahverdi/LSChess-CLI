import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TileArea extends JPanel implements MouseListener, MouseMotionListener{
	
	private JLabel lbl;
	private ImageIcon img; 

	public TileArea(String wob){
		setLayout(new BorderLayout());
		if (wob.equals("black")) img = new ImageIcon("images\\Black-Marble.jpg");
		else if (wob.equals("white")) img = new ImageIcon("images\\White-Marble.jpg");

		lbl = new JLabel("", img, JLabel.CENTER);
		add(lbl, BorderLayout.CENTER);
	}

	public JLabel getJLabel(){ return lbl; }

	public void mouseClicked(MouseEvent e){

	}

	public void mouseEntered(MouseEvent e){
		System.out.println("Entered!");
	}

	public void mouseExited(MouseEvent e){}

	public void mousePressed(MouseEvent e){}

	public void mouseReleased(MouseEvent e){}

	public void mouseDragged(MouseEvent e){}

	public void mouseMoved(MouseEvent e){}
}