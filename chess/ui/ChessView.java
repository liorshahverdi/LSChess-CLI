import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessView extends JFrame {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 850;
	private static JPanel gui_board;
	public ImageIcon black_tile, white_tile;
	public JTextField tf; 

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

		gui_board = drawInitialGUIBoard();
		temp.add(gui_board);

		tf = new JTextField("Im right here");
		tf.setEditable(false);
		temp.add(tf);

		return temp;
	}

	private JPanel drawInitialGUIBoard(){
		JPanel b = new JPanel(new GridLayout(8,8));

		//////////////////// ROW 1 /////////////////////////////
		JPanel t1 = new JPanel(new BorderLayout());
		JLabel lt1 = new JLabel("", black_tile, JLabel.CENTER);
		t1.add(lt1,BorderLayout.CENTER);
		b.add(t1);

		JPanel t2 = new JPanel(new BorderLayout());
		JLabel lt2 = new JLabel("", white_tile, JLabel.CENTER);
		t2.add(lt2,BorderLayout.CENTER);
		b.add(t2);

		JPanel t3 = new JPanel(new BorderLayout());
		JLabel lt3 = new JLabel("", black_tile, JLabel.CENTER);
		t3.add(lt3,BorderLayout.CENTER);
		b.add(t3);

		JPanel t4 = new JPanel(new BorderLayout());
		JLabel lt4 = new JLabel("", white_tile, JLabel.CENTER);
		t4.add(lt4,BorderLayout.CENTER);
		b.add(t4);

		JPanel t5 = new JPanel(new BorderLayout());
		JLabel lt5 = new JLabel("", black_tile, JLabel.CENTER);
		t5.add(lt5,BorderLayout.CENTER);
		b.add(t5);

		JPanel t6 = new JPanel(new BorderLayout());
		JLabel lt6 = new JLabel("", white_tile, JLabel.CENTER);
		t6.add(lt6,BorderLayout.CENTER);
		b.add(t6);

		JPanel t7 = new JPanel(new BorderLayout());
		JLabel lt7 = new JLabel("", black_tile, JLabel.CENTER);
		t7.add(lt7,BorderLayout.CENTER);
		b.add(t7);

		JPanel t8 = new JPanel(new BorderLayout());
		JLabel lt8 = new JLabel("", white_tile, JLabel.CENTER);
		t8.add(lt8,BorderLayout.CENTER);
		b.add(t8);
		//					END 

		//////////////////// ROW 2 /////////////////////////////
		JPanel t9 = new JPanel(new BorderLayout());
		JLabel lt9 = new JLabel("", white_tile, JLabel.CENTER);
		t9.add(lt9,BorderLayout.CENTER);
		b.add(t9);

		JPanel t10 = new JPanel(new BorderLayout());
		JLabel lt10 = new JLabel("", black_tile, JLabel.CENTER);
		t10.add(lt10,BorderLayout.CENTER);
		b.add(t10);

		JPanel t11 = new JPanel(new BorderLayout());
		JLabel lt11 = new JLabel("", white_tile, JLabel.CENTER);
		t11.add(lt11,BorderLayout.CENTER);
		b.add(t11);

		JPanel t12 = new JPanel(new BorderLayout());
		JLabel lt12 = new JLabel("", black_tile, JLabel.CENTER);
		t12.add(lt12,BorderLayout.CENTER);
		b.add(t12);

		JPanel t13 = new JPanel(new BorderLayout());
		JLabel lt13 = new JLabel("", white_tile, JLabel.CENTER);
		t13.add(lt13,BorderLayout.CENTER);
		b.add(t13);

		JPanel t14 = new JPanel(new BorderLayout());
		JLabel lt14 = new JLabel("", black_tile, JLabel.CENTER);
		t14.add(lt14,BorderLayout.CENTER);
		b.add(t14);

		JPanel t15 = new JPanel(new BorderLayout());
		JLabel lt15 = new JLabel("", white_tile, JLabel.CENTER);
		t15.add(lt15,BorderLayout.CENTER);
		b.add(t15);

		JPanel t16 = new JPanel(new BorderLayout());
		JLabel lt16 = new JLabel("", black_tile, JLabel.CENTER);
		t16.add(lt16,BorderLayout.CENTER);
		b.add(t16);
		//					END

		//////////////////// ROW 3 /////////////////////////////
		JPanel t17 = new JPanel(new BorderLayout());
		JLabel lt17 = new JLabel("", black_tile, JLabel.CENTER);
		t17.add(lt17,BorderLayout.CENTER);
		b.add(t17);

		JPanel t18 = new JPanel(new BorderLayout());
		JLabel lt18 = new JLabel("", white_tile, JLabel.CENTER);
		t18.add(lt18,BorderLayout.CENTER);
		b.add(t18);

		JPanel t19 = new JPanel(new BorderLayout());
		JLabel lt19 = new JLabel("", black_tile, JLabel.CENTER);
		t19.add(lt19,BorderLayout.CENTER);
		b.add(t19);

		JPanel t20 = new JPanel(new BorderLayout());
		JLabel lt20 = new JLabel("", white_tile, JLabel.CENTER);
		t20.add(lt20,BorderLayout.CENTER);
		b.add(t20);

		JPanel t21 = new JPanel(new BorderLayout());
		JLabel lt21 = new JLabel("", black_tile, JLabel.CENTER);
		t21.add(lt21,BorderLayout.CENTER);
		b.add(t21);

		JPanel t22 = new JPanel(new BorderLayout());
		JLabel lt22 = new JLabel("", white_tile, JLabel.CENTER);
		t22.add(lt22,BorderLayout.CENTER);
		b.add(t22);

		JPanel t23 = new JPanel(new BorderLayout());
		JLabel lt23 = new JLabel("", black_tile, JLabel.CENTER);
		t23.add(lt23,BorderLayout.CENTER);
		b.add(t23);

		JPanel t24 = new JPanel(new BorderLayout());
		JLabel lt24 = new JLabel("", white_tile, JLabel.CENTER);
		t24.add(lt24,BorderLayout.CENTER);
		b.add(t24);
		//					END

		////////////////////// ROW 4 ///////////////////////////
		JPanel t25 = new JPanel(new BorderLayout());
		JLabel lt25 = new JLabel("", white_tile, JLabel.CENTER);
		t25.add(lt25,BorderLayout.CENTER);
		b.add(t25);

		JPanel t26 = new JPanel(new BorderLayout());
		JLabel lt26 = new JLabel("", black_tile, JLabel.CENTER);
		t26.add(lt26,BorderLayout.CENTER);
		b.add(t26);

		JPanel t27 = new JPanel(new BorderLayout());
		JLabel lt27 = new JLabel("", white_tile, JLabel.CENTER);
		t27.add(lt27,BorderLayout.CENTER);
		b.add(t27);

		JPanel t28 = new JPanel(new BorderLayout());
		JLabel lt28 = new JLabel("", black_tile, JLabel.CENTER);
		t28.add(lt28,BorderLayout.CENTER);
		b.add(t28);

		JPanel t29 = new JPanel(new BorderLayout());
		JLabel lt29 = new JLabel("", white_tile, JLabel.CENTER);
		t29.add(lt29,BorderLayout.CENTER);
		b.add(t29);

		JPanel t30 = new JPanel(new BorderLayout());
		JLabel lt30 = new JLabel("", black_tile, JLabel.CENTER);
		t30.add(lt30,BorderLayout.CENTER);
		b.add(t30);

		JPanel t31 = new JPanel(new BorderLayout());
		JLabel lt31 = new JLabel("", white_tile, JLabel.CENTER);
		t31.add(lt31,BorderLayout.CENTER);
		b.add(t31);

		JPanel t32 = new JPanel(new BorderLayout());
		JLabel lt32 = new JLabel("", black_tile, JLabel.CENTER);
		t32.add(lt32,BorderLayout.CENTER);
		b.add(t32);
		//						END

		///////////////////////// ROW 5 ////////////////////////		
		JPanel t33 = new JPanel(new BorderLayout());
		JLabel lt33 = new JLabel("", black_tile, JLabel.CENTER);
		t33.add(lt33,BorderLayout.CENTER);
		b.add(t33);

		JPanel t34 = new JPanel(new BorderLayout());
		JLabel lt34 = new JLabel("", white_tile, JLabel.CENTER);
		t34.add(lt34,BorderLayout.CENTER);
		b.add(t34);

		JPanel t35 = new JPanel(new BorderLayout());
		JLabel lt35 = new JLabel("", black_tile, JLabel.CENTER);
		t35.add(lt35,BorderLayout.CENTER);
		b.add(t35);

		JPanel t36 = new JPanel(new BorderLayout());
		JLabel lt36 = new JLabel("", white_tile, JLabel.CENTER);
		t36.add(lt36,BorderLayout.CENTER);
		b.add(t36);

		JPanel t37 = new JPanel(new BorderLayout());
		JLabel lt37 = new JLabel("", black_tile, JLabel.CENTER);
		t37.add(lt37,BorderLayout.CENTER);
		b.add(t37);

		JPanel t38 = new JPanel(new BorderLayout());
		JLabel lt38 = new JLabel("", white_tile, JLabel.CENTER);
		t38.add(lt38,BorderLayout.CENTER);
		b.add(t38);

		JPanel t39 = new JPanel(new BorderLayout());
		JLabel lt39 = new JLabel("", black_tile, JLabel.CENTER);
		t39.add(lt39,BorderLayout.CENTER);
		b.add(t39);

		JPanel t40 = new JPanel(new BorderLayout());
		JLabel lt40 = new JLabel("", white_tile, JLabel.CENTER);
		t40.add(lt40,BorderLayout.CENTER);
		b.add(t40);
		//						END

		///////////////////////// ROW 6 ////////////////////////
		JPanel t41 = new JPanel(new BorderLayout());
		JLabel lt41 = new JLabel("", white_tile, JLabel.CENTER);
		t41.add(lt41,BorderLayout.CENTER);
		b.add(t41);

		JPanel t42 = new JPanel(new BorderLayout());
		JLabel lt42 = new JLabel("", black_tile, JLabel.CENTER);
		t42.add(lt42,BorderLayout.CENTER);
		b.add(t42);

		JPanel t43 = new JPanel(new BorderLayout());
		JLabel lt43 = new JLabel("", white_tile, JLabel.CENTER);
		t43.add(lt43,BorderLayout.CENTER);
		b.add(t43);

		JPanel t44 = new JPanel(new BorderLayout());
		JLabel lt44 = new JLabel("", black_tile, JLabel.CENTER);
		t44.add(lt44,BorderLayout.CENTER);
		b.add(t44);

		JPanel t45 = new JPanel(new BorderLayout());
		JLabel lt45 = new JLabel("", white_tile, JLabel.CENTER);
		t45.add(lt45,BorderLayout.CENTER);
		b.add(t45);

		JPanel t46 = new JPanel(new BorderLayout());
		JLabel lt46 = new JLabel("", black_tile, JLabel.CENTER);
		t46.add(lt46,BorderLayout.CENTER);
		b.add(t46);

		JPanel t47 = new JPanel(new BorderLayout());
		JLabel lt47 = new JLabel("", white_tile, JLabel.CENTER);
		t47.add(lt47,BorderLayout.CENTER);
		b.add(t47);

		JPanel t48 = new JPanel(new BorderLayout());
		JLabel lt48 = new JLabel("", black_tile, JLabel.CENTER);
		t48.add(lt48,BorderLayout.CENTER);
		b.add(t48);
		//						END

		///////////////////////// ROW 7 ////////////////////////
		JPanel t49 = new JPanel(new BorderLayout());
		JLabel lt49 = new JLabel("", black_tile, JLabel.CENTER);
		t49.add(lt49,BorderLayout.CENTER);
		b.add(t49);

		JPanel t50 = new JPanel(new BorderLayout());
		JLabel lt50 = new JLabel("", white_tile, JLabel.CENTER);
		t50.add(lt50,BorderLayout.CENTER);
		b.add(t50);

		JPanel t51 = new JPanel(new BorderLayout());
		JLabel lt51 = new JLabel("", black_tile, JLabel.CENTER);
		t51.add(lt51,BorderLayout.CENTER);
		b.add(t51);

		JPanel t52 = new JPanel(new BorderLayout());
		JLabel lt52 = new JLabel("", white_tile, JLabel.CENTER);
		t52.add(lt52,BorderLayout.CENTER);
		b.add(t52);

		JPanel t53 = new JPanel(new BorderLayout());
		JLabel lt53 = new JLabel("", black_tile, JLabel.CENTER);
		t53.add(lt53,BorderLayout.CENTER);
		b.add(t53);

		JPanel t54 = new JPanel(new BorderLayout());
		JLabel lt54 = new JLabel("", white_tile, JLabel.CENTER);
		t54.add(lt54,BorderLayout.CENTER);
		b.add(t54);

		JPanel t55 = new JPanel(new BorderLayout());
		JLabel lt55 = new JLabel("", black_tile, JLabel.CENTER);
		t55.add(lt55,BorderLayout.CENTER);
		b.add(t55);

		JPanel t56 = new JPanel(new BorderLayout());
		JLabel lt56 = new JLabel("", white_tile, JLabel.CENTER);
		t56.add(lt56,BorderLayout.CENTER);
		b.add(t56);
		//						END

		///////////////////////// ROW 8 ////////////////////////
		JPanel t57 = new JPanel(new BorderLayout());
		JLabel lt57 = new JLabel("", white_tile, JLabel.CENTER);
		t57.add(lt57,BorderLayout.CENTER);
		b.add(t57);

		JPanel t58 = new JPanel(new BorderLayout());
		JLabel lt58 = new JLabel("", black_tile, JLabel.CENTER);
		t58.add(lt58,BorderLayout.CENTER);
		b.add(t58);

		JPanel t59 = new JPanel(new BorderLayout());
		JLabel lt59 = new JLabel("", white_tile, JLabel.CENTER);
		t59.add(lt59,BorderLayout.CENTER);
		b.add(t59);

		JPanel t60 = new JPanel(new BorderLayout());
		JLabel lt60 = new JLabel("", black_tile, JLabel.CENTER);
		t60.add(lt60,BorderLayout.CENTER);
		b.add(t60);

		JPanel t61 = new JPanel(new BorderLayout());
		JLabel lt61 = new JLabel("", white_tile, JLabel.CENTER);
		t61.add(lt61,BorderLayout.CENTER);
		b.add(t61);

		JPanel t62 = new JPanel(new BorderLayout());
		JLabel lt62 = new JLabel("", black_tile, JLabel.CENTER);
		t62.add(lt62,BorderLayout.CENTER);
		b.add(t62);

		JPanel t63 = new JPanel(new BorderLayout());
		JLabel lt63 = new JLabel("", white_tile, JLabel.CENTER);
		t63.add(lt63,BorderLayout.CENTER);
		b.add(t63);

		JPanel t64 = new JPanel(new BorderLayout());
		JLabel lt64 = new JLabel("", black_tile, JLabel.CENTER);
		t64.add(lt64,BorderLayout.CENTER);
		b.add(t64);

		return b;
	}

	public static void main(String[] args) {
		try {
			ChessView cv = new ChessView(); 
		}catch(Exception e){System.out.println(e.getMessage());} 
	}
}