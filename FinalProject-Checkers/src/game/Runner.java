package game;
import java.util.Scanner;

public class Runner {
	private static int level;
	private static GameData board = new GameData();
	private static int team;
	private static Scanner sc = new Scanner(System.in);
	private static PriorityQueue<PieceMove> pq = new PriorityQueue<>();
	private static PieceMove[] legalMoves;
	private static AITree thisTurn;
	
	public static void main(String[] args) {			
		
		playCheckers();	
		
	}
	
	private static void chooseLevel() {
		do {
			System.out.println("What level would you like to play?\n"
					+ "[1]Easy\n"
					+ "[2]Hard\n");
			
			level = sc.nextInt();
			
		}while(level < 1 || level > 2);
	}
	private static void playCheckers() {
		
		chooseLevel();
		
		team = 3;	//let player go first
		
		while(!board.isGameOver(team)) {			
			board.printBoard();
			if(team == GameData.R_PAWN) {
				switch(level) {
				case 1:
					legalMoves = board.getLegalMoves(team);
					
					for(int i = 0; i < legalMoves.length; i++) {
		
						pq.add( legalMoves[i] , board.evaluateMove(legalMoves,i));
					}				
					board.makeMove(pq.remove());				
					pq.clear();
					break;
				case 2:
					thisTurn = new AITree(5, board);
					board = thisTurn.makeMove();
					break;
					
				}				
				team = GameData.W_PAWN;
			}
			else {
				legalMoves = board.getLegalMoves(team);
				boolean illegalMove = true;
				do {
					PieceMove tempMove = playerMove();
					for(int i = 0; i < legalMoves.length; i++) {
						if(tempMove.getFromRow() == legalMoves[i].getFromRow() &&
								tempMove.getFromCol() == legalMoves[i].getFromCol() &&
								tempMove.getToRow() == legalMoves[i].getToRow() &&
								tempMove.getToCol() == legalMoves[i].getToCol()) {
							board.makeMove(tempMove);
							illegalMove = false;
						}
						
						
					}
					if(illegalMove)
						System.out.println("Invalid Entry. Try again. \n");
				
				}while(illegalMove);
				
				team = GameData.R_PAWN;
			}
			System.out.println("\n");
		}
		
		System.out.println("GAME OVER.");
	}
	private static PieceMove playerMove() {
		
		int fromRow = getFromRow();
		int fromCol = getFromCol();
		int toRow = getToRow();
		int toCol = getToCol();
		
		PieceMove newMove = new PieceMove(fromRow, fromCol, toRow, toCol);
		return newMove;
	}
	
	private static int getToCol() {
		System.out.println("Enter toCol");
		int toCol = sc.nextInt();

		while (toCol < 0 || toCol > 7) {
			System.out.println("You can't move there. Try again: ");
			toCol = sc.nextInt();
		}

		return toCol;
	}

	private static int getToRow() {
		System.out.println("Enter toRow");
		int toRow = sc.nextInt();

		while (toRow < 0 || toRow > 7) {
			System.out.println("You can't move there. Try again: ");
			toRow = sc.nextInt();
		}

		return toRow;
	}

	private static int getFromRow() {
		System.out.println("Enter fromRow");
		int fromRow = sc.nextInt();

		while (fromRow < 0 || fromRow > 7) {
			System.out.println("You can't move there. Try again: ");
			fromRow = sc.nextInt();
		}

		return fromRow;
	}

	private static int getFromCol() {
		System.out.println("Enter fromCol");
		int fromCol = sc.nextInt();

		while (fromCol < 0 || fromCol > 7) {
			System.out.println("You can't move there. Try again: ");
			fromCol = sc.nextInt();
		}

		return fromCol;
	}

}
