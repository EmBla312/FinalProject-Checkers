
public class CheckersRunner {


	public static void main(String[] args) {
		GameData board = new GameData();
		int team = 3;
		PieceMove[] legalMoves;
		PriorityQueue<PieceMove> pq = new PriorityQueue<>();
		
		while(board.getLegalMoves(team) != null) {			
			board.printBoard();
			if(team == GameData.R_PAWN) {
				legalMoves = board.getLegalMoves(team);
	
				for(int i = 0; i < legalMoves.length; i++) {
	
					pq.add( legalMoves[i] , board.evaluateMove(legalMoves,i));
				}				
				board.makeMove(pq.remove());				
				pq.clear();
				team = GameData.W_PAWN;
			}
			else {
				legalMoves = board.getLegalMoves(team);
				boolean illegalMove = true;
				do {
					PieceMove tempMove = board.playerMove();
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
	}
	
	

}
