package game;
import java.util.Scanner;


public class Runner {
	/**
	 * Level of difficulty to play the game
	 */
	private static int level;
	/**
	 * The board to play the game on.
	 */

	private static GameData board = new GameData();
	/**
	 * The player whose pieces are currently movable.
	 */
	private static int team;
	private static Scanner sc = new Scanner(System.in);
	private static PieceMove[] legalMoves;


	public static void main(String[] args) {			
		playCheckers();	
	}

	/**
	 * Sets the level to easy, medium, or hard.
	 */
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
	/**
	 * Plays the game in easy mode, with the computer player choosing its move from the bottom of a priority queue and looking only one move ahead.
	 */
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

				//team = GameData.R_PAWN;
			}
		}	

//		String winner;
//		if(team == GameData.W_PAWN)
//			winner = "AI Wins.";
//		else
//			winner = "You Win.";
//		System.out.println("GAME OVER. " + winner);  

	}
	/**
	 * Plays the game in medium mode, with the computer player choosing its move from the top of a priority queue and looking only one move ahead.
	 */
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
//
//		String winner;
//		if(team == GameData.W_PAWN)
//			winner = "AI Wins.";
//		else
//			winner = "You Win.";
//		System.out.println("GAME OVER. " + winner);  

	}
	/**
	 * Plays the game in hard mode, with the computer player using a general tree to plan 4 moves ahead.
	 */
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
				thisTurn = new AITree(7, board);
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
//		board.printBoard();
//		String winner;
//		if(team == GameData.W_PAWN)
//			winner = "AI Wins.";
//		else
//			winner = "You Win.";
//		System.out.println("GAME OVER. " + winner);  
		


	}
	/**
	 * Checks whether the move the player wants to make is included in the list of legal moves.
	 * @param tempMove This is the move to check for validity.
	 * @param i This is the index that should hold tempMove.
	 * @return true if the list of legal moves contains tempMove.
	 */
	private static boolean isValidMove(PieceMove tempMove, int i) {
		return tempMove.getFromRow() == legalMoves[i].getFromRow() &&
				tempMove.getFromCol() == legalMoves[i].getFromCol() &&
				tempMove.getToRow() == legalMoves[i].getToRow() &&
				tempMove.getToCol() == legalMoves[i].getToCol();
	}

	/**
	 * Gets the player's indices and stores them in a PieceMove object.
	 * @return a PieceMove object containing the move the player wants to make.
	 */
	private static PieceMove playerMove() {

		int fromRow = getFromRow();
		int fromCol = getFromCol();
		int toRow = getToRow();
		int toCol = getToCol();

		PieceMove newMove = new PieceMove(fromRow, fromCol, toRow, toCol);
		return newMove;
	}
	/**
	 * Gets the indices of the tile the player wants to move to. Used to get the next tile if the player must make a double jump.
	 * @return the indices entered by the player.
	 */
	private static PieceMove playerMove(int fromRow, int fromCol) {

		int toRow = getToRow();
		int toCol = getToCol();

		PieceMove newMove = new PieceMove(fromRow, fromCol, toRow, toCol);
		return newMove;
	}

	/**
	 * Gets the column index of the tile the player wants to move to.
	 * @return the column index entered by the player.
	 */
	private static int getToCol() {
		System.out.println("Enter toCol:");
		int toCol = sc.nextInt();

		while (toCol < 0 || toCol > 7) {
			System.out.println("You can't move there. Try again: ");
			toCol = sc.nextInt();
		}

		return toCol;
	}

	/**
	 * Gets the row index of the tile the player wants to move to.
	 * @return therow index entered by the player.
	 */
	private static int getToRow() {
		System.out.println("Enter toRow:");
		int toRow = sc.nextInt();

		while (toRow < 0 || toRow > 7) {
			System.out.println("You can't move there. Try again: ");
			toRow = sc.nextInt();
		}

		return toRow;
	}

	/**
	 * Gets the row index of the tile that contains the piece the player wants to move.
	 * @return the row index entered by the player.
	 */
	private static int getFromRow() {
		System.out.println("Enter fromRow:");
		int fromRow = sc.nextInt();

		while (fromRow < 0 || fromRow > 7) {
			System.out.println("You can't move there. Try again: ");
			fromRow = sc.nextInt();
		}

		return fromRow;
	}

	/**
	 * Gets the column index of the tile that contains the piece the player wants to move.
	 * @return the column index entered by the player.
	 */
	private static int getFromCol() {
		System.out.println("Enter fromCol:");
		int fromCol = sc.nextInt();

		while (fromCol < 0 || fromCol > 7) {
			System.out.println("You can't move there. Try again: ");
			fromCol = sc.nextInt();
		}

		return fromCol;
	}

	/**
	 * Calls makeMove() with a PieceMove object. If the move is a jump, this method checks if the player must make a second jump. After Move, checks if opposing team has moves it can make.
	 * If not, game over.
	 * @param move
	 */
	private static void doMove(PieceMove move) {

		board.makeMove(move);

		if (move.isJump()) {
			legalMoves = board.getLegalJumpsFrom(team,move.getToRow(),move.getToCol());
			if (legalMoves != null) {
				if (team == GameData.W_PAWN) {

					System.out.println(legalMoves.length); // testing
					System.out.println(legalMoves[0].getFromRow()+ " " + legalMoves[0].getFromCol()+ " " + legalMoves[0].getToRow()+ " " + legalMoves[0].getToCol()); //testing
					System.out.println("You must continue jumping.");
					board.printBoard();
					
					legalMoves = board.getLegalMoves(team);
					boolean illegalMove = true;
					do {
						PieceMove tempMove = playerMove(move.getToRow(), move.getToCol());
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
					

				}
				else {
					doMove(legalMoves[0]);
				}
			}
		}
		
		if(team == GameData.W_PAWN) {
			team = GameData.R_PAWN;
		}
		else {
			team = GameData.W_PAWN;
		}
		
		if(board.getLegalMoves(team) == null) {
			
			board.printBoard();
			
			if(team == GameData.W_PAWN) {
				System.out.println("GAME OVER. AI WINS.");
			}
			else {
				System.out.println("GAME OVER. YOU WIN.");
			}
			
		}
			

	

	}  // end doMakeMove();



}
