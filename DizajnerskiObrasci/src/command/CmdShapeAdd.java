package command;

import java.io.Serializable;

import geometry.Shape;
import mvc.Model;

public class CmdShapeAdd implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5125891555203037325L;
	private Model model;
	private Shape shape;

	private String log;
	
	public CmdShapeAdd(Model model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		model.add(shape);
		log = "ADD " + shape.toString();
	}

	@Override
	public void unexecute() {
		model.remove(shape);
	}

	@Override
	public String toString() {
		return log;
	}

}
