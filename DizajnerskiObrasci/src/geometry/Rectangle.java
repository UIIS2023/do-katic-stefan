package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends AreaShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1380983494381137428L;
	private Point upperLeftPoint;
	private int width;
	private int height;

	public Rectangle() {
		upperLeftPoint = new Point();
	}

	public Rectangle(Point upperLeftPoint, int height, int width) {
		this.upperLeftPoint = upperLeftPoint;
		this.width = width;
		this.height = height;
	}

	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected) throws Exception {
		this(upperLeftPoint, height, width);
		setSelected(selected);
	}

	public Rectangle(Point upperLeftPoint, Integer height, Integer width, Color border, Color fill) {
		this(upperLeftPoint, height, width);
		setBorder(border);
		setFill(fill);
	}

	public int area() {
		return width * height;
	}

	public boolean equals (Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			if (this.upperLeftPoint.equals(r.getUpperLeftPoint()) &&
					this.height == r.getHeight() &&
					this.width == r.getWidth()) {
				return true;
			} else 
				return false;
		} else 
			return false;

	}


	@Override
	public void moveBy(int byX, int byY) {
		upperLeftPoint.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Rectangle) 
			return (int) (this.area() - ((Rectangle) o).area());

		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		if (this.getUpperLeftPoint().getX() <= x
				&& x <= (this.getUpperLeftPoint().getX() + width)
				&& this.getUpperLeftPoint().getY() <= y
				&& y <= (this.getUpperLeftPoint().getY() + height)) {
			return true;
		} else
			return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getFill());
		g.fillRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.width, this.getHeight());
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() -3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() + width - 3, this.getUpperLeftPoint().getY() - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() + height - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() + width - 3, this.getUpperLeftPoint().getY() + height - 3, 6, 6);
		} else {
			g.setColor(getBorder());
		}
		g.drawRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.width, this.getHeight());
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) throws Exception {
		if(upperLeftPoint.getX() >= 0 && upperLeftPoint.getY() >= 0)
			this.upperLeftPoint = upperLeftPoint;
		else throw new Exception("X and Y have to be greater than 0.");
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) throws Exception {
		if (width > 0)
			this.width = width;
		else 
			throw new NumberFormatException("Width has to be greater than 0.");
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) throws Exception {	
		if (height > 0)
			this.height = height;
		else 
			throw new NumberFormatException("Height has to be greater than 0.");
	}
	
	public String toString() {
		return "RECTANGLE UPPER_LEFT_POINT (" + upperLeftPoint.getX() + "," + upperLeftPoint.getY() + ") WIDTH " + width + " HEIGHT " + height + " BORDER " + getBorder() + " FILL " + getFill();
	}
	
	public Rectangle clone() {
		Rectangle cloneRectangle = new Rectangle(new Point(this.upperLeftPoint.getX(), this.upperLeftPoint.getY()), this.height, this.width, this.getBorder(), this.getFill());
		return cloneRectangle;
	}
}
