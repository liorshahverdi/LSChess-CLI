import java.util.ArrayList;
import java.util.Scanner;
public class CG{
	public static boolean check;
	public static boolean mate;
	public static String[][] board;
	public static int cp;//will be modded by 2 at each iteration
	public ArrayList<ChessPiece> offBoard;

	public CG(){
		check = false;
		mate = false;
		cp = 1;
		board = new String[][]{
			/*{"b_c","b_h","b_b","b_k","b_q","b_b","b_h","b_c"},
			{"b_p","b_p","b_p","b_p","b_p","b_p","b_p","b_p"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"w_p","w_p","w_p","w_p","w_p","w_p","w_p","w_p"},
			{"w_c","w_h","w_b","w_k","w_q","w_b","w_h","w_c"}*/

			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"b_k"},
			{"-",  "-"  ,"-"  ,"-"  ,"w_q","-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"w_c"  ,"-"  ,"-"}
		};
		offBoard = new ArrayList<ChessPiece>();
		//printMat(board);
		System.out.println("Welcome to LSChess! Let's play!");
		startGame();
	}

	public enum ChessPiece{
		//possible pieces (or empty) tiles
		WHITE_CASTLE("w_c"), WHITE_KNIGHT("w_h"), WHITE_BISHOP("w_b"), WHITE_QUEEN("w_q"), WHITE_KING("w_k"), WHITE_PAWN("w_p"),
		BLACK_CASTLE("b_c"), BLACK_KNIGHT("b_h"), BLACK_BISHOP("b_b"), BLACK_QUEEN("b_q"), BLACK_KING("b_k"), BLACK_PAWN("b_p"), 
		EMPTY("-");
	
		private String str;
		private ChessPiece(String s){this.str = s;}
		//String representation of enum type
		private String getStr(){return str;}

