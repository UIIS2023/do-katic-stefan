package command;

import geometry.Point;

public class CmdPointEdit implements Command {
	
	private Point oldState;
	private Point newState;
	private Point original = new Point();
	
	private String log;
	
	public CmdPointEdit(Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		try {
			original.setX(oldState.getX());
			original.setY(oldState.getY());
			original.setBorder(oldState.getBorder());
			
			oldState.setX(newState.getX());
			oldState.setY(newState.getY());
			oldState.setBorder(newState.getBorder());
			
			log = "EDIT OLD_POINT " + this.original.toString() + " NEW_POINT " + this.oldState.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			oldState.setX(newState.getX());
			oldState.setY(newState.getY());
			oldState.setBorder(newState.getBorder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unexecute() {
		try {
			oldState.setX(original.getX());
			oldState.setY(original.getY());
			oldState.setBorder(original.getBorder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return log;
	}	
}