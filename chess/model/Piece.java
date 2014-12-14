public class Piece{
	private int board_location;
	private String type;

	public Piece(String t){
		this.type = t;
		board_location = -1;//not on board initally
	}

	public String toString(){ return "board_location = "+board_location+"\ttype = "+type; }
}