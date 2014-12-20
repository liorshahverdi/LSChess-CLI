public class CGMove{
	private Cell src, dest;
	private boolean isKill;
	private ChessPiece c;

	public CGMove(ChessPiece t, Cell s, Cell d, boolean ik){
		this.c = t;
		this.src = s;
		this.dest = d;
		this.isKill = ik;
	}

	public Cell getSrc() { return src; }

	public Cell getDest() { return dest; }

	public boolean getIsKill(){
		if (isKill) return true;
		else return false;
	}
}