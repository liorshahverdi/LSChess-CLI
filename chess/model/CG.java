public class CG{
	public char[][] board;

	public CG(){
		board = new char[8][8];
		initialize();
		printBoard(board);
	}

	public void initialize(){
		board[0][0]='c'; board[0][1]='n'; board[0][2]='b'; board[0][3]='q'; 
		board[0][4]='k'; board[0][5]='b'; board[0][6]='n'; board[0][7]='c';

		board[1][0]='p'; board[1][1]='p'; board[1][2]='p'; board[1][3]='p'; 
		board[1][4]='p'; board[1][5]='p'; board[1][6]='p'; board[1][7]='p';
	}

	public void printBoard(char[][] b){
		System.out.println("-------------------------------------------------------------------------");
		for (char[] row : board)
		{
			System.out.print("|\t");
		    for (char x : row)
		    {
		    	if (x == ' ') System.out.print("-\t");
		    	else System.out.print(x+"\t");
		    }
		    System.out.print("|\n");
		}
		System.out.println("-------------------------------------------------------------------------");
	}

	public static void main(String[] args){
		CG mine = new CG();
	}
}