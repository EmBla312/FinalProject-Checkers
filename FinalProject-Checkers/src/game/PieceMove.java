package game;

public class PieceMove {

	private int fromRow, fromCol;  // Position of piece to be moved.
	private int toRow, toCol;      // Square it is to move to.
	
	public int getFromRow() {
		return fromRow;
	}
	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}
	public int getFromCol() {
		return fromCol;
	}
	public void setFromCol(int fromCol) {
		this.fromCol = fromCol;
	}
	public int getToRow() {
		return toRow;
	}
	public void setToRow(int toRow) {
		this.toRow = toRow;
	}
	public int getToCol() {
		return toCol;
	}
	public void setToCol(int toCol) {
		this.toCol = toCol;
	}
	PieceMove(int fromRow, int fromCol, int toRow, int toCol) {
		// Constructor.  Just set the values of the instance variables.
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
		
	}
	boolean isJump() {
		// Test whether this move is a jump.  It is assumed that
		// the move is legal.  In a jump, the piece moves two
		// rows.  (In a regular move, it only moves one row.)
		return (fromRow - toRow == 2 || fromRow - toRow == -2);
	}
}
