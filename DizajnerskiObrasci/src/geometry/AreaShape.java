package geometry;

import java.awt.Color;

public abstract class AreaShape extends Shape {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2959661599531513576L;
	private Color fill = Color.WHITE;
	
	public Color getFill() {
		return fill;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}
}
