package command;

import java.io.Serializable;

import geometry.Shape;
import mvc.Model;

public class CmdShapeBringToFront implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2881759709965277321L;
	private Model model;
	private Shape shape;
	private int oldPosition;
	
	private String log;

	public CmdShapeBringToFront(Model model, Shape shape) {
		this.model = model;
		this.shape = shape;
		this.oldPosition = model.getIndex(shape);
	}

	@Override
	public void execute() {
		model.remove(shape);
		model.add(shape);
		model.removeSelectedShape(shape);
		model.addSelectedShape(shape);

		log = "BRING_TO_FRONT " + shape.toString() + " OLD_POSITION " + oldPosition + " NEW POSITION " + model.getIndex(shape);
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
