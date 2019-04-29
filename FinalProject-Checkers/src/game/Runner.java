package game;
import java.util.Scanner;


public class Runner {
	private static int level;
	private static GameData board = new GameData();
	private static int team;
	private static Scanner sc = new Scanner(System.in);
	private static PieceMove[] legalMoves;

	
	public static void main(String[] args) {			
		playCheckers();	
	}
	
	private static void chooseLevel() {
		do {
			System.out.println("What level would you like to play?\n"
					+ "[1]Easy\n"
					+ "[2]Medium\n"
					+ "[3]Hard\n");
			
			level = sc.nextInt();
			
		}while(level < 1 || level > 3);
	}
	private static void playCheckers() {
		
		chooseLevel();
		
		switch(level) {
		case 1:
			playEasy();
			break;
		case 2:
			playMed();
			break;
		case 3:
			playHard();
			break;
		}
	}
	private static void playEasy() {
		PriorityQueue<PieceMove> pq = new PriorityQueue<>();
		team = 3;	//let player go first
		System.out.println("========================================");
		
		while(!board.isGameOver(team)) {			
				
			board.printBoard();
			
			if(team == GameData.R_PAWN) {
				System.out.println("========================================");
				System.out.println("MY MOVE");
				System.out.println("========================================");
			}
			else {
				System.out.println("========================================");
				System.out.println("YOUR MOVE");
				System.out.println("========================================");
			}
			
			if(team == GameData.R_PAWN) {
				legalMoves = board.getLegalMoves(team);
				
				for(int i = 0; i < legalMoves.length; i++) {
	
					pq.add( legalMoves[i] , board.evaluateMove(legalMoves,i));
				}				
				doMove(pq.remove());				
				pq.clear();
				
				team = GameData.W_PAWN;
			}
			
			else {
				legalMoves = board.getLegalMoves(team);
				boolean illegalMove = true;
				do {
					PieceMove tempMove = playerMove();
					for(int i = 0; i < legalMoves.length; i++) {
						if(isValidMove(tempMove, i)) {
							illegalMove = false;
						}						
					}
					if(illegalMove)
						System.out.println("Invalid Entry. Try again. \n");
					else {
						doMove(tempMove);
					}
				
				}while(illegalMove);
				
				team = GameData.R_PAWN;
			}
		}	
		
		String winner;
		if(team == GameData.W_PAWN)
			winner = "AI Wins.";
		else
			winner = "You Win.";
		System.out.println("GAME OVER. " + winner);  
		
	}
	
	private static void playMed() {
		PriorityQueue<PieceMove> pq = new PriorityQueue<>();
		team = 3;	//let player go first
		
		System.out.println("========================================");
		
		while(!board.isGameOver(team)) {
			
			board.printBoard();
			
			if(team == GameData.R_PAWN) {
				System.out.println("========================================");
				System.out.println("MY MOVE");
				System.out.println("========================================");
			}
			else {
				System.out.println("========================================");
				System.out.println("YOUR MOVE");
				System.out.println("========================================");
			}
			
			if(team == GameData.R_PAWN) {
				legalMoves = board.getLegalMoves(team);
				
				for(int i = 0; i < legalMoves.length; i++) {
	
					pq.add( legalMoves[i] , board.evaluateMove(legalMoves,i));
				}				
				doMove(pq.removeBack());				
				pq.clear();
				
				team = GameData.W_PAWN;
			}
			
			else {
				legalMoves = board.getLegalMoves(team);
				boolean illegalMove = true;
				do {
					PieceMove tempMove = playerMove();
					for(int i = 0; i < legalMoves.length; i++) {
						if(isValidMove(tempMove, i)) {
							illegalMove = false;
						}						
					}
					if(illegalMove)
						System.out.println("Invalid Entry. Try again. \n");
					else
						doMove(tempMove);
						
				
				}while(illegalMove);
				
				team = GameData.R_PAWN;
			}
		}	
		
		String winner;
		if(team == GameData.W_PAWN)
			winner = "AI Wins.";
		else
			winner = "You Win.";
		System.out.println("GAME OVER. " + winner);  
		
	}
	
