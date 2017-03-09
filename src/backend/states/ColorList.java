package backend.states;

import javafx.scene.paint.Color;

public enum ColorList {
	WHITE(0, Color.WHITE),
	BLACK(1, Color.BLACK),
	BLUE(2, Color.BLUE),
	RED(3, Color.RED),
	YELLOW(4, Color.YELLOW),
	GREEN(5, Color.GREEN),
	PURPLE(6, Color.PURPLE);
	private int index;
	private Color color;
	
	private ColorList(int index, Color color) {
		this.index = index;
		this.color = color;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Color getColor() {
		return color;
	}
	
	public static int setColor(int index, int r, int g, int b) {
		if (ColorList.fromInt(index) != null) {
			ColorList.fromInt(index).color = Color.rgb(r,g,b);
			return index;
		}
		else {
			return (-1);
		}
	}
	
	public static ColorList fromInt(int index) {
		for (ColorList cl: ColorList.values()) {
			if (cl.getIndex() == index) return cl;
		}
		return null;
	}
}
