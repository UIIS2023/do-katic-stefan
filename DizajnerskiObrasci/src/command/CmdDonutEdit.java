package command;

import geometry.Donut;

public class CmdDonutEdit implements Command {

	private Donut oldState;
	private Donut newState;
	private Donut original = new Donut();
	
	private String log;
	
	public CmdDonutEdit(Donut oldState, Donut newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		try {
			original.getCenter().setX(oldState.getCenter().getX());
			original.getCenter().setY(oldState.getCenter().getY());
			original.setRadius(oldState.getRadius());
			original.setInnerRadius(oldState.getInnerRadius());
			original.setBorder(oldState.getBorder());
			original.setFill(oldState.getFill());
			
			oldState.getCenter().setX(newState.getCenter().getX());
			oldState.getCenter().setY(newState.getCenter().getY());
			oldState.setRadius(newState.getRadius());
			oldState.setInnerRadius(newState.getInnerRadius());
			oldState.setBorder(newState.getBorder());
			oldState.setFill(newState.getFill());
			
			log = "EDIT OLD_DONUT " + this.original.toString() + " NEW_DONUT " + this.oldState.toString();
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
			oldState.setInnerRadius(original.getInnerRadius());
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