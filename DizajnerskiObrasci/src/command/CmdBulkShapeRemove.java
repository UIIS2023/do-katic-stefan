package command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import geometry.Shape;
import mvc.Model;

public class CmdBulkShapeRemove implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1748519107756160910L;
	private Model model;
	private List<Shape> shapes;
	private int[] shapesIndex;

	private String log;
	
	public CmdBulkShapeRemove(Model model, List<Shape> shapes) {
		this.model = model;
		this.shapes = new ArrayList<>(shapes);
		this.shapesIndex = new int[shapes.size()];
	}

	@Override
	public void execute() {
		model.removeSelectedShapes(shapes);
		for (int i = 0; i < shapes.size(); i++) {
			shapesIndex[i] = model.getIndex(shapes.get(i));
			model.remove(shapesIndex[i]);
		}
	
		log = "REMOVE_BULK SHAPES " + shapes.toString() + " INDEXES " + Arrays.toString(shapesIndex);
	}

	@Override
	public void unexecute() {
		for (int i = shapes.size()-1; i >= 0 ; i--) {
			model.add(shapesIndex[i], shapes.get(i));
		}
		model.addSelectedShapes(shapes);
	}

	@Override
	public String toString() {
		return log;
	}
}
