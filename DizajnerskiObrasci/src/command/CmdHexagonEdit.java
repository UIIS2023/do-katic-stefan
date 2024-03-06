package command;

import adapter.HexagonAdapter;

public class CmdHexagonEdit implements Command {

	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original = new HexagonAdapter();

	private String log;
	
	public CmdHexagonEdit(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		original.setX(oldState.getX());
		original.setY(oldState.getY());
		original.setRadius(oldState.getRadius());
		original.setBorder(oldState.getBorder());
		original.setFill(oldState.getFill());
		
		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setRadius(newState.getRadius());
		oldState.setBorder(newState.getBorder());
		oldState.setFill(newState.getFill());
		
		log = "EDIT OLD_HEXAGON " + this.original.toString() + " NEW_HEXAGON " + this.oldState.toString();
	}

	@Override
	public void unexecute() {
		oldState.setX(original.getX());
		oldState.setY(original.getY());
		oldState.setRadius(original.getRadius());
		oldState.setBorder(original.getBorder());
		oldState.setFill(original.getFill());
	}
	
	@Override
	public String toString() {
		return log;
	}	
}