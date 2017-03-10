package backend.states;

import javafx.scene.image.Image;

/**
 * The enumeration between Turtle's images and indices.
 * @author Yilin Gao
 *
 */
public enum ShapeList {

	SHAPE1(0, "turtleicon.png"),
	SHAPE2(1, "turtle.png"),
	SHAPE3(2, "turtle_Yilin.png");
	private int index;
	private Image image;
	
	private ShapeList(int index, String imagePath) {
		this.index = index;
		this.image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
	}
	
	/**
	 * The method to get the current ShapeList index.
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * The method to get the current ShapeList image.
	 * @return
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * The method to get the corresponding ShapeList from an index.
	 * If no matching index, return null.
	 * @param index
	 * @return
	 */
	public static ShapeList fromInt(int index) {
		for (ShapeList sl: ShapeList.values()) {
			if (sl.getIndex() == index) return sl;
		}
		return null;
	}
}
