package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Point;
import geometry.Shape;
import hexagon.Hexagon;

public class HexagonAdapter extends Shape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5104035617434421196L;
	private Hexagon hexagon;

	public HexagonAdapter(Point center, int r) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), r);
	}
	
	public HexagonAdapter(Point center, int r, boolean selected) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), r);
		this.setSelected(selected);
	}
	
	public HexagonAdapter(Point center, int r, Color border, Color fill) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), r);
		this.hexagon.setBorderColor(border);
		this.hexagon.setAreaColor(fill);
	}
	
	public HexagonAdapter() {
		this.hexagon = new Hexagon(0, 0, 0);
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.setX(byX);
		this.setY(byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter)
			return (this.hexagon.getR() - ((HexagonAdapter) o).getHexagon().getR());
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return this.hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		this.hexagon.paint(g);
	}
	
	@Override
	public boolean isSelected() {
		return this.hexagon.isSelected();
	}
	
	@Override
	public void setSelected(boolean selected) {
		this.hexagon.setSelected(selected);
	}
	
	private Hexagon getHexagon() {
		return this.hexagon;
	}
	
	public int getX() {
		return this.hexagon.getX();
	}
	
	public void setX(int x) {
		this.hexagon.setX(x);
	}
	
	public int getY() {
		return this.hexagon.getY();
	}
	
	public void setY(int y) {
		this.hexagon.setY(y);
	}
	
	public int getRadius() {
		return this.hexagon.getR();
	}
	
	public void setRadius(int r) {
		this.hexagon.setR(r);
	}
	
	@Override
	public Color getBorder() {
		return this.hexagon.getBorderColor();
	}
	
	@Override
	public void setBorder(Color color) {
		this.hexagon.setBorderColor(color);
	}
	
	public Color getFill() {
		return this.hexagon.getAreaColor();
	}
	
	public void setFill(Color color) {
		this.hexagon.setAreaColor(color);
	}
	
	public String toString() {
		return "HEXAGON CENTER (" + this.hexagon.getX() + "," + this.hexagon.getY() + ") RADIUS " + getRadius() + " BORDER " + getBorder() + " FILL " + getFill();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter h = (HexagonAdapter) obj;
			if ((this.getX() == h.getX()) &&
				(this.getY() == h.getY()) &&
				(this.getRadius() == h.getRadius())) {
				return true;
			} else 
				return false;
		} else 
			return false;
	}
	
	public HexagonAdapter clone() {
		HexagonAdapter cloneHexagon = new HexagonAdapter(new Point(getX(), getY()), getRadius(), getBorder(), getFill());
		cloneHexagon.setSelected(isSelected());
		return cloneHexagon;
	}
}
