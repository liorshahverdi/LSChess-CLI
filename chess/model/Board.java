public class Board{
	private int rows, columns;
	private Tile[][] grid;
	
	public Board(int r, int c){
		rows = r;
		columns = c;
		grid = new Tile[rows][columns];
		//grid[0][0] = new Tile();
		//printBoard();
	}

	public Tile[][] getBoard() { return grid; }

	public void printBoard(){
		for (Tile[] row : grid)
		{
			System.out.print("|\t");
		    for (Tile x : row)
		    {
		    	if (x == null) System.out.print("-\t");
		    	else System.out.print(x.getVal().toString()+"\t");
		    }
		    System.out.print("|\n");
		}
	}
}