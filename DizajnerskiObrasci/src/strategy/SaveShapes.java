package strategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import geometry.Shape;

public class SaveShapes implements SaveStrategy {
	
	private List<Shape> shapes;

	@Override
	public void saveToFile(String path) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(path);
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
	    objectOutputStream.writeObject(shapes);
	    objectOutputStream.flush();
	    objectOutputStream.close();
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(List<Shape> shapes) {
		this.shapes = shapes;
	}

}
