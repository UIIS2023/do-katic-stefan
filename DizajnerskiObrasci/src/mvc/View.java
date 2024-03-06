package mvc;

import java.awt.Graphics;
import java.util.ListIterator;

import javax.swing.JPanel;

import geometry.Shape;

public class View extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Model model = new Model();
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public void paint(Graphics g) {
		ListIterator<Shape> it = model.getShapes().listIterator();
		
		super.paintComponent(g);
		//super.removeAll();
		
		while(it.hasNext()) {
			it.next().draw(g);
		}
	}
}
