import java.util.Properties;
import java.util.ArrayList;
public class CG{
	public String[][] board;

	public CG(){
		board = new String[][]{
			{"c","n","b","q","k","b","n","c"},
			{"p","p","p","p","p","p","p","p"},
			{"-","-","-","-","-","-","-","-"},
			{"-","-","-","-","-","-","-","-"},
			{"-","-","-","-","-","-","-","-"},
			{"-","-","-","-","-","-","-","-"},
			{"p","p","p","p","p","p","p","p"},
			{"c","n","b","q","k","b","n","c"}
		};
		printMat(board);
		//startGame();
	}

	private enum ChessPiece{
		//possible pieces (or empty) tiles
		CASTLE("c"), KNIGHT("n"), BISHOP("b"), QUEEN("q"), KING("k"), PAWN("p"), EMPTY("-"); 
	
		private String str;
		private ChessPiece(String s){this.str = s;}
		//String representation of enum type
		private String getStr(){return str;}

		private ArrayList<Properties> possibleMoves(){
			ArrayList<Properties> temp = new ArrayList<Properties>();

			
			
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
	}

	public static void main(String[] args){ CG m = new CG(); }
}