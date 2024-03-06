package command;

import java.io.Serializable;

import geometry.Shape;
import mvc.Model;

public class CmdShapeToBack implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2897543327095037826L;
	private Model model;
	private Shape shape;
	private int oldPosition;

	private String log;

	public CmdShapeToBack(Model model, Shape shape) {
		this.model = model;
		this.shape = shape;
		this.oldPosition = model.getIndex(this.shape);
	}

	@Override
	public void execute() {
		// Swap elemente tako da Shape koji treba da ide na back, zauzme manji index za - 1
		this.model.swap(oldPosition, oldPosition - 1);
		model.removeSelectedShape(shape);
		model.addSelectedShape(shape);
		
		log = "TO_BACK " + shape.toString() + " OLD_POSITION " + oldPosition + " NEW POSITION " + model.getIndex(shape);
	}

	@Override
	public void unexecute() {
		this.model.swap(oldPosition - 1, oldPosition);
		model.removeSelectedShape(shape);
		model.addSelectedShape(shape);
	}

	@Override
	public String toString() {
		return log;
	}
}
