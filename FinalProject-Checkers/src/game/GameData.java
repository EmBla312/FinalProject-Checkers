package game;
import java.util.LinkedList;
import java.util.Scanner;

public class GameData {
	
	public static final int EMPTY = 0;
	public static final int R_PAWN = 1;
	public static final int R_KING = 2;
	public static final int W_PAWN = 3;
	public static final int W_KING = 4;

	private int[][] board; 

	public GameData() {

		board = new int[8][8];
		setUpBoard();
	}
	
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
	
	public boolean isGameOver(int team) {
		PieceMove[] legalMoves = getLegalMoves(team);
		return legalMoves == null;
	}
	public int pieceAt(int row, int col) {
		
		return board[row][col];		
	}
	public void setPieceAt(int row, int col, int piece) {
		
		board[row][col] = piece;
	}
	
	public boolean isKing(int fromRow, int fromCol) {
		return board[fromRow][fromCol] == W_KING;
	}
	public void makeMove(PieceMove move) {
		
		makeMove(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
	}

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
		if(moves.isEmpty())
			return null;
		
		else { 
			PieceMove[] moveArray = new PieceMove[moves.size()];
				for(int i = 0; i < moves.size(); i++)
			 moveArray[i] = moves.get(i);
		       
		       return moveArray;
		}
	} // end getLegalMoves()
	
	//explain this method plz
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
	    
	    if (moves.isEmpty())
	       return null;
	    
	    else {
	    	
	    	PieceMove[] moveArray = new PieceMove[moves.size()];
			for(int i = 0; i < moves.size(); i++)
				moveArray[i] = moves.get(i);
	       
	       return moveArray;
	    }
	}	//end legalJumps
	
	
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
	
	public GameData copy(GameData original) {
		GameData copy = new GameData();
		for (int row = 0; row < original.board.length; row++) {
			for (int col = 0; col < original.board[0].length; col++) {
				copy.board[row][col] = original.board[row][col];
			}
		}
		return copy;
	}
	
	public LinkedList<GameData> getFutureBoards(GameData board, int team) {
		PieceMove[] depth1array = board.getLegalMoves(team);
		
		LinkedList<GameData> depth1 = new LinkedList<GameData>();
		
		GameData cpyBoard = copy(board);
		
		for (int i = 0; i < depth1array.length; i++) {
			cpyBoard.makeMove(depth1array[i]);
			depth1.add(cpyBoard);
			cpyBoard = copy(board);
		}
		
		return depth1;
	}
}	



