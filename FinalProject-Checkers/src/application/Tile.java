package application;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
public class Tile extends Rectangle {

	public Tile(int row, int col, boolean color){
		setWidth(Main.TILE_SIZE);
		setHeight(Main.TILE_SIZE);
		relocate(row * Main.TILE_SIZE, col * Main.TILE_SIZE);
		
		setFill(color ? Color.valueOf("silver") : Color.valueOf("black"));
	}
}