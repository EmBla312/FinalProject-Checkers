
public class CheckersRunner {

	public static void main(String[] args) {

		GameData board = new GameData();
		int team = 3;
		PriorityQueue<PieceMove> pq = new PriorityQueue<>();

		while(board.getLegalMoves(team) != null) {			
			board.printBoard();
			if(team == 1) {
				PieceMove[] legalMoves = board.getLegalMoves(team);
	
				for(int i = 0; i < legalMoves.length; i++) {
	
					pq.add( legalMoves[i] , board.evaluateMove(legalMoves,i));
				}				
				board.makeMove(pq.remove());				
				pq.clear();
				team = 3;
			}
			else {
				board.playerMove();
				team = 1;
			}
			System.out.println("\n");
		}
	}

}
