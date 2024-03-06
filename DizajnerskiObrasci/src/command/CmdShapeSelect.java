package command;

import java.io.Serializable;

import geometry.Shape;
import mvc.Model;

public class CmdShapeSelect implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1307114048648365223L;
	private Model model;
	private Shape shape;

	private String log;
	
	public CmdShapeSelect(Model model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		shape.setSelected(true);
		model.addSelectedShape(shape);

		log = "SELECT " + shape.toString();
	}

	@Override
	public void unexecute() {
		model.removeSelectedShape(shape);
		shape.setSelected(false);
	}

	@Override
	public String toString() {
		return log;
	}

}
