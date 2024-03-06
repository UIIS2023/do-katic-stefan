package command;

import java.io.Serializable;

public class CmdRedo implements Command, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3519113419462018638L;
	private Command command;

	public CmdRedo(Command cmd) {
		this.command = cmd;
	}

	@Override
	public void execute() {
		command.execute();
	}

	@Override
	public void unexecute() {
		command.unexecute();
	}

	public Command getCommand() {
		return command;
	}

	@Override
	public String toString() {
		return "REDO " + command.toString();
	}
}
