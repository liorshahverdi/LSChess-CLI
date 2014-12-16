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
	}

	private enum ChessPiece{
		//possible pieces (or empty) tiles
		CASTLE("c"), KNIGHT("n"), BISHOP("b"), QUEEN("q"), KING("k"), PAWN("p"), EMPTY("-"); 
	
		private String str;

		private ChessPiece(String s){
			this.str = s;
		}

		//String representation of enum type
		private String getStr(){
    		return str;
		}
	}

	public static void printMat(String[][] x)
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

	public static void main(String[] args){
		CG m = new CG();
	}
}