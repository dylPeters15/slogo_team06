package backend.states;

import javafx.scene.paint.Color;

/**
 * The enumeration between javafx Color and indices.
 * @author Yilin Gao
 *
 */
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
	
	/**
	 * The method to get the current ColorList index.
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * The method to get the current ColorList Color.
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * The method to change the Color of a given index to a new color determined by its RGB values.
	 * If the index exists, return the index. Otherwise return -1.
	 * @param index
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public static int setColor(int index, int r, int g, int b) {
		if (ColorList.fromInt(index) != null) {
			ColorList.fromInt(index).color = Color.rgb(r,g,b);
			return index;
		}
		else {
			return (-1);
		}
	}
	
	/**
	 * The method to get the corresponding ColorList from an index.
	 * If no matching index, return null.
	 * @param index
	 * @return
	 */
	public static ColorList fromInt(int index) {
		for (ColorList cl: ColorList.values()) {
			if (cl.getIndex() == index) return cl;
		}
		return null;
	}
}
