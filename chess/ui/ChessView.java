import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessView extends JFrame implements MouseListener, MouseMotionListener {
	private static final int WIDTH = 850;
	private static final int HEIGHT = 850;
	private static JPanel gui_board;
	private ImageIcon black_tile, white_tile;

	public ChessView(){
		setTitle("LS Chess Game");
		add(getJContentPane());
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private JPanel getJContentPane(){
		JPanel temp = new JPanel();
		temp.setBackground(Color.red);
		temp.setLayout(new FlowLayout());

		black_tile = new ImageIcon("images\\Black-Marble.jpg");
		white_tile = new ImageIcon("images\\White-Marble.jpg");

		gui_board = new JPanel();
		gui_board.setLayout(new GridLayout(8,8));
		temp.add(gui_board);

		JPanel tile1 = new JPanel();

		return temp;
	}

	public void mouseClicked(MouseEvent me) {}

	public void mousePressed(MouseEvent me) {}

	public void mouseReleased(MouseEvent me) {}

	public void mouseEntered(MouseEvent me) {}

	public void mouseExited(MouseEvent me) {}

	public void mouseMoved(MouseEvent me){}

	public void mouseDragged(MouseEvent me){}
	
	public static void main(String[] args) {
		try {
			ChessView cv = new ChessView(); 
		}catch(Exception e){System.out.println(e.getMessage());} 
	}
}