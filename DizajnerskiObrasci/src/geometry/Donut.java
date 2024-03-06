package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5306873918352480472L;
	private int innerRadius;

	public Donut() {

	}

	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}

	public Donut(Point point, Integer rad, Integer innerRad, Color border, Color fill) {
		this(point, rad, innerRad);
		setBorder(border);
		setFill(fill);
	}

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (this.getCenter().equals(d.getCenter()) &&
					this.getRadius() == d.getRadius() &&
					innerRadius == d.getInnerRadius()) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		Ellipse2D outer = new Ellipse2D.Double(
				super.getCenter().getX() - super.getRadius(), // "najlevlja" koordinata po X-u
				super.getCenter().getY() - super.getRadius(), // "najvisa" koordinata po Y-u
				super.getRadius() * 2, // sirina
				super.getRadius() * 2); // visina
	
		Ellipse2D inner = new Ellipse2D.Double(
				super.getCenter().getX() -  innerRadius, 
				super.getCenter().getY() - innerRadius, 
				innerRadius * 2, 
				innerRadius * 2);
		
		Area area = new Area(outer); // spoljasnji krug
		area.subtract(new Area(inner)); // oduzimanje unutrasnjeg kruga od spoljasnjeg
		
		g2d.setColor(super.getFill());
		g2d.fill(area);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() + getRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - getRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getRadius() -3, 6, 6);
			g.drawRect(this.getCenter().getX()  - 3, this.getCenter().getY() - getRadius() -3, 6, 6);
		}
		else
			g.setColor(super.getBorder());
		g2d.draw(area);
	}

	@Override
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && (dFromCenter > innerRadius);
	}

	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && (dFromCenter > (super.getRadius() - innerRadius));
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}


	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		if (innerRadius > 0 && innerRadius < super.getRadius())
			this.innerRadius = innerRadius;
		else
			throw new NumberFormatException("Inner radius has to be greater than 0 and less than OUTER RADIUS.");
	}

	public String toString() {
		return "DONUT CENTER (" + getCenter().getX() + "," + getCenter().getY() + ") RADIUS " + getRadius() + " INNER_RADIUS " + innerRadius + " BORDER " + getBorder() + " FILL " + getFill();
	}
	
	public Donut clone() {
		Donut cloneDonut = new Donut(new Point(getCenter().getX(), getCenter().getY()), getRadius(), getInnerRadius(), getBorder(), getFill());
		return cloneDonut;
	}
}
