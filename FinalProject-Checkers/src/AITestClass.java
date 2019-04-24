
public class AITestClass {

	public static void main(String[] args) {
		GameData board = new GameData();
		AITree thisTurn = new AITree(5, board);
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
				//AITree thisTurn = new AITree(5, board);
				thisTurn.makeMove(board);
				team = GameData.R_PAWN;
			}
			System.out.println("\n");
		}
	}
	
	

}
