package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8647674115539478156L;
	private int x;
	private int y;

	public Point() {

	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y, boolean selected) {
		this(x, y);
		setSelected(selected);
	}

	public Point(Point point) {
		this(point.getX(), point.getY());
		setSelected(point.isSelected());
	}

	public Point(Integer x, Integer y, Color border) {
		this.x = x;
		this.y = y;
		setBorder(border);
	}

	public double distance(int x2, int y2) {
		double dx = this.x - x2;
		double dy = this.y - y2;
		double d = Math.sqrt(dx*dx + dy*dy);
		return d;
	}

	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point) o;
			if (this.x == p.getX() && this.y == p.getY())
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.x += byX;
		this.y += byY;	
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Point) {
			Point start = null;
			start = new Point(0,0);
			return (int) (this.distance(start.getX(), start.getY()) - ((Point) o).distance(start.getX(),  start.getY()));
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		if (this.distance(x, y) <= 3 )
			return true;
		return false;
	}

	@Override
	public void draw(Graphics g) {
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.x - 3, this.y - 3, 6, 6);
		} else {
			g.setColor(getBorder());
		}
		g.drawLine(this.x - 2, y, this.x + 2, y);
		g.drawLine(x, this.y - 2, x, this.y + 2);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) throws Exception {
		if (x >= 0)
			this.x = x;
		else
			throw new NumberFormatException("X has to be INTEGER > 0.");
	}

	public int getY() {
		return y;
	}

	public void setY(int y) throws Exception {
		if (y >= 0)
			this.y = y;
		else
			throw new NumberFormatException("Y has to be INTEGER > 0.");
	}
	
	public Point clone() {
		Point clonePoint = new Point(this.getX(), this.getY(), this.getBorder());
		clonePoint.setSelected(isSelected());
		return clonePoint;
	}

	public String toString() {
		return "POINT ("+ getX() +"," + getY() + ")" + " BORDER " + getBorder();
	}

}
