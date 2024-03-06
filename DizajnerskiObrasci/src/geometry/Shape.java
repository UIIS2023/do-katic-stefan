package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Moveable, Comparable<Object>, Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 177999491342269311L;
	private boolean selected;
	private Color border = Color.BLACK;
	
	public Shape() {
		
	}
	
	public Shape(boolean selected) {
		this.selected = selected;
	}
	
	public abstract boolean contains(int x, int y);
	
	public abstract void draw(Graphics g);
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setBorder(Color border) {
		this.border = border;
	}

	public Color getBorder() {
		return border;
	}
	
	public abstract Shape clone();
}
