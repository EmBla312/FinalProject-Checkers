package application;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import game.GameData;

public class Piece extends StackPane {
	
	private int fromX;
	private int fromY;
	
	public Piece(int x, int y, int color) {
		fromX = x;
		fromY = y;
		
		relocate(x * Main.TILE_SIZE, y * Main.TILE_SIZE);
		
		Circle piece = null;
		//Circle whitePiece = null;
		
		if(color == GameData.R_PAWN) {
			//first two param will return the exact point location of where the circle is so (50, 50) of the Tile's dimension (100, 100)
			piece = new Circle(Main.TILE_SIZE / 2, Main.TILE_SIZE / 2, Main.TILE_SIZE * 0.4);			
			piece.setFill(Color.valueOf("red"));
			piece.setStroke(Color.valueOf("black"));
			piece.setStrokeWidth(3);
			
			//centers piece on board
			piece.setTranslateX(Main.TILE_SIZE * .10);
			piece.setTranslateY(Main.TILE_SIZE * .10);
		} 
		else {
			piece = new Circle(Main.TILE_SIZE / 2, Main.TILE_SIZE / 2, Main.TILE_SIZE * 0.4);
			piece.setFill(Color.valueOf("white"));
			piece.setStroke(Color.valueOf("black"));
			piece.setStrokeWidth(3);
			
			//centers pieces on board
			piece.setTranslateX(Main.TILE_SIZE * .10);
			piece.setTranslateY(Main.TILE_SIZE * .10);
		}
//		if(piece != null)
//			getChildren().add(piece);
//		if(whitePiece != null)
//		getChildren().add(whitePiece);
	
	}
}

