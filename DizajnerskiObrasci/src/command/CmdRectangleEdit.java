package command;

import geometry.Rectangle;

public class CmdRectangleEdit implements Command {

	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original = new Rectangle();

	private String log;
	
	public CmdRectangleEdit(Rectangle oldState, Rectangle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		try {
			original.getUpperLeftPoint().setX(oldState.getUpperLeftPoint().getX());
			original.getUpperLeftPoint().setY(oldState.getUpperLeftPoint().getY());
			original.setWidth(oldState.getWidth());
			original.setHeight(oldState.getHeight());
			original.setBorder(oldState.getBorder());
			original.setFill(oldState.getFill());
			
			oldState.getUpperLeftPoint().setX(newState.getUpperLeftPoint().getX());
			oldState.getUpperLeftPoint().setY(newState.getUpperLeftPoint().getY());
			oldState.setWidth(newState.getWidth());
			oldState.setHeight(newState.getHeight());
			oldState.setBorder(newState.getBorder());
			oldState.setFill(newState.getFill());
			
			log = "EDIT OLD_RECTANGLE " + this.original.toString() + " NEW_RECTANGLE " + this.oldState.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unexecute() {
		try {
			oldState.getUpperLeftPoint().setX(original.getUpperLeftPoint().getX());
			oldState.getUpperLeftPoint().setY(original.getUpperLeftPoint().getY());
			oldState.setWidth(original.getWidth());
			oldState.setHeight(original.getHeight());
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