package command;

import geometry.Line;

public class CmdLineEdit implements Command {
	
	private Line oldState;
	private Line newState;
	private Line original = new Line();
	
	private String log;
	
	public CmdLineEdit(Line oldState, Line newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		try {
			original.getStartPoint().setX(oldState.getStartPoint().getX());
			original.getStartPoint().setY(oldState.getStartPoint().getY());
			original.getEndPoint().setX(oldState.getEndPoint().getX());
			original.getEndPoint().setY(oldState.getEndPoint().getY());
			original.setBorder(oldState.getBorder());
			
			oldState.getStartPoint().setX(newState.getStartPoint().getX());
			oldState.getStartPoint().setY(newState.getStartPoint().getY());
			oldState.getEndPoint().setX(newState.getEndPoint().getX());
			oldState.getEndPoint().setY(newState.getEndPoint().getY());
			oldState.setBorder(newState.getBorder());
			
			log = "EDIT OLD_LINE " + this.original.toString() + " NEW_LINE " + this.oldState.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unexecute() {
		try {
			oldState.getStartPoint().setX(original.getStartPoint().getX());
			oldState.getStartPoint().setY(original.getStartPoint().getY());
			oldState.getEndPoint().setX(original.getEndPoint().getX());
			oldState.getEndPoint().setY(original.getEndPoint().getY());
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