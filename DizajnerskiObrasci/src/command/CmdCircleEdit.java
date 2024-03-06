package command;

import geometry.Circle;

public class CmdCircleEdit implements Command {
	
	private Circle oldState;
	private Circle newState;
	private Circle original = new Circle();
	
	private String log;
	
	public CmdCircleEdit(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		try {
			original.getCenter().setX(oldState.getCenter().getX());
			original.getCenter().setY(oldState.getCenter().getY());
			original.setRadius(oldState.getRadius());
			original.setBorder(oldState.getBorder());
			original.setFill(oldState.getFill());
			
			oldState.getCenter().setX(newState.getCenter().getX());
			oldState.getCenter().setY(newState.getCenter().getY());
			oldState.setRadius(newState.getRadius());
			oldState.setBorder(newState.getBorder());
			oldState.setFill(newState.getFill());
			
			log = "EDIT OLD_CIRCLE " + this.original.toString() + " NEW_CIRCLE " + this.oldState.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unexecute() {
		try {
			oldState.getCenter().setX(original.getCenter().getX());
			oldState.getCenter().setY(original.getCenter().getY());
			oldState.setRadius(original.getRadius());
			oldState.setBorder(original.getBorder());
			oldState.setFill(original.getFill());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return log;
	}	
}