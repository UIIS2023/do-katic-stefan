package command;

import java.io.Serializable;

public class CmdUndo implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3330804952441426306L;
	private Command command;

	public CmdUndo(Command cmd) {
		this.command = cmd;
	}

	@Override
	public void execute() {
		command.unexecute();
	}

	@Override
	public void unexecute() {
		command.execute();
	}

	public Command getCommand() {
		return command;
	}

	@Override
	public String toString() {
		return "UNDO " + command.toString();
	}
}
