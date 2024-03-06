package mvc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import geometry.Shape;

public class Model {
	private List<Shape> shapes = new ArrayList<Shape>();
	private List<Shape> selectedShapes = new ArrayList<Shape>();

	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public List<Shape> getShapes(){
		return shapes;
	}
	
	public void add(Shape s) {
		shapes.add(s);
	}
	
	public void add(int index, Shape shape) {
		shapes.add(index, shape);
	}
	
	public void remove(int index) {
		shapes.remove(index);
	}
	
	public void remove(Shape s) {
		shapes.remove(s);
	}
	
	public void update(Shape s, int index) {
		shapes.set(index, s);
	}
	
	public Shape get(int i) {
		return shapes.get(i);
	}
	
	public int getIndex(Shape shape) {
		return shapes.lastIndexOf(shape);
	}
	
	public void swap(int oldIndex, int newIndex) {
		Collections.swap(shapes, oldIndex, newIndex);
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}
	
	public PropertyChangeSupport getSupport() {
		return support;
	}

	public void setSupport(PropertyChangeSupport support) {
		this.support = support;
	}
	
	public List<Shape> getSelectedShapes() {
		return selectedShapes;
	}
	
	public void setSelectedShapes(List<Shape> selectedShapes) {
		support.firePropertyChange("selectedShapes", this.selectedShapes, selectedShapes);
		this.selectedShapes = selectedShapes;
	}
	
	public void addSelectedShape(Shape shape) {
		List<Shape> oldSelectedShapes = new ArrayList<Shape>(this.selectedShapes);
		this.selectedShapes.add(shape);
		support.firePropertyChange("selectedShapes", oldSelectedShapes, this.selectedShapes);
	}
	
	public void addSelectedShapes(List<Shape> shapes) {
		List<Shape> oldSelectedShapes = new ArrayList<Shape>(this.selectedShapes);
		this.selectedShapes.addAll(shapes);
		support.firePropertyChange("selectedShapes", oldSelectedShapes, this.selectedShapes);
	}
	
	public void removeSelectedShape(Shape shape) {
		List<Shape> oldSelectedShapes = new ArrayList<Shape>(this.selectedShapes);
		this.selectedShapes.remove(shape);
		support.firePropertyChange("selectedShapes", oldSelectedShapes, this.selectedShapes);
	}
	
	public void removeSelectedShapes(List<Shape> shapes) {
		List<Shape> oldSelectedShapes = new ArrayList<Shape>(this.selectedShapes);
		this.selectedShapes.removeAll(shapes);
		support.firePropertyChange("selectedShapes", oldSelectedShapes, this.selectedShapes);
	}
	
	public void removeAllSelectedShapes() {
		List<Shape> oldSelectedShapes = new ArrayList<Shape>(this.selectedShapes);
		this.selectedShapes.clear();
		support.firePropertyChange("selectedShapes", oldSelectedShapes, this.selectedShapes);
	}
}
