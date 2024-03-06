package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6550468934858885795L;
	private Point startPoint;
	private Point endPoint;

	public Line() {
		startPoint = new Point();
		endPoint = new Point();
	}

	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		setSelected(selected);
	}
	
	public Line(Line line) {
		this(line.getStartPoint(), line.getEndPoint());
		setSelected(line.isSelected());
	}

	public Line(Point start, Point end, Color border) {
		this(start, end);
		setBorder(border);
	}

	public double length() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}

	public Point middleOfLine() {
		int middleByX = (this.getStartPoint().getX() + this.getEndPoint().getX()) / 2;
		int middleByY = (this.getStartPoint().getY() + this.getEndPoint().getY()) / 2;
		Point p = new Point(middleByX, middleByY);
		return p;
	}

	public boolean equals (Object obj) {
		if (obj instanceof Line) {
			Line l = (Line) obj;
			if (this.startPoint.equals(l.getStartPoint()) &&
					this.endPoint.equals(l.getEndPoint())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void moveBy(int byX, int byY) {
		startPoint.moveBy(byX, byY);
		endPoint.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Line) {
			return (int) (this.length() - ((Line) o).length());
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		if ((startPoint.distance(x, y) + endPoint.distance(x, y)) - length() <= 0.05)
			return true;
		else
			return false;

	}

	@Override
	public void draw(Graphics g) {
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getStartPoint().getX() - 3, this.getStartPoint().getY() - 3, 6, 6);
			g.drawRect(this.getEndPoint().getX() - 3, this.getEndPoint().getY() - 3, 6, 6);
			g.drawRect(this.middleOfLine().getX() - 3, this.middleOfLine().getY() - 3, 6, 6);
		} else {
			g.setColor(getBorder());
		}
		g.drawLine(this.getStartPoint().getX(), this.getStartPoint().getY(), this.getEndPoint().getX(), this.getEndPoint().getY());
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) throws Exception {
		if(startPoint.getX() >= 0 && startPoint.getY() >= 0)
			this.startPoint = startPoint;
		else throw new Exception("X and Y have to be INTEGER > 0.");
	}
	
	public void setEndPoint(Point endPoint) throws Exception {
		if(endPoint.getX() >= 0 && endPoint.getY() >= 0)
			this.endPoint = endPoint;
		else throw new Exception("X and Y have to be INTEGER > 0.");
	}
	
	public void setStartPoint(int sX, int sY) throws Exception {
		this.startPoint.setX(sX);
		this.startPoint.setY(sY);
	}
	
	public void setEndPoint(int eX, int eY) throws Exception {
		this.endPoint.setX(eX);
		this.endPoint.setY(eY);
	}

	public Point getEndPoint() {
		return endPoint;
	}
	
	public Line clone() {
		Line cloneLine = new Line(new Point(this.startPoint.getX(), this.startPoint.getY()), new Point(this.endPoint.getY(), this.endPoint.getY()), getBorder());
		return cloneLine;
	}

	public String toString() {
		return "LINE START_POINT ("+ startPoint.getX() + "," + startPoint.getY() + ") END_POINT (" + endPoint.getX() + "," + endPoint.getY() + ") BORDER " + getBorder();
	}
}
