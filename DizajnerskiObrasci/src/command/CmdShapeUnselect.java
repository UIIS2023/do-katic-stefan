package command;

import java.io.Serializable;

import geometry.Shape;
import mvc.Model;

public class CmdShapeUnselect implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4109660132146668335L;
	private Model model;
	private Shape shape;

	private String log;
	
	public CmdShapeUnselect(Model model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		model.removeSelectedShape(shape);
		shape.setSelected(false);
		
		log =  "UNSELECT " + shape.toString();
	}

	@Override
	public void unexecute() {
		shape.setSelected(true);
		model.addSelectedShape(shape);
	}

	@Override
	public String toString() {
		return log;
	}

}
