package game;
import java.util.LinkedList;
import java.util.Scanner;

public class GameData {
	/**
	 * The int value of an empty tile.
	 */
	private static final int EMPTY = 0;
	/**
	 * The int value of a tile containing a red pawn.
	 */
	public static final int R_PAWN = 1;
	/**
	 * The int value of a tile containing a white pawn.
	 */
	public static final int W_PAWN = 3;
	/**
	 * The int value of a tile containing a red king.
	 */
	private static final int R_KING = 2;
	/**
	 * The int value of a tile containing a white king.
	 */
	private static final int W_KING = 4;
	/**
	 * An 8 by 8 array to represent the checker board.
	 */
	private int[][] board; 

	/**
	 * Creates the board array and calls setUpBoard to place piece values.
	 */
	public GameData() {

		board = new int[8][8];
		setUpBoard();
	}

	/**
	 * Places pieces in the appropriate tiles for the beginning of the game.
	 */
	public void setUpBoard() {

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if ( row % 2 != col % 2 ) {
					if (row < 3)
						board[row][col] = R_PAWN;
					else if (row > 4)
						board[row][col] = W_PAWN;
					else
						board[row][col] = EMPTY;
				}
				else {
					board[row][col] = EMPTY;
				}
			}
		}
	}//end setUpBoard()

	/**
	 * Determines whether the game is over.
	 * @param team The player whose legal moves need to be evaluated.
	 * @return true if the given player can make no legal moves.
	 */
	public boolean isGameOver(int team) {
		PieceMove[] legalMoves = getLegalMoves(team);
		return legalMoves == null;
	}
	//	/**
	//	 * 
	//	 * @param row
	//	 * @param col
	//	 * @return
	//	 */
	//	public int pieceAt(int row, int col) {
	//		
	//		return board[row][col];		
	//	}
	/**
	 * Sets the element at the coordinates to the piece variable.
	 * @param row This is the row index of the array space.
	 * @param col This is the column index of the array space.
	 * @param piece This is the value to set as the element at the given indices.
	 */
	public void setPieceAt(int row, int col, int piece) {

		board[row][col] = piece;
	}

	/**
	 * Determines whether an element is a white king. 
	 * @param fromRow This is the row index of the tile to be checked.
	 * @param fromCol This is the col index of the tile to be checked.
	 * @return True if the given indices contain a white king.
	 */
	public boolean isKing(int fromRow, int fromCol) {
		return board[fromRow][fromCol] == W_KING;
	}

	/**
	 * Gets indices from a PieceMove object and passes them to the private makeMove method.
	 * @param move This is the PieceMove object that contains the coordinates.
	 */
	public void makeMove(PieceMove move) {

		makeMove(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
	}

	/**
	 * Checks whether move is jump or regular move. Moves the piece int from the current tile to the new tile and sets the current tile to 0 for empty.
	 * @param fromRow This is the row index of the tile the piece starts at.
	 * @param fromCol This is the col index of the tile the piece starts at.
	 * @param toRow This is the row index of the tile the piece will be moved to.
	 * @param toCol This is the col index of the tile the piece will be moved to.
	 */
	private void makeMove(int fromRow, int fromCol, int toRow, int toCol) {

		board[toRow][toCol] = board[fromRow][fromCol];
		board[fromRow][fromCol] = EMPTY;

		if(fromRow - toRow == 2 || fromRow - toRow == -2) {	//if jump remove middle piece
			int middleRow = (fromRow + toRow) / 2;
			int middleCol = (fromCol + toCol) / 2;
			board[middleRow][middleCol] = EMPTY;
		}

		if (toRow == 0 && board[toRow][toCol] == W_PAWN) 
			board[toRow][toCol] = W_KING;					//if reach backline, Promote piece
		if (toRow == 7 && board[toRow][toCol] == R_PAWN)
			board[toRow][toCol] = R_KING;
	}
	//team represented by the corresponding Pawn IE R_PAWN = 1
	/**
	 * Gets a list of all possible moves from the player's current position.
	 * @param team Represents the player whose moves are being calculated.
	 * @return an array of length 0 if linked list of moves is empty; else returns an array of all possible PieceMove objects.
	 */
	public PieceMove[] getLegalMoves(int team) {
		//the moves linkedlist can then be passed into a stack entry by entry.

		LinkedList<PieceMove> moves = new LinkedList<>(); //all moves stored here 

		//Checks if any piece on a team can jump 
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {		//team + 1 are that team's kings
				if(board[row][col] == team || board[row][col] == (team + 1)) {

					if (canJump(team, row, col, row+1, col+1, row+2, col+2))						
						moves.add(new PieceMove(row, col, row+2, col+2));

					if (canJump(team, row, col, row-1, col+1, row-2, col+2))
						moves.add(new PieceMove(row, col, row-2, col+2));

					if (canJump(team, row, col, row+1, col-1, row+2, col-2))
						moves.add(new PieceMove(row, col, row+2, col-2));

					if (canJump(team, row, col, row-1, col-1, row-2, col-2))
						moves.add(new PieceMove(row, col, row-2, col-2));
				}
				//Need point values given to each of these PieceMoves so we can do an evaluation of which Move to do
				//method: evalBestJump() ???????????
			}
		}


		//if there is a jump, must jump first. If no jumps, then check for all moves

		if(moves.isEmpty()) {
			for(int row = 0; row < board.length; row++) {
				for(int col = 0; col < board[0].length; col++) {		//team + 1 are that team's kings
					if (board[row][col] == team || board[row][col] == (team + 1)) {

						if (canMove(team,row,col,row+1,col+1))
							moves.add(new PieceMove(row,col,row+1,col+1));	

						if (canMove(team,row,col,row-1,col+1))
							moves.add(new PieceMove(row,col,row-1,col+1));

						if (canMove(team,row,col,row+1,col-1))
							moves.add(new PieceMove(row,col,row+1,col-1));

						if (canMove(team,row,col,row-1,col-1))
							moves.add(new PieceMove(row,col,row-1,col-1));
					}
				}
			}
		}

		//if no moves it'll return null, else copy all moves into an array and return it.
		if(moves.isEmpty()) {
			return null;
		}
		//return null;

		else { 
			PieceMove[] moveArray = new PieceMove[moves.size()];
			for(int i = 0; i < moves.size(); i++)
				moveArray[i] = moves.get(i);

			return moveArray;
		}
	} // end getLegalMoves()

	/**
	 * Gets all legal jumps for the player's piece at the given indices. Used for double-jumping.
	 * @param team Represents the player whose jumps are being calculated.
	 * @param row This is the Row index of the tile that contains the piece.
	 * @param col This is the column index of the tile that contains the piece.
	 * @return an array of all possible jumps for that single piece.
	 */
	public PieceMove[] getLegalJumpsFrom(int team, int row, int col) {


		LinkedList<PieceMove> moves = new LinkedList<>();  

		if (board[row][col] == team || board[row][col] == (team + 1)) {

			if (canJump(team, row, col, row+1, col+1, row+2, col+2))
				moves.add(new PieceMove(row, col, row+2, col+2));

			if (canJump(team, row, col, row-1, col+1, row-2, col+2))
				moves.add(new PieceMove(row, col, row-2, col+2));

			if (canJump(team, row, col, row+1, col-1, row+2, col-2))
				moves.add(new PieceMove(row, col, row+2, col-2));

			if (canJump(team, row, col, row-1, col-1, row-2, col-2))
				moves.add(new PieceMove(row, col, row-2, col-2));
		}

		if (moves.isEmpty()) {

			return null;
		}
		else {

			PieceMove[] moveArray = new PieceMove[moves.size()];
			for(int i = 0; i < moves.size(); i++)
				moveArray[i] = moves.get(i);

			return moveArray;
		}
	}	//end legalJumps

	/**
	 * Tests whether a piece can jump to the given tile.
	 * @param team Represents the player whose piece is jumping.
	 * @param fromRow This is the row index of the tile the piece starts at.
	 * @param fromCol This is the column index of the tile the piece starts at.
	 * @param middleRow This is the row index of the tile that should hold an opponent's piece to jump.
	 * @param middleCol This is the column index of the tile that should hold an opponent's piece to jump.
	 * @param toRow This is the row index of th tile immediately after the middle tile.
	 * @param toCol This is the col index of th tile immediately after the middle tile.
	 * @return true if (middleRow, middleCol) contains opponent's piece and (toRow, toCol) is empty.
	 */
	private boolean canJump(int team, int fromRow, int fromCol, int middleRow, int middleCol, int toRow, int toCol) {


		if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8)
			return false;  // (toRow,toCol) is off the board.

		if (board[toRow][toCol] != EMPTY)
			return false;  // (toRow,toCol) already contains a piece.

		if (team == W_PAWN) {
			if (board[fromRow][fromCol] == W_PAWN && toRow > fromRow)
				return false;  // Regular W_PAWN piece can only move  up.
			if (board[middleRow][middleCol] != R_PAWN && board[middleRow][middleCol] != R_KING)
				return false;  // There is no R_PAWN piece to jump.
			return true;  // The jump is legal.
		}
		else {
			if (board[fromRow][fromCol] == R_PAWN && toRow < fromRow)
				return false;  // Regular R_PAWN piece can only move down.
			if (board[middleRow][middleCol] != W_PAWN && board[middleRow][middleCol] != W_KING)
				return false;  // There is no W_PAWN piece to jump.
			return true;  // The jump is legal.
		}

	}  // end canJump()

	/**
	 * Called by the getLegalMoves method to test whether the player can legally move from (fromRow,fromCol) to (toRow,toCol).
	 * It is assumed that (fromRow,toRow) contains one of the team's pieces and that (toRow,toCol) is a neighboring tile.
	 * @param team Represents the player whose piece is moving.
	 * @param fromRow This is the row index of the tile the piece starts at.
	 * @param fromCol This is the column index of the tile the piece starts at.
	 * @param toRow This is the row index of the tile to check for validity.
	 * @param toCol This is the column index of the tile to check for validity.
	 * @return true if (toRow, toCol) is within array bounds, empty, and in the correct direction for the given team.
	 */
	private boolean canMove(int team, int fromRow, int fromCol, int toRow, int toCol) {
		// This is called by the getLegalMoves() method to determine whether
		// the team can legally move from (fromRow,fromCol) to (toRow,toCol).  It is
		// assumed that (fromRow,toRow) contains one of the team's pieces and
		// that (toRow,toCol) is a neighboring square.

		if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8)
			return false;  // (toRow,toCol) is off the board.

		if (board[toRow][toCol] != EMPTY)
			return false;  // (toRow,toCol) already contains a piece.

		if (team == W_PAWN) {
			if (board[fromRow][fromCol] == W_PAWN && toRow > fromRow)
				return false;  // Regular white piece can only move down.

			return true;  // The move is legal.
		}
		else {
			if (board[fromRow][fromCol] == R_PAWN && toRow < fromRow)
				return false;  // Regular red piece can only move up.
			return true;  // The move is legal.
		}

	}  // end canMove()

	/**
	 * Evaluates each PieceMove in an array of PieceMoves and gives it an integer weight.
	 * @param legalMoves This is the array of PieceMoves to be evaluated.
	 * @param position This is the index of the PieceMove object to be evaluated.
	 * @return the weight of of the PieceMove based on whether it's a jump or a regular move, and whether the piece is a king.
	 */
	public int evaluateMove(PieceMove[] legalMoves, int position) {
		int weight = 0;

		int row = legalMoves[position].getFromRow();
		int col = legalMoves[position].getFromCol();

		if(legalMoves[position].isJump())
			weight += 10;
		else
			weight += 5;

		if(isKing(row, col)) 
			weight += 2;
		else
			weight++;

		return weight;
	}

	/**
	 * Calculates a point weight for a single board.
	 * @param board This is the board to be evaluated.
	 * @param teamVariable Represents the player whose turn it is.
	 * @return the point weight for the board.
	 */
	public int evaluateBoard(GameData board, int teamVariable) {
		int board_weight = 0;
		PieceMove[] boardAI = board.getLegalMoves(R_PAWN);
		PieceMove[] boardPlayer = board.getLegalMoves(W_PAWN);

		if(boardPlayer == null) {
			board_weight += 1000;
		}
		if(boardAI == null) {
			board_weight -= 1000;
		}
		int numJumps = getJumps(boardPlayer);
		if(numJumps != 0)
			board_weight -= numJumps * 20; //negates the +10 for AI being able to make a jump if the jump places its piece in danger. 
		if(boardAI != null) {
			for (int i = 0; i < boardAI.length; i++) {
				board_weight += board.evaluateMove(boardAI, i);
			}
		}
		return board_weight;
	}

	/**
	 * Counts the number of jumps in an array of legal moves.
	 * @param movesArray This is the array of PieceMove objects to check for jumps.
	 * @return The number of jumps in movesArray.
	 */
	private int getJumps(PieceMove[] movesArray) {
		int jumps = 0;

		if (movesArray != null) {
			if (movesArray[0].isJump())
				jumps = movesArray.length;
		}
		return jumps;
	}

	/**
	 * Prints the board for displaying.
	 */
	public void printBoard() {

		System.out.println("   0 1 2 3 4 5 6 7");		
		System.out.print("------------------\n");

		for(int row = 0; row < board.length; row++) {
			System.out.print(row + "| ");
			for(int col = 0; col < board[0].length; col++) {
				System.out.print(board[row][col] + " " );
			}
			System.out.println();
		}

	}
	/**
	 * Copies a GameData object.
	 * @param original This is the GameData object to be copied.
	 * @return an identical GameData object.
	 */
	public GameData copy(GameData original) {
		GameData copy = new GameData();
		for (int row = 0; row < original.board.length; row++) {
			for (int col = 0; col < original.board[0].length; col++) {
				copy.board[row][col] = original.board[row][col];
			}
		}
		return copy;
	}

	/**
	 * Gets a list of all the boards that are possible 1 move into the future from a current board.
	 * @param board This is the initial state of the board.
	 * @param team This is the player whose pieces are moving.
	 * @return a linked list of boards.
	 */
	public LinkedList<GameData> getFutureBoards(GameData board, int team) {
		PieceMove[] futureBoardsArray = board.getLegalMoves(team);

		LinkedList<GameData> futureBoardsList = new LinkedList<GameData>();

		GameData cpyBoard = copy(board);

		if (futureBoardsArray != null) {
			for (int i = 0; i < futureBoardsArray.length; i++) {
				cpyBoard.makeMove(futureBoardsArray[i]);
				futureBoardsList.add(cpyBoard);
				cpyBoard = copy(board);
			}
		}

		return futureBoardsList;
	}
}	



