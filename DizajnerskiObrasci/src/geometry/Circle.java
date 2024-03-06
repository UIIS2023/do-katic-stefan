package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends AreaShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3227363826574292092L;
	private Point center;
	private int radius;

	public Circle() {
		center = new Point();
	} 

	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		setSelected(selected);
	}

	public Circle(Point center, Integer r, Color border, Color fill) {
		this(center, r);
		setBorder(border);
		setFill(fill);
	}

	public boolean equals (Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			if (this.center.equals(c.getCenter()) &&
					this.radius == c.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public double area() {
		return radius * radius * Math.PI;
	}

	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Circle)
			return (this.radius - ((Circle) o).radius);
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return this.getCenter().distance(x, y) <= radius;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getFill());
		g.fillOval(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(), this.getRadius()*2, this.getRadius()*2);

		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() + getRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - getRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getRadius() -3, 6, 6);
			g.drawRect(this.getCenter().getX()  - 3, this.getCenter().getY() - getRadius() -3, 6, 6);
		} else {
			g.setColor(getBorder());
		}
		g.drawOval(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(), this.getRadius()*2, this.getRadius()*2);
	}

	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) throws Exception {
		if(center.getX() >= 0 && center.getY() >= 0)
			this.center = center;
		else
			throw new Exception("X and Y have to be greater or equal than 0.");
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) throws Exception {
		if (radius > 0)
			this.radius = radius;
		else
			throw new NumberFormatException("Radius has to be greater than 0.");
	}
	
	public Circle clone() {
		Circle cloneCircle = new Circle(new Point(this.getCenter().getX(), this.getCenter().getY()), this.radius, getBorder(), getFill());
		return cloneCircle;
	}

	public String toString() {
		return "CIRCLE CENTER (" + center.getX() + "," + center.getY() + ") RADIUS " + radius + " BORDER " + getBorder() + " FILL " + getFill(); 
	}
}
