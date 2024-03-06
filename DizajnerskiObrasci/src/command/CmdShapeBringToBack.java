package command;

import java.io.Serializable;

import geometry.Shape;
import mvc.Model;

public class CmdShapeBringToBack implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7895385983994381974L;
	private Model model;
	private Shape shape;
	private int oldPosition;

	private String log;
	
	public CmdShapeBringToBack(Model model, Shape shape) {
		this.model = model;
		this.shape = shape;
		this.oldPosition = model.getIndex(shape);
	}

	@Override
	public void execute() {
		model.remove(shape);
		model.add(0, shape);
		model.removeSelectedShape(shape);
		model.addSelectedShape(shape);

		log = "BRING_TO_BACK " + shape.toString() + " OLD_POSITION " + oldPosition + " NEW POSITION " + model.getIndex(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.add(oldPosition, shape);
		model.removeSelectedShape(shape);
		model.addSelectedShape(shape);
	}

	@Override
	public String toString() {
		return log;
	}
}
