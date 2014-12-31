import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI extends JFrame implements ActionListener{
	public ChessGUI(){
		private static final int WIDTH = 1000;
		private static final int HEIGHT = 850;
		private static JPanel gui_board;
		public ImageIcon black_tile, white_tile;
	}

	public ChessView(){
		setTitle("LS Chess Game");
		add(getJContentPane());
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}