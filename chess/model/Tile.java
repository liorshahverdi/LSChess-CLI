import java.util.*;
public class Tile{
	private Vector tileVector;
	private boolean isOccupied;

	public Tile(){ 
		tileVector = new Vector();
		isOccupied = false; 
	}

	public Vector getVal() { return tileVector; }
}