		private static ArrayList<String> possibleMoves(String[][] b){
			ArrayList<String> temp = new ArrayList<String>();

			boolean white_turn = false;
			boolean black_turn = false;

			if (cp%2 == 0) black_turn = true;
			else white_turn = true;

			int i = 0;
			for (String[] row : b){
				for (String x : row){
					ChessPiece thisPiece = ChessPiece.getEnum(x);
					if (thisPiece == ChessPiece.WHITE_PAWN){
							//String now = "White Pawn @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row
							if (white_turn){
								//check in front
								if (r-1 >= 0){
									if (ChessPiece.getEnum(b[r-1][c]) == ChessPiece.EMPTY){
										//System.out.println("w_p move to row "+(r-1)+" column "+c);
										Cell move = new Cell(r-1, c);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
									}
								}
								//2 in front if pawn's first move
								if (r == 6){
									if (ChessPiece.getEnum(b[r-2][c]) == ChessPiece.EMPTY){
										//System.out.println("w_p move to row "+(r-1)+" column "+c);
										Cell move = new Cell(r-2, c);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
									}
								}

								//check if left-edge pawn
								if (c == 0){
									//only check top-right-diag for kill option
									if ((ChessPiece.getEnum(b[r-1][c+1]) != ChessPiece.EMPTY) && isBlack(ChessPiece.getEnum(b[r-1][c+1]))){
										Cell toKill = new Cell(r-1, c+1);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}
								//check if right-edge pawn
								else if (c == 7){
									//only check top-left-diag for kill option
									if ((ChessPiece.getEnum(b[r-1][c-1]) != ChessPiece.EMPTY) && isBlack(ChessPiece.getEnum(b[r-1][c-1]))){
										Cell toKill = new Cell(r-1, c-1);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}
								else{
									//check top-left diagonal FOR KILL OPTION
									if ((ChessPiece.getEnum(b[r-1][c-1])) != ChessPiece.EMPTY && isBlack(ChessPiece.getEnum(b[r-1][c-1]))){
										Cell toKill = new Cell(r-1, c-1);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									
									//check top-right diagonal for KILL OPTION
									if ((ChessPiece.getEnum(b[r-1][c+1])) != ChessPiece.EMPTY && isBlack(ChessPiece.getEnum(b[r-1][c+1]))){
										Cell toKill = new Cell(r-1, c+1);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}
							}
							if (black_turn){
								//compute opposite turn's moves, first in front
								if (r+1<=7){
									if (ChessPiece.getEnum(b[r+1][c]) == ChessPiece.EMPTY){
										//System.out.println("w_p move to row "+(r-1)+" column "+c);
										Cell move = new Cell(r+1, c);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
									}
								}
								if (r==1){
									if (ChessPiece.getEnum(b[r+2][c]) == ChessPiece.EMPTY){
										//System.out.println("w_p move to row "+(r-1)+" column "+c);
										Cell move = new Cell(r+2, c);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
									}
								}
								if (r+1 <=7 && c+1<=7){
									if ((ChessPiece.getEnum(b[r+1][c+1])) != ChessPiece.EMPTY && isBlack(ChessPiece.getEnum(b[r+1][c+1]))){
										Cell toKill = new Cell(r+1, c+1);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}
								if (r+1 <=7 && c-1>=0){
									//check top-right diagonal for KILL OPTION
									if ((ChessPiece.getEnum(b[r+1][c-1])) != ChessPiece.EMPTY && isBlack(ChessPiece.getEnum(b[r+1][c-1]))){
										Cell toKill = new Cell(r+1, c-1);
										temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}
							}
					}
					if (thisPiece == ChessPiece.WHITE_CASTLE){
							//String now = "White castle @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row

							//check from left to right

							//check if located @ leftmost-column
							if (c==0){
								//look right
								int dt = 1;
								boolean collision_right = false;
								while (!collision_right){
									if (c+dt == 8) break;
									if (ChessPiece.getEnum(b[r][c+dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(b[r][c+dt]))){
											Cell toKill = new Cell(r, c+dt);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_right = true;
									}
									else 
									{
										Cell move = new Cell(r, c+dt);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								//look up & down if possible
								//UP
								dt = 1;
								boolean collision_up = false;
								while (!collision_up){
									if (r-dt == -1) break;
									if (ChessPiece.getEnum(b[r-dt][c]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(b[r-dt][c]))){
											Cell toKill = new Cell(r-dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_up = true;
									}
									else
									{
										Cell move = new Cell(r-dt, c);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								//DOWN
								dt = 1;
								boolean collision_down = false;
								while(!collision_down){
									if (r+dt==8) break;
									if (ChessPiece.getEnum(b[r+dt][c]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(b[r+dt][c]))){
											Cell toKill = new Cell(r+dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_down = true;
									}
									else
									{
										Cell move = new Cell(r+dt, c);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
							}
							else if (c>=1 && c<7){
								int dt = 1;
								//LEFT
								boolean collision_left = false;
								while(!collision_left){
									if (c-dt == -1) break;
									if (ChessPiece.getEnum(b[r][c-dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(b[r][c-dt]))){
											Cell toKill = new Cell(r, c-dt);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_left = true;
									}
									else
									{
										Cell move = new Cell(r, c-dt);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								dt=1;
								//RIGHT
								boolean collision_right = false;
								while (!collision_right){
									if (c+dt == 8) break;
									if (ChessPiece.getEnum(b[r][c+dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(b[r][c+dt]))){
											Cell toKill = new Cell(r, c+dt);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_right = true;
									}
									else 
									{
										Cell move = new Cell(r, c+dt);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								dt = 1;
								//look up and down if possible
								//UP
								if (r != 0){
									boolean collision_up = false;
									while (!collision_up){
										if (r-dt == -1) break;
										if (ChessPiece.getEnum(b[r-dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isBlack(ChessPiece.getEnum(b[r-dt][c]))){
												Cell toKill = new Cell(r-dt, c);
												temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
												toKill.getRow()+" c "+toKill.getCol());
											}
											collision_up = true;
										}
										else
										{
											Cell move = new Cell(r-dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
											dt++;
										}
									}
								}
								dt=1;
								//DOWN
								if (r != 7){
									boolean collision_down = false;
									while(!collision_down){
										if (r+dt==8) break;
										if (ChessPiece.getEnum(b[r+dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isBlack(ChessPiece.getEnum(b[r+dt][c]))){
												Cell toKill = new Cell(r+dt, c);
												temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
												toKill.getRow()+" c "+toKill.getCol());
											}
											collision_down = true;
										}
										else
										{
											Cell move = new Cell(r+dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
											dt++;
										}
									}
								}
							}
							else if (c==7){
								int dt = 1;
								//LEFT
								boolean collision_left = false;
								while(!collision_left){
									if (c-dt == -1) break;
									if (ChessPiece.getEnum(b[r][c-dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(b[r][c-dt]))){
											Cell toKill = new Cell(r, c-dt);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_left = true;
									}
									else
									{
										Cell move = new Cell(r, c-dt);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								dt=1;
								//UP
								if (r != 0){
									boolean collision_up = false;
									while (!collision_up){
										if (r-dt == -1) break;
										if (ChessPiece.getEnum(b[r-dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isBlack(ChessPiece.getEnum(b[r-dt][c]))){
												Cell toKill = new Cell(r-dt, c);
												temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
												toKill.getRow()+" c "+toKill.getCol());
											}
											collision_up = true;
										}
										else
										{
											Cell move = new Cell(r-dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
											dt++;
										}
									}
								}
								dt=1;
								//DOWN
								if (r != 7){
									boolean collision_down = false;
									while(!collision_down){
										if (r+dt==8) break;
										if (ChessPiece.getEnum(b[r+dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isBlack(ChessPiece.getEnum(b[r+dt][c]))){
												Cell toKill = new Cell(r+dt, c);
												temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
												toKill.getRow()+" c "+toKill.getCol());
											}
											collision_down = true;
										}
										else
										{
											Cell move = new Cell(r+dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
											dt++;
										}
									}
								}
							}
					}
					if (thisPiece == ChessPiece.WHITE_KNIGHT){
							//String now = "White knight @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row
							
							final int two = 2;
							final int one = 1;

							////LEFT SIDE////

							////UP_2_LEFT_1////
							if ((r-two >= 0) && (c-one >= 0)){	
								if (ChessPiece.getEnum(b[r-two][c-one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r-two][c-one]))){
										Cell toKill = new Cell(r-two, c-one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-two, c-one);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							/////////////////

							////UP_1_LEFT_2////
							if ((r-one >= 0) && (c-two >= 0)){	
								if (ChessPiece.getEnum(b[r-one][c-two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r-one][c-two]))){
										Cell toKill = new Cell(r-two, c-one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-one, c-two);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							/////////////////

							////DOWN_1_LEFT_2////
							if ((r+one <= 7) && (c-two >= 0)){	
								if (ChessPiece.getEnum(b[r+one][c-two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r+one][c-two]))){
										Cell toKill = new Cell(r+one, c-two);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+one, c-two);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							/////////////////

							////DOWN_2_LEFT_1////
							if ((r+two <= 7) && (c-one >= 0)){	
								if (ChessPiece.getEnum(b[r+two][c-one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r+two][c-one]))){
										Cell toKill = new Cell(r+two, c-one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+two, c-one);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							/////////////////

							////RIGHT SIDE/////

							////UP_2_RIGHT_1////
							if ((r-two >= 0) && (c+one <= 7)){	
								if (ChessPiece.getEnum(b[r-two][c+one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r-two][c+one]))){
										Cell toKill = new Cell(r-two, c+one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-two, c+one);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							////////////////////

							////UP_1_RIGHT_2////
							if ((r-one >= 0) && (c+two <= 7)){	
								if (ChessPiece.getEnum(b[r-one][c+two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r-one][c+two]))){
										Cell toKill = new Cell(r-one, c+two);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-one, c+two);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							////////////////////

							////DOWN_1_RIGHT_2////
							if ((r+one <= 7) && (c+two <= 7)){	
								if (ChessPiece.getEnum(b[r+one][c+two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r+one][c+two]))){
										Cell toKill = new Cell(r+one, c+two);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+one, c+two);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							////////////////////

							////DOWN_2_RIGHT_1////
							if ((r+two <= 7) && (c+one <= 7)){	
								if (ChessPiece.getEnum(b[r+two][c+one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r+two][c+one]))){
										Cell toKill = new Cell(r+two, c+one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+two, c+one);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							////////////////////
					}
					if (thisPiece == ChessPiece.WHITE_BISHOP){
							//String now = "White castle @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row

							int dt = 1;
							boolean collision_tl = false;
							while (!collision_tl){
								if ((c-dt == -1) || (r-dt == -1)) break;
								if (ChessPiece.getEnum(b[r-dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r-dt][c-dt]))){
										Cell toKill = new Cell(r-dt, c-dt);
										temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_tl = true;
								}
								else{
									Cell move = new Cell(r-dt, c-dt);
									temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							dt = 1;
							boolean collision_bl = false;
							while(!collision_bl){
								if ((c-dt == -1) || (r+dt == 8)) break;
								if (ChessPiece.getEnum(b[r+dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r+dt][c-dt]))){
										Cell toKill = new Cell(r+dt, c-dt);
										temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_bl = true;
								}
								else{
									Cell move = new Cell(r+dt, c-dt);
									temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							dt = 1;
							boolean collision_tr = false;
							while(!collision_tr){
								if ((c+dt == 8) ||(r-dt == -1)) break;
								if (ChessPiece.getEnum(b[r-dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r-dt][c+dt]))){
										Cell toKill = new Cell(r-dt, c+dt);
										temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_tr = true;
								}
								else{
									Cell move = new Cell(r-dt, c+dt);
									temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}	
							dt = 1;
							boolean collision_br = false;
							while (!collision_br){
								if ((c+dt == 8) ||(r+dt == 8)) break;
								if (ChessPiece.getEnum(b[r+dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r+dt][c+dt]))){
										Cell toKill = new Cell(r+dt, c+dt);
										temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_br = true;
								}
								else{
									Cell move = new Cell(r+dt, c+dt);
									temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
					}
					if (thisPiece == ChessPiece.WHITE_QUEEN){
							//String now = "White castle @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row

							int dt = 1;
							//up
							boolean collision_up = false;
							while (!collision_up){
								if (r-dt == -1) break;
								if (ChessPiece.getEnum(b[r-dt][c]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r-dt][c]))){
										Cell toKill = new Cell(r-dt, c);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_up = true;
								}
								else{
									Cell move = new Cell(r-dt, c);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							dt = 1;
							//top-right
							boolean collision_tr = false;
							while(!collision_tr){
								if ((c+dt == 8) ||(r-dt == -1)) break;
								if (ChessPiece.getEnum(b[r-dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r-dt][c+dt]))){
										Cell toKill = new Cell(r-dt, c+dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_tr = true;
								}
								else{
									Cell move = new Cell(r-dt, c+dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}	
							//right
							dt = 1;
							boolean collision_right = false;
							while (!collision_right){
								if (c+dt == 8) break;
								if (ChessPiece.getEnum(b[r][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r][c+dt]))){
										Cell toKill = new Cell(r, c+dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_right = true;
								}else{
									Cell move = new Cell(r, c+dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//bottom-right
							dt = 1;
							boolean collision_br = false;
							while (!collision_br){
								if ((c+dt == 8) ||(r+dt == 8)) break;
								if (ChessPiece.getEnum(b[r+dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r+dt][c+dt]))){
										Cell toKill = new Cell(r+dt, c+dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_br = true;
								}
								else{
									Cell move = new Cell(r+dt, c+dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//down
							dt = 1;
							boolean collision_down = false;
							while(!collision_down){
								if (r+dt==8) break;
								if (ChessPiece.getEnum(b[r+dt][c]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r+dt][c]))){
										Cell toKill = new Cell(r+dt, c);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_down = true;
								}else{
									Cell move = new Cell(r+dt, c);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//bottom-left
							dt = 1;
							boolean collision_bl = false;
							while(!collision_bl){
								if ((c-dt == -1) || (r+dt == 8)) break;
								if (ChessPiece.getEnum(b[r+dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r+dt][c-dt]))){
										Cell toKill = new Cell(r+dt, c-dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_bl = true;
								}
								else{
									Cell move = new Cell(r+dt, c-dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//left
							dt = 1;
							boolean collision_left = false;
							while(!collision_left){
								if (c-dt == -1) break;
								if (ChessPiece.getEnum(b[r][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r][c-dt]))){
										Cell toKill = new Cell(r, c-dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_left = true;
								}else{
									Cell move = new Cell(r, c-dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//top-left
							dt = 1;
							boolean collision_tl = false;
							while (!collision_tl){
								if ((c-dt == -1) || (r-dt == -1)) break;
								if (ChessPiece.getEnum(b[r-dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(b[r-dt][c-dt]))){
										Cell toKill = new Cell(r-dt, c-dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_tl = true;
								}
								else{
									Cell move = new Cell(r-dt, c-dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
					}
					if (thisPiece == ChessPiece.WHITE_KING){
							//String now = "White Pawn @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row

							//up
							if (r-1 != -1){
								if (ChessPiece.getEnum(b[r-1][c]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r-1, c);
									temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r-1][c]) != ChessPiece.EMPTY && 
										isBlack(ChessPiece.getEnum(b[r-1][c])) &&
										ChessPiece.getEnum(b[r-1][c]) != ChessPiece.BLACK_KING){
											Cell toKill = new Cell(r-1, c);
											temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}
								
							//top-right
							if ((c+1 != 8) && (r-1 != -1)){
								if (ChessPiece.getEnum(b[r-1][c+1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r-1, c+1);
									temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r-1][c+1]) != ChessPiece.EMPTY && 
										isBlack(ChessPiece.getEnum(b[r-1][c+1])) &&
										ChessPiece.getEnum(b[r-1][c+1]) != ChessPiece.BLACK_KING){
											Cell toKill = new Cell(r-1, c+1);
											temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//right
							if (c+1 != 8){
								if (ChessPiece.getEnum(b[r][c+1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r, c+1);
									temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r][c+1]) != ChessPiece.EMPTY && 
										isBlack(ChessPiece.getEnum(b[r][c+1])) &&
										ChessPiece.getEnum(b[r][c+1]) != ChessPiece.BLACK_KING){
											Cell toKill = new Cell(r, c+1);
											temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//bottom-right
							if ((c+1 != 8) && (r+1 != 8)){
								if (ChessPiece.getEnum(b[r+1][c+1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r+1, c+1);
									temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r+1][c+1]) != ChessPiece.EMPTY && 
										isBlack(ChessPiece.getEnum(b[r+1][c+1])) &&
										ChessPiece.getEnum(b[r+1][c+1]) != ChessPiece.BLACK_KING){
											Cell toKill = new Cell(r+1, c+1);
											temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//bottom
							if (r+1 != 8){
								if (ChessPiece.getEnum(b[r+1][c]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r+1, c);
									temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r+1][c]) != ChessPiece.EMPTY && 
										isBlack(ChessPiece.getEnum(b[r+1][c])) &&
										ChessPiece.getEnum(b[r+1][c]) != ChessPiece.BLACK_KING){
											Cell toKill = new Cell(r+1, c);
											temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//bottom-left
							if ((c-1 != -1) && (r+1 != 8)){
								if (ChessPiece.getEnum(b[r+1][c-1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r+1, c-1);
									temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" column "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r+1][c-1]) != ChessPiece.EMPTY && 
										isBlack(ChessPiece.getEnum(b[r+1][c-1])) &&
										ChessPiece.getEnum(b[r+1][c-1]) != ChessPiece.BLACK_KING){
											Cell toKill = new Cell(r+1, c-1);
											temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//left
							if (c-1 != -1){
								if (ChessPiece.getEnum(b[r][c-1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r, c-1);
									temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r][c-1]) != ChessPiece.EMPTY && 
										isBlack(ChessPiece.getEnum(b[r][c-1])) &&
										ChessPiece.getEnum(b[r][c-1]) != ChessPiece.BLACK_KING){
											Cell toKill = new Cell(r, c-1);
											temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//top-left
							if ((c-1 != -1) && (r-1 != -1)){
								if (ChessPiece.getEnum(b[r-1][c-1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r-1, c-1);
									temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r-1][c-1]) != ChessPiece.EMPTY && 
										isBlack(ChessPiece.getEnum(b[r-1][c-1])) &&
										ChessPiece.getEnum(b[r-1][c-1]) != ChessPiece.BLACK_KING){
											Cell toKill = new Cell(r-1, c-1);
											temp.add(ChessPiece.WHITE_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}
					}
					if (thisPiece == ChessPiece.BLACK_PAWN){
							//String now = "White Pawn @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row
							//check in front
							if (ChessPiece.getEnum(b[r-1][c]) == ChessPiece.EMPTY){
								//System.out.println("w_p move to row "+(r-1)+" column "+c);
								Cell move = new Cell(r-1, c);
								temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
								move.getRow()+" c "+move.getCol());
							}

							//2 in front if pawn's first move
							if (r == 6){
								if (ChessPiece.getEnum(b[r-2][c]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r-2, c);
									temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
							}

							//check if left-edge pawn
							if (c == 0){
								//only check top-right-diag for kill option
								if ((ChessPiece.getEnum(b[r-1][c+1]) != ChessPiece.EMPTY) && isWhite(ChessPiece.getEnum(b[r-1][c+1]))){
									Cell toKill = new Cell(r-1, c+1);
									temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
									toKill.getRow()+" c "+toKill.getCol());
								}
							}
							//check if right-edge pawn
							else if (c == 7){
								//only check top-left-diag for kill option
								if ((ChessPiece.getEnum(b[r-1][c-1]) != ChessPiece.EMPTY) && isWhite(ChessPiece.getEnum(b[r-1][c-1]))){
									Cell toKill = new Cell(r-1, c-1);
									temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
									toKill.getRow()+" c "+toKill.getCol());
								}
							}
							else{
								//check top-left diagonal FOR KILL OPTION
								if ((ChessPiece.getEnum(b[r-1][c-1])) != ChessPiece.EMPTY && isWhite(ChessPiece.getEnum(b[r-1][c-1]))){
									Cell toKill = new Cell(r-1, c-1);
									temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
									toKill.getRow()+" c "+toKill.getCol());
								}
								
								//check top-right diagonal for KILL OPTION
								if ((ChessPiece.getEnum(b[r-1][c+1])) != ChessPiece.EMPTY && isWhite(ChessPiece.getEnum(b[r-1][c+1]))){
									Cell toKill = new Cell(r-1, c+1);
									temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
									toKill.getRow()+" c "+toKill.getCol());
								}
							}

							if (!black_turn){
								//compute opposite turn's moves

								//check in front
								if (r+1<=7){
									if (ChessPiece.getEnum(b[r+1][c]) == ChessPiece.EMPTY){
										//System.out.println("w_p move to row "+(r-1)+" column "+c);
										Cell move = new Cell(r+1, c);
										temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
									}	
								}
								
								//2 in front if pawn's first move
								
								if (r == 1 && (r+2<=7)){
									if (ChessPiece.getEnum(b[r+2][c]) == ChessPiece.EMPTY){
										//System.out.println("w_p move to row "+(r-1)+" column "+c);
										Cell move = new Cell(r+2, c);
										temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
									}
								}
								if (r+1<=7 && c-1>=0){	
									//check if right-edge pawn
									if (c == 7){
										//only check bottom-left-diag for kill option
										if ((ChessPiece.getEnum(b[r+1][c-1]) != ChessPiece.EMPTY) && isWhite(ChessPiece.getEnum(b[r+1][c-1]))){
											Cell toKill = new Cell(r+1, c-1);
											temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
									}
								}
								else{
									if((r+1>= 0 && r+1<=7) && (c+1>=0 && c+1<=7)){
										//check bottom-right diagonal FOR KILL OPTION
										if ((ChessPiece.getEnum(b[r+1][c+1])) != ChessPiece.EMPTY && isWhite(ChessPiece.getEnum(b[r+1][c+1]))){
											Cell toKill = new Cell(r+1, c+1);
											temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
									}
									
									if ((r+1>= 0 && r+1<=7) && (c-1>=0 && c-1<=7)) {
										//check top-right diagonal for KILL OPTION
										if ((ChessPiece.getEnum(b[r+1][c-1])) != ChessPiece.EMPTY && isWhite(ChessPiece.getEnum(b[r+1][c-1]))){
											Cell toKill = new Cell(r+1, c-1);
											temp.add(ChessPiece.BLACK_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
									} 	
								}
							}
					}
					if (thisPiece == ChessPiece.BLACK_CASTLE){
							//String now = "White castle @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row

							//check from left to right

							//check if located @ leftmost-column
							if (c==0){
								//look right
								int dt = 1;
								boolean collision_right = false;
								while (!collision_right){
									if (c+dt == 8) break;
									if (ChessPiece.getEnum(b[r][c+dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isWhite(ChessPiece.getEnum(b[r][c+dt]))){
											Cell toKill = new Cell(r, c+dt);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_right = true;
									}
									else 
									{
										Cell move = new Cell(r, c+dt);
										temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								//look up & down if possible
								//UP
								dt = 1;
								boolean collision_up = false;
								while (!collision_up){
									if (r-dt == -1) break;
									if (ChessPiece.getEnum(b[r-dt][c]) != ChessPiece.EMPTY){
										//check for kill option
										if (isWhite(ChessPiece.getEnum(b[r-dt][c]))){
											Cell toKill = new Cell(r-dt, c);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_up = true;
									}
									else
									{
										Cell move = new Cell(r-dt, c);
										temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								//DOWN
								dt = 1;
								boolean collision_down = false;
								while(!collision_down){
									if (r+dt==8) break;
									if (ChessPiece.getEnum(b[r+dt][c]) != ChessPiece.EMPTY){
										//check for kill option
										if (isWhite(ChessPiece.getEnum(b[r+dt][c]))){
											Cell toKill = new Cell(r+dt, c);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_down = true;
									}
									else
									{
										Cell move = new Cell(r+dt, c);
										temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
							}
							else if (c>=1 && c<7){
								int dt = 1;
								//LEFT
								boolean collision_left = false;
								while(!collision_left){
									if (c-dt == -1) break;
									if (ChessPiece.getEnum(b[r][c-dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isWhite(ChessPiece.getEnum(b[r][c-dt]))){
											Cell toKill = new Cell(r, c-dt);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_left = true;
									}
									else
									{
										Cell move = new Cell(r, c-dt);
										temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								dt=1;
								//RIGHT
								boolean collision_right = false;
								while (!collision_right){
									if (c+dt == 8) break;
									if (ChessPiece.getEnum(b[r][c+dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isWhite(ChessPiece.getEnum(b[r][c+dt]))){
											Cell toKill = new Cell(r, c+dt);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_right = true;
									}
									else 
									{
										Cell move = new Cell(r, c+dt);
										temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								dt = 1;
								//look up and down if possible
								//UP
								if (r != 0){
									boolean collision_up = false;
									while (!collision_up){
										if (r-dt == -1) break;
										if (ChessPiece.getEnum(b[r-dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isWhite(ChessPiece.getEnum(b[r-dt][c]))){
												Cell toKill = new Cell(r-dt, c);
												temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
												toKill.getRow()+" c "+toKill.getCol());
											}
											collision_up = true;
										}
										else
										{
											Cell move = new Cell(r-dt, c);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
											dt++;
										}
									}
								}
								dt=1;
								//DOWN
								if (r != 7){
									boolean collision_down = false;
									while(!collision_down){
										if (r+dt==8) break;
										if (ChessPiece.getEnum(b[r+dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isWhite(ChessPiece.getEnum(b[r+dt][c]))){
												Cell toKill = new Cell(r+dt, c);
												temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
												toKill.getRow()+" c "+toKill.getCol());
											}
											collision_down = true;
										}
										else
										{
											Cell move = new Cell(r+dt, c);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
											dt++;
										}
									}
								}
							}
							else if (c==7){
								int dt = 1;
								//LEFT
								boolean collision_left = false;
								while(!collision_left){
									if (c-dt == -1) break;
									if (ChessPiece.getEnum(b[r][c-dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isWhite(ChessPiece.getEnum(b[r][c-dt]))){
											Cell toKill = new Cell(r, c-dt);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
										}
										collision_left = true;
									}
									else
									{
										Cell move = new Cell(r, c-dt);
										temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
										dt++;
									}
								}
								dt=1;
								//UP
								if (r != 0){
									boolean collision_up = false;
									while (!collision_up){
										if (r-dt == -1) break;
										if (ChessPiece.getEnum(b[r-dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isWhite(ChessPiece.getEnum(b[r-dt][c]))){
												Cell toKill = new Cell(r-dt, c);
												temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
												toKill.getRow()+" c "+toKill.getCol());
											}
											collision_up = true;
										}
										else
										{
											Cell move = new Cell(r-dt, c);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
											dt++;
										}
									}
								}
								dt=1;
								//DOWN
								if (r != 7){
									boolean collision_down = false;
									while(!collision_down){
										if (r+dt==8) break;
										if (ChessPiece.getEnum(b[r+dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isWhite(ChessPiece.getEnum(b[r+dt][c]))){
												Cell toKill = new Cell(r+dt, c);
												temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
												toKill.getRow()+" c "+toKill.getCol());
											}
											collision_down = true;
										}
										else
										{
											Cell move = new Cell(r+dt, c);
											temp.add(ChessPiece.BLACK_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
											move.getRow()+" c "+move.getCol());
											dt++;
										}
									}
								}
							}
					}
					if (thisPiece == ChessPiece.BLACK_KNIGHT){
							//String now = "Black knight @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row
							
							final int two = 2;
							final int one = 1;

							////LEFT SIDE////

							////UP_2_LEFT_1////
							if ((r-two >= 0) && (c-one >= 0)){	
								if (ChessPiece.getEnum(b[r-two][c-one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r-two][c-one]))){
										Cell toKill = new Cell(r-two, c-one);
										temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-two, c-one);
									temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							/////////////////

							////UP_1_LEFT_2////
							if ((r-one >= 0) && (c-two >= 0)){	
								if (ChessPiece.getEnum(b[r-one][c-two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r-one][c-two]))){
										Cell toKill = new Cell(r-two, c-one);
										temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-one, c-two);
									temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							/////////////////

							////DOWN_1_LEFT_2////
							if ((r+one <= 7) && (c-two >= 0)){	
								if (ChessPiece.getEnum(b[r+one][c-two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r+one][c-two]))){
										Cell toKill = new Cell(r+one, c-two);
										temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+one, c-two);
									temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							/////////////////

							////DOWN_2_LEFT_1////
							if ((r+two <= 7) && (c-one >= 0)){	
								if (ChessPiece.getEnum(b[r+two][c-one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r+two][c-one]))){
										Cell toKill = new Cell(r+two, c-one);
										temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+two, c-one);
									temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							/////////////////

							////RIGHT SIDE/////

							////UP_2_RIGHT_1////
							if ((r-two >= 0) && (c+one <= 7)){	
								if (ChessPiece.getEnum(b[r-two][c+one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r-two][c+one]))){
										Cell toKill = new Cell(r-two, c+one);
										temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-two, c+one);
									temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							////////////////////

							////UP_1_RIGHT_2////
							if ((r-one >= 0) && (c+two <= 7)){	
								if (ChessPiece.getEnum(b[r-one][c+two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r-one][c+two]))){
										Cell toKill = new Cell(r-one, c+two);
										temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-one, c+two);
									temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							////////////////////

							////DOWN_1_RIGHT_2////
							if ((r+one <= 7) && (c+two <= 7)){	
								if (ChessPiece.getEnum(b[r+one][c+two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r+one][c+two]))){
										Cell toKill = new Cell(r+one, c+two);
										temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+one, c+two);
									temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							////////////////////

							////DOWN_2_RIGHT_1////
							if ((r+two <= 7) && (c+one <= 7)){	
								if (ChessPiece.getEnum(b[r+two][c+one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r+two][c+one]))){
										Cell toKill = new Cell(r+two, c+one);
										temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+two, c+one);
									temp.add(ChessPiece.BLACK_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
								}
							}
							////////////////////
					}
					if (thisPiece == ChessPiece.BLACK_BISHOP){
							//String now = "Black bishop @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row

							int dt = 1;
							boolean collision_tl = false;
							while (!collision_tl){
								if ((c-dt == -1) || (r-dt == -1)) break;
								if (ChessPiece.getEnum(b[r-dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r-dt][c-dt]))){
										Cell toKill = new Cell(r-dt, c-dt);
										temp.add(ChessPiece.BLACK_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_tl = true;
								}
								else{
									Cell move = new Cell(r-dt, c-dt);
									temp.add(ChessPiece.BLACK_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							dt = 1;
							boolean collision_bl = false;
							while(!collision_bl){
								if ((c-dt == -1) || (r+dt == 8)) break;
								if (ChessPiece.getEnum(b[r+dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r+dt][c-dt]))){
										Cell toKill = new Cell(r+dt, c-dt);
										temp.add(ChessPiece.BLACK_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_bl = true;
								}
								else{
									Cell move = new Cell(r+dt, c-dt);
									temp.add(ChessPiece.BLACK_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							dt = 1;
							boolean collision_tr = false;
							while(!collision_tr){
								if ((c+dt == 8) ||(r-dt == -1)) break;
								if (ChessPiece.getEnum(b[r-dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r-dt][c+dt]))){
										Cell toKill = new Cell(r-dt, c+dt);
										temp.add(ChessPiece.BLACK_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_tr = true;
								}
								else{
									Cell move = new Cell(r-dt, c+dt);
									temp.add(ChessPiece.BLACK_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}	
							dt = 1;
							boolean collision_br = false;
							while (!collision_br){
								if ((c+dt == 8) ||(r+dt == 8)) break;
								if (ChessPiece.getEnum(b[r+dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r+dt][c+dt]))){
										Cell toKill = new Cell(r+dt, c+dt);
										temp.add(ChessPiece.BLACK_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_br = true;
								}
								else{
									Cell move = new Cell(r+dt, c+dt);
									temp.add(ChessPiece.BLACK_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
					}
					if (thisPiece == ChessPiece.BLACK_QUEEN){
							//String now = "White queen @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row

							int dt = 1;
							//up
							boolean collision_up = false;
							while (!collision_up){
								if (r-dt == -1) break;
								if (ChessPiece.getEnum(b[r-dt][c]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r-dt][c]))){
										Cell toKill = new Cell(r-dt, c);
										temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_up = true;
								}
								else{
									Cell move = new Cell(r-dt, c);
									temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							dt = 1;
							//top-right
							boolean collision_tr = false;
							while(!collision_tr){
								if ((c+dt == 8) ||(r-dt == -1)) break;
								if (ChessPiece.getEnum(b[r-dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r-dt][c+dt]))){
										Cell toKill = new Cell(r-dt, c+dt);
										temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_tr = true;
								}
								else{
									Cell move = new Cell(r-dt, c+dt);
									temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}	
							//right
							dt = 1;
							boolean collision_right = false;
							while (!collision_right){
								if (c+dt == 8) break;
								if (ChessPiece.getEnum(b[r][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r][c+dt]))){
										Cell toKill = new Cell(r, c+dt);
										temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_right = true;
								}else{
									Cell move = new Cell(r, c+dt);
									temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//bottom-right
							dt = 1;
							boolean collision_br = false;
							while (!collision_br){
								if ((c+dt == 8) ||(r+dt == 8)) break;
								if (ChessPiece.getEnum(b[r+dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r+dt][c+dt]))){
										Cell toKill = new Cell(r+dt, c+dt);
										temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_br = true;
								}
								else{
									Cell move = new Cell(r+dt, c+dt);
									temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//down
							dt = 1;
							boolean collision_down = false;
							while(!collision_down){
								if (r+dt==8) break;
								if (ChessPiece.getEnum(b[r+dt][c]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r+dt][c]))){
										Cell toKill = new Cell(r+dt, c);
										temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_down = true;
								}else{
									Cell move = new Cell(r+dt, c);
									temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//bottom-left
							dt = 1;
							boolean collision_bl = false;
							while(!collision_bl){
								if ((c-dt == -1) || (r+dt == 8)) break;
								if (ChessPiece.getEnum(b[r+dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r+dt][c-dt]))){
										Cell toKill = new Cell(r+dt, c-dt);
										temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_bl = true;
								}
								else{
									Cell move = new Cell(r+dt, c-dt);
									temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//left
							dt = 1;
							boolean collision_left = false;
							while(!collision_left){
								if (c-dt == -1) break;
								if (ChessPiece.getEnum(b[r][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r][c-dt]))){
										Cell toKill = new Cell(r, c-dt);
										temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_left = true;
								}else{
									Cell move = new Cell(r, c-dt);
									temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
							//top-left
							dt = 1;
							boolean collision_tl = false;
							while (!collision_tl){
								if ((c-dt == -1) || (r-dt == -1)) break;
								if (ChessPiece.getEnum(b[r-dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isWhite(ChessPiece.getEnum(b[r-dt][c-dt]))){
										Cell toKill = new Cell(r-dt, c-dt);
										temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
										toKill.getRow()+" c "+toKill.getCol());
									}
									collision_tl = true;
								}
								else{
									Cell move = new Cell(r-dt, c-dt);
									temp.add(ChessPiece.BLACK_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
									move.getRow()+" c "+move.getCol());
									dt++;
								}
							}
					}
					if (thisPiece == ChessPiece.BLACK_KING){
							//String now = "Black King @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row

							//up
							if (r-1 != -1){
								if (ChessPiece.getEnum(b[r-1][c]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r-1, c);
									temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r-1][c]) != ChessPiece.EMPTY && 
										isWhite(ChessPiece.getEnum(b[r-1][c])) &&
										ChessPiece.getEnum(b[r-1][c]) != ChessPiece.WHITE_KING){
											Cell toKill = new Cell(r-1, c);
											temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}
								
							//top-right
							if ((c+1 != 8) && (r-1 != -1)){
								if (ChessPiece.getEnum(b[r-1][c+1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r-1, c+1);
									temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r-1][c+1]) != ChessPiece.EMPTY && 
										isWhite(ChessPiece.getEnum(b[r-1][c+1])) &&
										ChessPiece.getEnum(b[r-1][c+1]) != ChessPiece.WHITE_KING){
											Cell toKill = new Cell(r-1, c+1);
											temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//right
							if (c+1 != 8){
								if (ChessPiece.getEnum(b[r][c+1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r, c+1);
									temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r][c+1]) != ChessPiece.EMPTY && 
										isWhite(ChessPiece.getEnum(b[r][c+1])) &&
										ChessPiece.getEnum(b[r][c+1]) != ChessPiece.WHITE_KING){
											Cell toKill = new Cell(r, c+1);
											temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//bottom-right
							if ((c+1 != 8) && (r+1 != 8)){
								if (ChessPiece.getEnum(b[r+1][c+1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r+1, c+1);
									temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r+1][c+1]) != ChessPiece.EMPTY && 
										isWhite(ChessPiece.getEnum(b[r+1][c+1])) &&
										ChessPiece.getEnum(b[r+1][c+1]) != ChessPiece.WHITE_KING){
											Cell toKill = new Cell(r+1, c+1);
											temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//bottom
							if (r+1 != 8){
								if (ChessPiece.getEnum(b[r+1][c]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r+1, c);
									temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r+1][c]) != ChessPiece.EMPTY && 
										isWhite(ChessPiece.getEnum(b[r+1][c])) &&
										ChessPiece.getEnum(b[r+1][c]) != ChessPiece.WHITE_KING){
											Cell toKill = new Cell(r+1, c);
											temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//bottom-left
							if ((c-1 != -1) && (r+1 != 8)){
								if (ChessPiece.getEnum(b[r+1][c-1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r+1, c-1);
									temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" column "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r+1][c-1]) != ChessPiece.EMPTY && 
										isWhite(ChessPiece.getEnum(b[r+1][c-1])) &&
										ChessPiece.getEnum(b[r+1][c-1]) != ChessPiece.WHITE_KING){
											Cell toKill = new Cell(r+1, c-1);
											temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//left
							if (c-1 != -1){
								if (ChessPiece.getEnum(b[r][c-1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r, c-1);
									temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r][c-1]) != ChessPiece.EMPTY && 
										isWhite(ChessPiece.getEnum(b[r][c-1])) &&
										ChessPiece.getEnum(b[r][c-1]) != ChessPiece.WHITE_KING){
											Cell toKill = new Cell(r, c-1);
											temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}

							//top-left
							if ((c-1 != -1) && (r-1 != -1)){
								if (ChessPiece.getEnum(b[r-1][c-1]) == ChessPiece.EMPTY){
									//System.out.println("w_p move to row "+(r-1)+" column "+c);
									Cell move = new Cell(r-1, c-1);
									temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" goto r "+
										move.getRow()+" c "+move.getCol());
								}
								else if (ChessPiece.getEnum(b[r-1][c-1]) != ChessPiece.EMPTY && 
										isWhite(ChessPiece.getEnum(b[r-1][c-1])) &&
										ChessPiece.getEnum(b[r-1][c-1]) != ChessPiece.WHITE_KING){
											Cell toKill = new Cell(r-1, c-1);
											temp.add(ChessPiece.BLACK_KING.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill r "+
											toKill.getRow()+" c "+toKill.getCol());
								}
							}
					}
					i++;
				}
			}
			return temp;
		}

		private static ChessPiece getEnum(String str) {
			for (ChessPiece t: ChessPiece.values()){
				if (str.equals(t.getStr())) return t;
			}
			return null;
		}

		private static boolean isWhite(ChessPiece c){
			switch(c){
				case WHITE_CASTLE: case WHITE_PAWN: case WHITE_KING:
				case WHITE_QUEEN: case WHITE_BISHOP: case WHITE_KNIGHT:{ return true; }
				default:{return false;}
			}
		}

		private static boolean isBlack(ChessPiece c){
			switch(c){
				case BLACK_CASTLE: case BLACK_PAWN: case BLACK_KING:
				case BLACK_QUEEN: case BLACK_BISHOP: case BLACK_KNIGHT:{ return true; }
				default:{return false;}
			}
		}

		private static String convertToCoords(int i){
			String temp="";
			
			if (i==0) temp="a8";else if (i==1) temp="b8";else if (i==2) temp="c8";else if (i==3) temp="d8";
			else if (i==4) temp="e8";else if (i==5) temp="f8";else if (i==6) temp="g8";else if (i==7) temp="h8";

			else if (i==8) temp="a7";else if (i==9) temp="b7";else if (i==10) temp="c7";else if (i==11) temp="d7";
			else if (i==12) temp="e7";else if (i==13) temp="f7";else if (i==14) temp="g7";else if (i==15) temp="h7";

			else if (i==16) temp="a6";else if (i==17) temp="b6";else if (i==18) temp="c6";else if (i==19) temp="d6";
			else if (i==20) temp="e6";else if (i==21) temp="f6";else if (i==22) temp="g6";else if (i==23) temp="h6";

			else if (i==24) temp="a5";else if (i==25) temp="b5";else if (i==26) temp="c5";else if (i==27) temp="d5";
			else if (i==28) temp="e5";else if (i==29) temp="f5";else if (i==30) temp="g5";else if (i==31) temp="h5";

			else if (i==32) temp="a4";else if (i==33) temp="b4";else if (i==34) temp="c4";else if (i==35) temp="d4";
			else if (i==36) temp="e4";else if (i==37) temp="f4";else if (i==38) temp="g4";else if (i==39) temp="h4";

			else if (i==40) temp="a3";else if (i==41) temp="b3";else if (i==42) temp="c3";else if (i==43) temp="d3";
			else if (i==44) temp="e3";else if (i==45) temp="f3";else if (i==46) temp="g3";else if (i==47) temp="h3";

			else if (i==48) temp="a2";else if (i==49) temp="b2";else if (i==50) temp="c2";else if (i==51) temp="d2";
			else if (i==52) temp="e2";else if (i==53) temp="f2";else if (i==54) temp="g2";else if (i==55) temp="h2";

			else if (i==56) temp="a1";else if (i==57) temp="b1";else if (i==58) temp="c1";else if (i==59) temp="d1";
			else if (i==60) temp="e1";else if (i==61) temp="f1";else if (i==62) temp="g1";else if (i==63) temp="h1";
			return temp;
		}

		private static int toGridFormat(char c){
			int base =0;
			if (c =='8' || c == 'a') base = 0;
			if (c =='7' || c == 'b') base = 1;
			if (c =='6' || c == 'c') base = 2;
			if (c =='5' || c == 'd') base = 3;
			if (c =='4' || c == 'e') base = 4;
			if (c =='3' || c == 'f') base = 5;
			if (c =='2' || c == 'g') base = 6;
			if (c =='1' || c == 'h') base = 7;
			return base;
		}
	}

	private static void printMat(String[][] x)
	{	
		System.out.println("--------------------------------------------------------------------------");
		for (String[] row : x)
		{
			System.out.print("|\t");
		    for (String value : row)
		    {
		    	if (value == null) System.out.println("null\t");
		    	if (value.equals("-")) System.out.print("-\t");
		    	else System.out.print(value+"\t");
		    }
		    System.out.print("|\n\n");
		}
		System.out.println("--------------------------------------------------------------------------");
	}

	private static void flipMat(String[][] x)
	{
		String[][] newMat = new String[8][8];
		int oj = 0;
		int oi = 0;
		for (int j=7; j>-1; j--){
			for (int i=7; i>-1; i--){
				if (oi == 8) oi = 0;
				newMat[oj][oi] = board[j][i];
				oi++;
			}
			if (oj == 8) oj = 0;
			else oj++;
		}
		board = newMat;
	}

	private static String currentPlayer(int c){
		if (c%2 == 0) return "Black's Turn";
		else return "White's Turn";
	}

	private static void prepareForOpDisplay(){
		Scanner c = new Scanner(System.in);
		System.out.print("Type row number (0-7) "); int pr = c.nextInt();
		System.out.print("Type column number (0-7) "); int pc = c.nextInt();
		Cell selected_piece = new Cell(pr, pc);
		//is the piece selected owned by the player w/ current turn?
		while (!ownedByCurrentPlayer(selected_piece)){
			System.out.println("That's not your's!");
			printMat(board);
			System.out.print("Type row number (0-7) "); pr = c.nextInt();
			System.out.print("Type column number (0-7) "); pc = c.nextInt();
			selected_piece = new Cell(pr, pc);
		}
		String weirdStr = toWeirdForm(selected_piece);

		//match possible moves from arraylist of possible moves to this piece
		ArrayList<String> p = ChessPiece.possibleMoves(board);

		//System.out.println("BEFORE");
		for (String e : p ){ System.out.println(e); }
		
		if (check) { 
			p = edit(p);
			//System.out.println("AFTER");
			for (String a : p ){ System.out.println(a); } 
		}
		String[][] moveCopy = deepCopy(board);
		for (String g : p){
			String[] move_props = g.split(" ");
			if (move_props[2].equals(weirdStr)){ 
				//System.out.println("Were the same!\n"+g);
				int thisRow = Integer.parseInt(move_props[5]);
				int thisCol = Integer.parseInt(move_props[7]);
				if (move_props[3].equals("kill")) moveCopy[thisRow][thisCol] = "Trgt";
				else moveCopy[thisRow][thisCol] = "X";
			}
		}
		printMat(moveCopy);
	}

	public static ArrayList<String> edit(ArrayList<String> w){ 
		ArrayList<String> temp = new ArrayList<String>();
		for (String ah: w){
			//System.out.println(ah);
			String[] ahp = ah.split(" ");
			String srcstr = ahp[2]; char ch_srcco = srcstr.charAt(0); char ch_srcro = srcstr.charAt(1);
			int srcco = ChessPiece.toGridFormat(ch_srcco); int srcro = ChessPiece.toGridFormat(ch_srcro);
			//System.out.println("src row is "+srcro+" and src col is"+srcco);
			int dest_row = Integer.parseInt(ahp[5]); int dest_col = Integer.parseInt(ahp[7]);
			//System.out.println("dest row is "+dest_row+" and dest col is "+dest_col+"--------------------");
			String[][] myCopy = deepCopy(board);
			myCopy[dest_row][dest_col] = myCopy[srcro][srcco]; myCopy[srcro][srcco] = "-";
			//printMat(myCopy);
			if (!willLeaveUsInCheck(myCopy)) temp.add(ah); 
		}
		return temp;
	}

	public static boolean willLeaveUsInCheck(String[][] b){
		ArrayList<String> myArrList = ChessPiece.possibleMoves(b);//all moves generated for current move
		//System.out.println("\nMoves for this move");
		for (String c: myArrList){
			String[] cprop = c.split(" "); int row = Integer.parseInt(cprop[5]); int col = Integer.parseInt(cprop[7]);
			String pStr = b[row][col]; ChessPiece piece = ChessPiece.getEnum(pStr); 
			if (currentPlayer(cp).equals("White's Turn")){
				if (piece == ChessPiece.WHITE_KING) {
					//System.out.println("My king @ destination!");
					return true;
				}
			}
			if (currentPlayer(cp).equals("Black's Turn")){
				if (piece == ChessPiece.BLACK_KING) return true;
			}
		}

		return false;
	}

/*
	have a list of moves
	have a deep copy of the board

	for each move, perform it on a DC
	generate new arrlist of moves for move from 1 step above
	if any cells from this arrlist contain player's king as destination, remove this move

*/

	public static String[][] deepCopy(String[][] x) {
		String[][] temp = new String[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j=0; j < 8; j++) {
				temp[i][j] = board[i][j];
			}
		}
		return temp;
	}

	public static void setTile(String[][] x, int r, int c, String val) { x[r][c] = val; }
	public static String getTile(int r, int c, String[][] x) { return x[r][c]; }

	private static String toWeirdForm(Cell p){
		int p_row = p.getRow(); int p_col = p.getCol();
		String temp = "";
		//System.out.println("ROW IS "+p_row+" AND COL IS "+p_col);
		if (p_col == 0) temp+="a"; if (p_col == 1) temp+="b"; if (p_col == 2) temp+="c"; if (p_col == 3) temp+="d";
		if (p_col == 4) temp+="e"; if (p_col == 5) temp+="f"; if (p_col == 6) temp+="g"; if (p_col == 7) temp+="h";

		if (p_row == 7) temp+="1"; if (p_row == 6) temp+="2"; if (p_row == 5) temp+="3"; if (p_row == 4) temp+="4";
		if (p_row == 3) temp+="5"; if (p_row == 2) temp+="6"; if (p_row == 1) temp+="7"; if (p_row == 0) temp+="8";
		return temp;
	}

	private static boolean ownedByCurrentPlayer(Cell b){
		String curplay = currentPlayer(cp);
		String mine = board[b.getRow()][b.getCol()];
		ChessPiece m1 = ChessPiece.getEnum(mine); 
		if (m1 == ChessPiece.EMPTY){
			System.out.println("That's empty..");
			return false;
		}
		if (curplay.equals("White's Turn") && ChessPiece.isWhite(m1)) return true;
		else if (curplay.equals("Black's Turn") && ChessPiece.isBlack(m1)) return true;
		return false;
	}

	private static void prepareToMovePiece(){
		Scanner c = new Scanner(System.in);
		System.out.print("Type source row number (0-7) "); int pr = c.nextInt();
		System.out.print("Type source column number (0-7) "); int pc = c.nextInt();
		Cell selected_piece = new Cell(pr, pc);
		//is the piece selected owned by the player w/ current turn?
		while(!valid(selected_piece)){
			System.out.println("Yeah, I don't know what you're referring to..");
			printMat(board);
			System.out.print("Type source row number (0-7) "); pr = c.nextInt();
			System.out.print("Type source column number (0-7) "); pc = c.nextInt();
			selected_piece = new Cell(pr, pc);
		}
		while (!ownedByCurrentPlayer(selected_piece)){
			System.out.println("That's not your's!");
			printMat(board);
			System.out.print("Type source row number (0-7) "); pr = c.nextInt();
			System.out.print("Type source column number (0-7) "); pc = c.nextInt();
			selected_piece = new Cell(pr, pc);
		}
		while (!hasMoves(selected_piece)){
			System.out.println("The ChessPiece you selected has no options.");
			printMat(board);
			System.out.print("Type source row number (0-7) "); pr = c.nextInt();
			System.out.print("Type source column number (0-7) "); pc = c.nextInt();
			selected_piece = new Cell(pr, pc);
		}
		String weirdStr = toWeirdForm(selected_piece);
		
		ArrayList<Cell> thisPiecesMoves = new ArrayList<Cell>();
		ArrayList<String> p = ChessPiece.possibleMoves(board);
		if (check) p = edit(p);
		String[][] moveCopy = deepCopy(board);
		for (String g : p){
			//System.out.println("->"+g);
			String[] move_props = g.split(" ");
			if (move_props[2].equals(weirdStr)){ 
				//System.out.println("Were the same!\n"+g);
				int thisRow = Integer.parseInt(move_props[5]);
				int thisCol = Integer.parseInt(move_props[7]);
				Cell thisCell = new Cell(thisRow,thisCol);
				if (move_props[3].equals("kill")) moveCopy[thisRow][thisCol] = "Trgt"; 
				else moveCopy[thisRow][thisCol] = "X";
				thisPiecesMoves.add(thisCell);
			}
		}
		printMat(moveCopy);

		//prompt for destination
		System.out.print("Type destination row number (0-7) "); int dpr = c.nextInt();
		System.out.print("Type destination column number (0-7) "); int dpc = c.nextInt();
		Cell selected_dest_piece = new Cell(dpr, dpc);

		while(!valid(selected_dest_piece)){
			System.out.println("Yeah, I don't know what you're referring to..");
			printMat(moveCopy);
			System.out.print("Type destination row number (0-7) "); dpr = c.nextInt();
			System.out.print("Type destination column number (0-7) "); dpc = c.nextInt();
			selected_dest_piece = new Cell(dpr, dpc);
		}
		while (!possiblePieceSelection(selected_dest_piece, thisPiecesMoves)){
			System.out.println("Yeah, clearly that's not one of your options");
			printMat(moveCopy);
			System.out.print("Type destination row number (0-7) "); dpr = c.nextInt();
			System.out.print("Type destination column number (0-7) "); dpc = c.nextInt();
			selected_dest_piece = new Cell(dpr, dpc);	
		}
		
		board[dpr][dpc] = board[pr][pc];
		board[pr][pc] = "-";
		if (!willLeaveUsInCheck(board)) check = false;

		ArrayList<String> post_move_ops = ChessPiece.possibleMoves(board);
		for (String x: post_move_ops){
			String[] x_prop = x.split(" ");
			if (x_prop[3].equals("kill")){
				int pkrow = Integer.parseInt(x_prop[5]); int pkcol = Integer.parseInt(x_prop[7]);
				String token = board[pkrow][pkcol]; ChessPiece pk = ChessPiece.getEnum(token);
				if (currentPlayer(cp).equals("White's Turn") && pk == ChessPiece.BLACK_KING) check = true;
				else if (currentPlayer(cp).equals("Black's Turn") && pk == ChessPiece.WHITE_KING) check = true;
			}
		}

		printMat(board);
	}

	private static boolean hasMoves(Cell x){
		ArrayList<String> p = ChessPiece.possibleMoves(board);
		for (String g: p){
			String[] move_props = g.split(" ");
			if (move_props[2].equals(toWeirdForm(x))) return true;
		}
		return false;
	}

	private static boolean valid(Cell x){
		if (x.getRow()<0 || x.getRow()>7) return false;
		if (x.getCol()<0 || x.getCol()>7) return false;
		return true;
	} 

	private static boolean possiblePieceSelection(Cell x, ArrayList<Cell> alc){
		for (Cell y: alc){
			//System.out.println("r->"+x.getRow()+"\tc->"+x.getCol());
			int y_row = y.getRow(); int y_col = y.getCol();
			if (y_row == x.getRow() && y_col == x.getCol()) return true;
		}
		return false;
	}

	/*private static void startGame(){
		Scanner s = new Scanner(System.in);
		while(!mate){
			if (check) {
				if (sizeOfCurrentPlayerMoveList() == 0){
					mate = true;
					System.out.println("CHECK MATE!!");
					break;
				}
				else System.out.println("CHECK!!!");
			}
			System.out.println(currentPlayer(cp));
			printMat(board);
			
			System.out.println("Type 'so' to see options or 'mp' to move piece");
			String inpt = s.nextLine();
			while ((!inpt.equals("so")) && (!inpt.equals("mp"))){//keep waiting for proper input
				System.out.println("Not an option, friend. Type 'so' or 'mp'");
				inpt = s.nextLine();
			}
			if (inpt.equals("so")){
				while(inpt.equals("so")){
					prepareForOpDisplay();
					System.out.println("Type 'so' to see options or 'mp' to move piece");
					inpt = s.nextLine();
					while((!inpt.equals("so")) && (!inpt.equals("mp"))){
						System.out.println("Not an option, friend. Type 'so' to see options or 'mp' to move piece");
						inpt = s.nextLine();
					}
				}
			}
			if (inpt.equals("mp")){ prepareToMovePiece(); }
			cp++;
			flipMat(board);
		}
	}*/

	private static void waitForValInput(){
		Scanner s = new Scanner(System.in);
		System.out.println("Usage: Type\t'so' to see options\t'mp' to move piece."); String inpt = s.nextLine();
		while ((!inpt.equals("so")) && (!inpt.equals("mp"))){//keep waiting for proper input
			System.out.println("Not an option, friend. Type 'so' or 'mp'");
			inpt = s.nextLine();
		}
			if (inpt.equals("so")){
				while(inpt.equals("so")){
					prepareForOpDisplay();
					System.out.println("Type 'so' to see options or 'mp' to move piece");
					inpt = s.nextLine();
					while((!inpt.equals("so")) && (!inpt.equals("mp"))){
						System.out.println("Not an option, friend. Type 'so' to see options or 'mp' to move piece");
						inpt = s.nextLine();
					}
				}
			}
			if (inpt.equals("mp")){ prepareToMovePiece(); }
	}

	private static boolean gameOver(){
		ArrayList<String> moovz = ChessPiece.possibleMoves(board);
		if (check) moovz = edit(moovz);
		/*if (moovz.size() == 0) {
			System.out.println("CHECK MATE!");
			String my_cp = currentPlayer(cp).substring(0,5); System.out.println(my_cp+ " won this match.");
			return true;
		}*/
		int numOfCurrentPlayersMoves = 0;
		for (String r : moovz){
			String[] rprops = r.split(" "); String piece = rprops[0]; //System.out.println("->"+piece+"\t"+currentPlayer(cp)+" check?="+check);
			if (currentPlayer(cp).equals("White's Turn")){
				if (isWhiteStr(piece)) numOfCurrentPlayersMoves++;
			}
			if (currentPlayer(cp).equals("Black's Turn")){
				if (isBlackStr(piece)) numOfCurrentPlayersMoves++;
			}
		}
		if (numOfCurrentPlayersMoves == 0) return true;
		if (check) System.out.println("CHECK!!!"); 
		return false;
	}

	private static boolean isWhiteStr(String s){
		return s.equals("WHITE_CASTLE") || s.equals("WHITE_KNIGHT") 
			|| s.equals("WHITE_BISHOP") || s.equals("WHITE_QUEEN") 
			|| s.equals("WHITE_KING") || s.equals("WHITE_PAWN") ;
	}

	private static boolean isBlackStr(String s){
		return s.equals("BLACK_CASTLE") || s.equals("BLACK_KNIGHT") 
			|| s.equals("BLACK_BISHOP") || s.equals("BLACK_QUEEN") 
			|| s.equals("BLACK_KING") || s.equals("BLACK_PAWN") ;
	}

	private static void startGame(){
		boolean keepPlaying = true;
		while (keepPlaying){
			System.out.println(currentPlayer(cp));
			printMat(board);
			if(gameOver()) {
				keepPlaying = false;
				if (currentPlayer(cp).equals("White's Turn")) System.out.println("Black won!");
				if (currentPlayer(cp).equals("Black's Turn")) System.out.println("White won!");
				break;
			}
			else {
				waitForValInput();
				cp++;
				flipMat(board);
			}
			
		}
		System.out.println("Goodbye");
	}

	public static void main(String[] args){ CG m = new CG(); }
}