package command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import geometry.Shape;
import mvc.Model;

public class CmdShapeUnselectAll implements Command, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8819402352234251609L;
	private Model model;
	private List<Shape> shapes;

	public CmdShapeUnselectAll(Model model) {
		this.model = model;
		this.shapes =  new ArrayList<>(model.getSelectedShapes());
	}

	@Override
	public void execute() {
		for (Shape shape : shapes) {
			shape.setSelected(false);
		}
		model.removeAllSelectedShapes();
	}

	@Override
	public void unexecute() {
		for (Shape shape : shapes) {
			shape.setSelected(true);
		}
		model.addSelectedShapes(shapes);
	}

	@Override
	public String toString() {
		return "UNSELECT_ALL";
	}

}
