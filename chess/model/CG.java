import java.util.Properties;
import java.util.ArrayList;
public class CG{
	public static String[][] board;
	public static int cp;//will be modded by 2 at each iteration
	public ArrayList<ChessPiece> offBoard;

	public CG(){
		cp = 1;
		board = new String[][]{
			/*{"b_c","b_n","b_b","b_k","b_q","b_b","b_n","b_c"},
			{"b_p","b_p","b_p","b_p","b_p","b_p","b_p","b_p"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"w_p","w_p","w_p","w_p","w_p","w_p","w_p","w_p"},
			{"w_c","w_n","w_b","w_k","w_q","w_b","w_n","w_c"}*/

			{"-",  "-",  "-",  "b_k",  "b_q",  "b_b","b_n",  "b_c"},
			{"-",  "-",  "-",  "b_p",  "b_p",  "b_p","b_p",  "b_p"},
			{"-"  ,"-"  ,  "-"  ,  "-"  ,  "-"  ,  "-"  ,"-"  ,  "-"  },
			{"-",  "-"  ,  "-"  ,  "-"  ,  "-"  ,  "-"  ,"-"  ,  "-"  },
			{"-"  ,"-"  ,  "-"  ,  "-"  ,  "w_q"  ,  "-"  ,"-"  ,  "-"  },
			{"-"  ,"-",   "-"  ,  "-"  ,  "-"  ,  "-"  ,"-"  ,  "-"  },
			{"-",  "-"  ,  "-",    "-"  ,  "-"  ,  "-"  ,"-"  ,  "-"  },
			{"-",  "-"  ,  "-",  "-"  ,  "-"  ,  "-"  ,"-"  ,  "-"  }
		};
		offBoard = new ArrayList<ChessPiece>();
		//printMat(board);
		System.out.println("Welcome to LSChess! Let's play!");
		startGame();
	}

	private enum ChessPiece{
		//possible pieces (or empty) tiles
		WHITE_CASTLE("w_c"), WHITE_KNIGHT("w_n"), WHITE_BISHOP("w_b"), WHITE_QUEEN("w_q"), WHITE_KING("w_k"), WHITE_PAWN("w_p"),
		BLACK_CASTLE("b_c"), BLACK_KNIGHT("b_n"), BLACK_BISHOP("b_b"), BLACK_QUEEN("b_q"), BLACK_KING("b_k"), BLACK_PAWN("b_p"), 
		EMPTY("-"); 
	
		private String str;
		private ChessPiece(String s){this.str = s;}
		//String representation of enum type
		private String getStr(){return str;}