	private static void playHard() {
		AITree thisTurn;
		team = 3;	//let player go first
		
		System.out.println("========================================");
		
		while(!board.isGameOver(team)) {
			
			board.printBoard();
			
			if(team == GameData.R_PAWN) {
				System.out.println("========================================");
				System.out.println("MY MOVE");
				System.out.println("========================================");
			}
			else {
				System.out.println("========================================");
				System.out.println("YOUR MOVE");
				System.out.println("========================================");
			}
			
			
			if(team == GameData.R_PAWN) {
				thisTurn = new AITree(200, board);
				board = thisTurn.makeMove();
								
				team = GameData.W_PAWN;
			}
			
			else {
				legalMoves = board.getLegalMoves(team);
				boolean illegalMove = true;
				do {
					PieceMove tempMove = playerMove();
					for(int i = 0; i < legalMoves.length; i++) {
						if(isValidMove(tempMove, i)) {
							illegalMove = false;
						}
						
						
					}
					if(illegalMove)
						System.out.println("Invalid Entry. Try again. \n");
					else
						doMove(tempMove);
						
				
				}while(illegalMove);
				
				team = GameData.R_PAWN;
			}
		}	
		
		String winner;
		if(team == GameData.W_PAWN)
			winner = "AI Wins.";
		else
			winner = "You Win.";
		System.out.println("GAME OVER. " + winner);  
		
		
	}
	
	private static boolean isValidMove(PieceMove tempMove, int i) {
		return tempMove.getFromRow() == legalMoves[i].getFromRow() &&
				tempMove.getFromCol() == legalMoves[i].getFromCol() &&
				tempMove.getToRow() == legalMoves[i].getToRow() &&
				tempMove.getToCol() == legalMoves[i].getToCol();
	}
	
	private static PieceMove playerMove() {
		
		int fromRow = getFromRow();
		int fromCol = getFromCol();
		int toRow = getToRow();
		int toCol = getToCol();
		
		PieceMove newMove = new PieceMove(fromRow, fromCol, toRow, toCol);
		return newMove;
	}
	
	private static PieceMove playerMove(int fromRow, int fromCol) {
		
		int toRow = getToRow();
		int toCol = getToCol();
		
		PieceMove newMove = new PieceMove(fromRow, fromCol, toRow, toCol);
		return newMove;
	}
	
	private static int getToCol() {
		System.out.println("Enter toCol:");
		int toCol = sc.nextInt();

		while (toCol < 0 || toCol > 7) {
			System.out.println("You can't move there. Try again: ");
			toCol = sc.nextInt();
		}

		return toCol;
	}

	private static int getToRow() {
		System.out.println("Enter toRow:");
		int toRow = sc.nextInt();

		while (toRow < 0 || toRow > 7) {
			System.out.println("You can't move there. Try again: ");
			toRow = sc.nextInt();
		}

		return toRow;
	}

	private static int getFromRow() {
		System.out.println("Enter fromRow:");
		int fromRow = sc.nextInt();

		while (fromRow < 0 || fromRow > 7) {
			System.out.println("You can't move there. Try again: ");
			fromRow = sc.nextInt();
		}

		return fromRow;
	}

	private static int getFromCol() {
		System.out.println("Enter fromCol:");
		int fromCol = sc.nextInt();

		while (fromCol < 0 || fromCol > 7) {
			System.out.println("You can't move there. Try again: ");
			fromCol = sc.nextInt();
		}

		return fromCol;
	}
	
	private static void doMove(PieceMove move) {
		
    board.makeMove(move);
    
  
  
    
    if (move.isJump()) {
       legalMoves = board.getLegalJumpsFrom(team,move.getToRow(),move.getToCol());
       if (legalMoves != null) {
          if (team == GameData.W_PAWN) {
        	  
        	  System.out.println(legalMoves.length); // testing
             System.out.println("You must continue jumping.");
             board.printBoard();
             doMove(playerMove(move.getToRow(), move.getToCol()));
             
             
          }
          else {
        	  //AI Turn
          }
       }
    }
    
 
    
 }  // end doMakeMove();
	
	

}
