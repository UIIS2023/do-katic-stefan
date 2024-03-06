package strategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import geometry.Shape;
import mvc.Model;

public class LoadShapes implements LoadStrategy {
	private Model model;
	
	public LoadShapes(Model model) {
		this.model = model;
	}

	@Override
	public void loadFile(String path) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(path);
	    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	    List<Shape> shapes = (List<Shape>) objectInputStream.readObject();
	    for (Shape shape : shapes) {
			model.add(shape);
			if (shape.isSelected())
				model.addSelectedShape(shape);
		}
	    objectInputStream.close();
	}

}
