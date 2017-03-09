package backend.states;

import javafx.scene.image.Image;

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
	
	public int getIndex() {
		return index;
	}
	
	public Image getImage() {
		return image;
	}
	
	public static ShapeList fromInt(int index) {
		for (ShapeList sl: ShapeList.values()) {
			if (sl.getIndex() == index) return sl;
		}
		return null;
	}
}
