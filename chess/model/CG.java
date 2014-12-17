import java.util.Properties;
import java.util.ArrayList;
public class CG{
	public static String[][] board;

	public CG(){
		board = new String[][]{
			{"w_c","w_n","w_b","w_k","w_q","w_b","w_n","w_c"},
			{"w_p","w_p","w_p","w_p","w_p","w_p","w_p","w_p"},
			{"-","-","-","-","-","-","-","-"},
			{"-","-","-","-","-","-","-","-"},
			{"-","-","-","-","-","-","-","-"},
			{"-","-","-","-","-","-","-","-"},
			{"b_p","b_p","b_p","b_p","b_p","b_p","b_p","b_p"},
			{"b_c","b_n","b_b","b_k","b_q","b_b","b_n","b_c"}
		};
		//printMat(board);
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

			int num_empty_tiles = 0;

			for (String[] row : board){
				for (String x : row){
					ChessPiece thisPiece = ChessPiece.getEnum(x);
					System.out.println("enum="+thisPiece);
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
		boolean play = false;
		while(play){}

		ArrayList<Properties> p = ChessPiece.possibleMoves();
	}

	public static void main(String[] args){ CG m = new CG(); }
}