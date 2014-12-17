import java.util.Properties;
import java.util.ArrayList;
public class CG{
	public static String[][] board;
	public static int cp;//will be modded by 2 at each iteration
	public ArrayList<ChessPiece> offBoard;

	public CG(){
		cp = 1;
		board = new String[][]{
			{"w_c","w_n","w_b","w_k","w_q","w_b","w_n","w_c"},
			{"w_p","w_p","w_p","w_p","w_p","w_p","w_p","w_p"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"-",  "-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"  ,"-"},
			{"b_p","b_p","b_p","b_p","b_p","b_p","b_p","b_p"},
			{"b_c","b_n","b_b","b_k","b_q","b_b","b_n","b_c"}
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

		private static ArrayList<Properties> possibleMoves(){
			ArrayList<Properties> temp = new ArrayList<Properties>();

			int num_whites = 0;
			int num_blacks = 0;
			for (String[] row : board){
				for (String x : row){
					ChessPiece thisPiece = ChessPiece.getEnum(x);
					if (isWhite(thisPiece)) num_whites++;
					if (isBlack(thisPiece)) num_blacks++;
				}
			}

			System.out.println("b="+num_blacks+"\tw="+num_whites);

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
	}

	private static void printMat(String[][] x)
	{
		System.out.println("-------------------------------------------------------------------------");
		for (String[] row : x)
		{
			System.out.print("|\t");
		    for (String value : row)
		    {
		    	if (value.equals("-")) System.out.print("-\t");
		    	else System.out.print(value+"\t");
		    }
		    System.out.print("|\n");
		}
		System.out.println("-------------------------------------------------------------------------");
	}

	private static void startGame(){
		boolean play = true;
		while(play){
			System.out.println(currentPlayer(cp));
			printMat(board);
			ArrayList<Properties> p = ChessPiece.possibleMoves();
			cp++;
			play = false;
		}
		//ArrayList<Properties> p = ChessPiece.possibleMoves();
		//System.out.println(currentPlayer(cp));
	}

	private static String currentPlayer(int c){
		if (c%2 == 0) return "Black's Turn";
		else return "White's Turn";
	}

	public static void main(String[] args){ CG m = new CG(); }
}