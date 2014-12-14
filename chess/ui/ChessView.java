import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessView extends JFrame implements ActionListener{
	private static final int WIDTH = 850;
	private static final int HEIGHT = 850;

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
		return temp;
	}

	public void actionPerformed(ActionEvent e){
		
	}

	public static void main(String[] args) {
		try {
			ChessView cv = new ChessView(); 
		}catch(Exception e){System.out.println(e.getMessage());} 
	}
}