		private static ArrayList<String> possibleMoves(){
			ArrayList<String> temp = new ArrayList<String>();

			boolean white_turn = false;
			boolean black_turn = false;

			if (cp%2 == 0) black_turn = true;
			else white_turn = true;

			int i = 0;
			for (String[] row : board){
				for (String x : row){
					ChessPiece thisPiece = ChessPiece.getEnum(x);
					if (white_turn){
						if (!isWhite(thisPiece));//CAN'T LEAVE LIKE THIS
						if (thisPiece == ChessPiece.WHITE_PAWN){
							//String now = "White Pawn @ "+ChessPiece.convertToCoords(i);
							char c_char = ChessPiece.convertToCoords(i).charAt(0);
							char r_char = ChessPiece.convertToCoords(i).charAt(1);
							int c = toGridFormat(c_char);//current piece's column
							int r = toGridFormat(r_char);//current piece's row
							//check in front
							if (ChessPiece.getEnum(board[r-1][c]) == ChessPiece.EMPTY){
								//System.out.println("w_p move to row "+(r-1)+" column "+c);
								Cell move = new Cell(r-1, c);
								temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
							}
							//check if left-edge pawn
							if (c == 0){
								//only check top-right-diag for kill option
								if ((ChessPiece.getEnum(board[r-1][c+1]) != ChessPiece.EMPTY) && isBlack(ChessPiece.getEnum(board[r-1][c+1]))){
									Cell toKill = new Cell(r-1, c+1);
									temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
									toKill.getRow()+" column "+toKill.getCol());
								}
							}
							//check if right-edge pawn
							else if (c == 7){
								//only check top-left-diag for kill option
								if ((ChessPiece.getEnum(board[r-1][c-1]) != ChessPiece.EMPTY) && isBlack(ChessPiece.getEnum(board[r-1][c-1]))){
									Cell toKill = new Cell(r-1, c-1);
									temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
									toKill.getRow()+" column "+toKill.getCol());
								}
							}
							else{
								//check top-left diagonal FOR KILL OPTION
								if ((ChessPiece.getEnum(board[r-1][c-1])) != ChessPiece.EMPTY && isBlack(ChessPiece.getEnum(board[r-1][c-1]))){
									Cell toKill = new Cell(r-1, c-1);
									temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
									toKill.getRow()+" column "+toKill.getCol());
								}
								
								//check top-right diagonal for KILL OPTION
								if ((ChessPiece.getEnum(board[r-1][c+1])) != ChessPiece.EMPTY && isBlack(ChessPiece.getEnum(board[r-1][c+1]))){
									Cell toKill = new Cell(r-1, c+1);
									temp.add(ChessPiece.WHITE_PAWN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
									toKill.getRow()+" column "+toKill.getCol());
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
									if (ChessPiece.getEnum(board[r][c+dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(board[r][c+dt]))){
											Cell toKill = new Cell(r, c+dt);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
											toKill.getRow()+" column "+toKill.getCol());
										}
										collision_right = true;
									}
									else 
									{
										Cell move = new Cell(r, c+dt);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
										move.getRow()+" column "+move.getCol());
										dt++;
									}
								}
								//look up & down if possible
								//UP
								dt = 1;
								boolean collision_up = false;
								while (!collision_up){
									if (r-dt == -1) break;
									if (ChessPiece.getEnum(board[r-dt][c]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(board[r-dt][c]))){
											Cell toKill = new Cell(r-dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
											toKill.getRow()+" column "+toKill.getCol());
										}
										collision_up = true;
									}
									else
									{
										Cell move = new Cell(r-dt, c);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
										move.getRow()+" column "+move.getCol());
										dt++;
									}
								}
								//DOWN
								dt = 1;
								boolean collision_down = false;
								while(!collision_down){
									if (r+dt==8) break;
									if (ChessPiece.getEnum(board[r+dt][c]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(board[r+dt][c]))){
											Cell toKill = new Cell(r+dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
											toKill.getRow()+" column "+toKill.getCol());
										}
										collision_down = true;
									}
									else
									{
										Cell move = new Cell(r+dt, c);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
										move.getRow()+" column "+move.getCol());
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
									if (ChessPiece.getEnum(board[r][c-dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(board[r][c-dt]))){
											Cell toKill = new Cell(r, c-dt);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
											toKill.getRow()+" column "+toKill.getCol());
										}
										collision_left = true;
									}
									else
									{
										Cell move = new Cell(r, c-dt);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
										move.getRow()+" column "+move.getCol());
										dt++;
									}
								}
								dt=1;
								//RIGHT
								boolean collision_right = false;
								while (!collision_right){
									if (c+dt == 8) break;
									if (ChessPiece.getEnum(board[r][c+dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(board[r][c+dt]))){
											Cell toKill = new Cell(r, c+dt);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
											toKill.getRow()+" column "+toKill.getCol());
										}
										collision_right = true;
									}
									else 
									{
										Cell move = new Cell(r, c+dt);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
										move.getRow()+" column "+move.getCol());
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
										if (ChessPiece.getEnum(board[r-dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isBlack(ChessPiece.getEnum(board[r-dt][c]))){
												Cell toKill = new Cell(r-dt, c);
												temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
												toKill.getRow()+" column "+toKill.getCol());
											}
											collision_up = true;
										}
										else
										{
											Cell move = new Cell(r-dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
											move.getRow()+" column "+move.getCol());
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
										if (ChessPiece.getEnum(board[r+dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isBlack(ChessPiece.getEnum(board[r+dt][c]))){
												Cell toKill = new Cell(r+dt, c);
												temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
												toKill.getRow()+" column "+toKill.getCol());
											}
											collision_down = true;
										}
										else
										{
											Cell move = new Cell(r+dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
											move.getRow()+" column "+move.getCol());
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
									if (ChessPiece.getEnum(board[r][c-dt]) != ChessPiece.EMPTY){
										//check for kill option
										if (isBlack(ChessPiece.getEnum(board[r][c-dt]))){
											Cell toKill = new Cell(r, c-dt);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
											toKill.getRow()+" column "+toKill.getCol());
										}
										collision_left = true;
									}
									else
									{
										Cell move = new Cell(r, c-dt);
										temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
										move.getRow()+" column "+move.getCol());
										dt++;
									}
								}
								dt=1;
								//UP
								if (r != 0){
									boolean collision_up = false;
									while (!collision_up){
										if (r-dt == -1) break;
										if (ChessPiece.getEnum(board[r-dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isBlack(ChessPiece.getEnum(board[r-dt][c]))){
												Cell toKill = new Cell(r-dt, c);
												temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
												toKill.getRow()+" column "+toKill.getCol());
											}
											collision_up = true;
										}
										else
										{
											Cell move = new Cell(r-dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
											move.getRow()+" column "+move.getCol());
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
										if (ChessPiece.getEnum(board[r+dt][c]) != ChessPiece.EMPTY){
											//check for kill option
											if (isBlack(ChessPiece.getEnum(board[r+dt][c]))){
												Cell toKill = new Cell(r+dt, c);
												temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
												toKill.getRow()+" column "+toKill.getCol());
											}
											collision_down = true;
										}
										else
										{
											Cell move = new Cell(r+dt, c);
											temp.add(ChessPiece.WHITE_CASTLE.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
											move.getRow()+" column "+move.getCol());
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
								if (ChessPiece.getEnum(board[r-two][c-one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r-two][c-one]))){
										Cell toKill = new Cell(r-two, c-one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-two, c-one);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
								}
							}
							/////////////////

							////UP_1_LEFT_2////
							if ((r-one >= 0) && (c-two >= 0)){	
								if (ChessPiece.getEnum(board[r-one][c-two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r-one][c-two]))){
										Cell toKill = new Cell(r-two, c-one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-one, c-two);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
								}
							}
							/////////////////

							////DOWN_1_LEFT_2////
							if ((r+one <= 7) && (c-two >= 0)){	
								if (ChessPiece.getEnum(board[r+one][c-two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r+one][c-two]))){
										Cell toKill = new Cell(r+one, c-two);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+one, c-two);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
								}
							}
							/////////////////

							////DOWN_2_LEFT_1////
							if ((r+two <= 7) && (c-one >= 0)){	
								if (ChessPiece.getEnum(board[r+two][c-one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r+two][c-one]))){
										Cell toKill = new Cell(r+two, c-one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+two, c-one);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
								}
							}
							/////////////////

							////RIGHT SIDE/////

							////UP_2_RIGHT_1////
							if ((r-two >= 0) && (c+one <= 7)){	
								if (ChessPiece.getEnum(board[r-two][c+one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r-two][c+one]))){
										Cell toKill = new Cell(r-two, c+one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-two, c+one);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
								}
							}
							////////////////////

							////UP_1_RIGHT_2////
							if ((r-one >= 0) && (c+two <= 7)){	
								if (ChessPiece.getEnum(board[r-one][c+two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r-one][c+two]))){
										Cell toKill = new Cell(r-one, c+two);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r-one, c+two);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
								}
							}
							////////////////////

							////DOWN_1_RIGHT_2////
							if ((r+one <= 7) && (c+two <= 7)){	
								if (ChessPiece.getEnum(board[r+one][c+two]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r+one][c+two]))){
										Cell toKill = new Cell(r+one, c+two);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+one, c+two);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
								}
							}
							////////////////////

							////DOWN_2_RIGHT_1////
							if ((r+two <= 7) && (c+one <= 7)){	
								if (ChessPiece.getEnum(board[r+two][c+one]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r+two][c+one]))){
										Cell toKill = new Cell(r+two, c+one);
										temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
								}else{
									Cell move = new Cell(r+two, c+one);
									temp.add(ChessPiece.WHITE_KNIGHT.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
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
								if (ChessPiece.getEnum(board[r-dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r-dt][c-dt]))){
										Cell toKill = new Cell(r-dt, c-dt);
										temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_tl = true;
								}
								else{
									Cell move = new Cell(r-dt, c-dt);
									temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}
							dt = 1;
							boolean collision_bl = false;
							while(!collision_bl){
								if ((c-dt == -1) || (r+dt == 8)) break;
								if (ChessPiece.getEnum(board[r+dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r+dt][c-dt]))){
										Cell toKill = new Cell(r+dt, c-dt);
										temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_bl = true;
								}
								else{
									Cell move = new Cell(r+dt, c-dt);
									temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}
							dt = 1;
							boolean collision_tr = false;
							while(!collision_tr){
								if ((c+dt == 8) ||(r-dt == -1)) break;
								if (ChessPiece.getEnum(board[r-dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r-dt][c+dt]))){
										Cell toKill = new Cell(r-dt, c+dt);
										temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_tr = true;
								}
								else{
									Cell move = new Cell(r-dt, c+dt);
									temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}	
							dt = 1;
							boolean collision_br = false;
							while (!collision_br){
								if ((c+dt == 8) ||(r+dt == 8)) break;
								if (ChessPiece.getEnum(board[r+dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r+dt][c+dt]))){
										Cell toKill = new Cell(r+dt, c+dt);
										temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_br = true;
								}
								else{
									Cell move = new Cell(r+dt, c+dt);
									temp.add(ChessPiece.WHITE_BISHOP.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
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
								if (ChessPiece.getEnum(board[r-dt][c]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r-dt][c]))){
										Cell toKill = new Cell(r-dt, c);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_up = true;
								}
								else{
									Cell move = new Cell(r-dt, c);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}
							dt = 1;
							//top-right
							boolean collision_tr = false;
							while(!collision_tr){
								if ((c+dt == 8) ||(r-dt == -1)) break;
								if (ChessPiece.getEnum(board[r-dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r-dt][c+dt]))){
										Cell toKill = new Cell(r-dt, c+dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_tr = true;
								}
								else{
									Cell move = new Cell(r-dt, c+dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}	
							//right
							dt = 1;
							boolean collision_right = false;
							while (!collision_right){
								if (c+dt == 8) break;
								if (ChessPiece.getEnum(board[r][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r][c+dt]))){
										Cell toKill = new Cell(r, c+dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_right = true;
								}else{
									Cell move = new Cell(r, c+dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}
							//bottom-right
							dt = 1;
							boolean collision_br = false;
							while (!collision_br){
								if ((c+dt == 8) ||(r+dt == 8)) break;
								if (ChessPiece.getEnum(board[r+dt][c+dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r+dt][c+dt]))){
										Cell toKill = new Cell(r+dt, c+dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_br = true;
								}
								else{
									Cell move = new Cell(r+dt, c+dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}
							//down
							dt = 1;
							boolean collision_down = false;
							while(!collision_down){
								if (r+dt==8) break;
								if (ChessPiece.getEnum(board[r+dt][c]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r+dt][c]))){
										Cell toKill = new Cell(r+dt, c);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_down = true;
								}else{
									Cell move = new Cell(r+dt, c);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}
							//bottom-left
							dt = 1;
							boolean collision_bl = false;
							while(!collision_bl){
								if ((c-dt == -1) || (r+dt == 8)) break;
								if (ChessPiece.getEnum(board[r+dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r+dt][c-dt]))){
										Cell toKill = new Cell(r+dt, c-dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_bl = true;
								}
								else{
									Cell move = new Cell(r+dt, c-dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}
							//left
							dt = 1;
							boolean collision_left = false;
							while(!collision_left){
								if (c-dt == -1) break;
								if (ChessPiece.getEnum(board[r][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r][c-dt]))){
										Cell toKill = new Cell(r, c-dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_left = true;
								}else{
									Cell move = new Cell(r, c-dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}
							//top-left
							dt = 1;
							boolean collision_tl = false;
							while (!collision_tl){
								if ((c-dt == -1) || (r-dt == -1)) break;
								if (ChessPiece.getEnum(board[r-dt][c-dt]) != ChessPiece.EMPTY){
									//check for kill option
									if (isBlack(ChessPiece.getEnum(board[r-dt][c-dt]))){
										Cell toKill = new Cell(r-dt, c-dt);
										temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" kill at row "+
										toKill.getRow()+" column "+toKill.getCol());
									}
									collision_tl = true;
								}
								else{
									Cell move = new Cell(r-dt, c-dt);
									temp.add(ChessPiece.WHITE_QUEEN.toString()+" @ "+ ChessPiece.convertToCoords(i) +" move to row "+
									move.getRow()+" column "+move.getCol());
									dt++;
								}
							}
						}
						if (thisPiece == ChessPiece.WHITE_KING){
							String now = "I'm a white king";
							temp.add(now);
						}
					}
					if (black_turn){
						if (!isBlack(thisPiece));//CAN'T LEAVE LIKE THIS
						if (thisPiece == ChessPiece.BLACK_PAWN){
							String now = "I'm a black pawn";
							temp.add(now);
						}
						if (thisPiece == ChessPiece.BLACK_CASTLE){
							String now = "I'm a black castle";
							temp.add(now);
						}
						if (thisPiece == ChessPiece.BLACK_KNIGHT){
							String now = "I'm a black knight";
							temp.add(now);
						}
						if (thisPiece == ChessPiece.BLACK_BISHOP){
							String now = "I'm a black bishop";
							temp.add(now);
						}
						if (thisPiece == ChessPiece.BLACK_QUEEN){
							String now = "I'm a black queen";
							temp.add(now);
						}
						if (thisPiece == ChessPiece.BLACK_KING){
							String now = "I'm a black king";
							temp.add(now);
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
			
			if (i==0) temp="a8";
			else if (i==1) temp="b8";
			else if (i==2) temp="c8";
			else if (i==3) temp="d8";
			else if (i==4) temp="e8";
			else if (i==5) temp="f8";
			else if (i==6) temp="g8";
			else if (i==7) temp="h8";

			else if (i==8) temp="a7";
			else if (i==9) temp="b7";
			else if (i==10) temp="c7";
			else if (i==11) temp="d7";
			else if (i==12) temp="e7";
			else if (i==13) temp="f7";
			else if (i==14) temp="g7";
			else if (i==15) temp="h7";

			else if (i==16) temp="a6";
			else if (i==17) temp="b6";
			else if (i==18) temp="c6";
			else if (i==19) temp="d6";
			else if (i==20) temp="e6";
			else if (i==21) temp="f6";
			else if (i==22) temp="g6";
			else if (i==23) temp="h6";

			else if (i==24) temp="a5";
			else if (i==25) temp="b5";
			else if (i==26) temp="c5";
			else if (i==27) temp="d5";
			else if (i==28) temp="e5";
			else if (i==29) temp="f5";
			else if (i==30) temp="g5";
			else if (i==31) temp="h5";

			else if (i==32) temp="a4";
			else if (i==33) temp="b4";
			else if (i==34) temp="c4";
			else if (i==35) temp="d4";
			else if (i==36) temp="e4";
			else if (i==37) temp="f4";
			else if (i==38) temp="g4";
			else if (i==39) temp="h4";

			else if (i==40) temp="a3";
			else if (i==41) temp="b3";
			else if (i==42) temp="c3";
			else if (i==43) temp="d3";
			else if (i==44) temp="e3";
			else if (i==45) temp="f3";
			else if (i==46) temp="g3";
			else if (i==47) temp="h3";

			else if (i==48) temp="a2";
			else if (i==49) temp="b2";
			else if (i==50) temp="c2";
			else if (i==51) temp="d2";
			else if (i==52) temp="e2";
			else if (i==53) temp="f2";
			else if (i==54) temp="g2";
			else if (i==55) temp="h2";

			else if (i==56) temp="a1";
			else if (i==57) temp="b1";
			else if (i==58) temp="c1";
			else if (i==59) temp="d1";
			else if (i==60) temp="e1";
			else if (i==61) temp="f1";
			else if (i==62) temp="g1";
			else if (i==63) temp="h1";
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
		int i = 8;
		System.out.println("--------a-------b-------c-------d-------e-------f-------g-------h---------");
		System.out.println("--------------------------------------------------------------------------");
		for (String[] row : x)
		{
			System.out.print(i+"|\t");
		    for (String value : row)
		    {
		    	if (value.equals("-")) System.out.print("-\t");
		    	else System.out.print(value+"\t");
		    }
		    System.out.print("|"+i+"\n");
		    i--;
		}
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("--------a-------b-------c-------d-------e-------f-------g-------h---------\n");
	}

	private static String currentPlayer(int c){
		if (c%2 == 0) return "Black's Turn";
		else return "White's Turn";
	}

	private static void startGame(){
		boolean play = true;
		while(play){
			System.out.println(currentPlayer(cp));
			printMat(board);
			ArrayList<String> p = ChessPiece.possibleMoves();
			for (String g : p){System.out.println(g);}
			cp++;
			play = false;
		}
		//ArrayList<Properties> p = ChessPiece.possibleMoves();
		//System.out.println(currentPlayer(cp));
	}

	public static void main(String[] args){ CG m = new CG(); }
}