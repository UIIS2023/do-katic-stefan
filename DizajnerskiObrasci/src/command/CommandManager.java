package command;

import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Stack;

import javax.swing.DefaultListModel;

import mvc.Controller;

public class CommandManager {
	
	private PropertyChangeSupport support;
	private Stack<Command> commandStack;
	private Stack<Command> undoStack;
	private int undoCounter;
	private int redoCounter;
	
	private CommandLogger logger;
	
	private DefaultListModel<Command> cmdDLM;
	private DefaultListModel<Command> undoDLM;
	
	public CommandManager(Controller controller) {
		support = controller.getSupport();
		commandStack = new Stack<Command>();
		undoStack = new Stack<Command>();
		undoCounter = 0;
		redoCounter = 0;
		logger = new CommandLogger();
		cmdDLM = controller.getCmdDLM();
		undoDLM = controller.getUndoDLM();
	}

	public void pushCommand(Command cmd) {	
		try {
			Stack<Command> oldCommandStack = (Stack<Command>) this.getCommandStack().clone();		
			Stack<Command> oldUndoStack = (Stack<Command>) this.getUndoStack().clone();
			
			if (cmd.getClass().getSimpleName().toString().equals("CmdUndo")) {
				getUndoStack().push(((CmdUndo) cmd).getCommand());
				undoCounter--;
				redoCounter++;
				undoDLM.addElement(cmd);
				cmdDLM.addElement(cmd);
			} else if (cmd.getClass().getSimpleName().toString().equals("CmdRedo")) {
				getCommandStack().push(((CmdRedo)cmd).getCommand());
				redoCounter--;
				undoCounter++;
				cmdDLM.addElement(cmd);
				undoDLM.removeElementAt(undoDLM.size()-1);
			} else {
				if (getUndoStack().size() > 0) {
					getUndoStack().clear();
					redoCounter = 0;
					undoDLM.removeAllElements();
				}
				getCommandStack().push(cmd);	
				undoCounter++;
				cmdDLM.addElement(cmd);
			}
			if (undoCounter > 0)
				support.firePropertyChange("enableUndo", oldCommandStack, getCommandStack());
			else 
				support.firePropertyChange("disableUndo", oldUndoStack, getUndoStack());
			
			if (redoCounter > 0) 
				support.firePropertyChange("enableRedo", oldUndoStack, getUndoStack());
			else
				support.firePropertyChange("disableRedo", oldCommandStack, getCommandStack());
		
			logger.log(cmd);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
	}
	
	public void undo() {
		try {
			CmdUndo cmdUndo = new CmdUndo(getCommandStack().pop());
			cmdUndo.execute();
			pushCommand(cmdUndo);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}	
	}

	public void redo() {
		try {
			CmdRedo cmdRedo = new CmdRedo(getUndoStack().pop());
			cmdRedo.execute();
			pushCommand(cmdRedo);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public Stack<Command> getCommandStack() {
		return commandStack;
	}

	public void setCommandStack(Stack<Command> commandStack) {
		this.commandStack = commandStack;
	}

	public Stack<Command> getUndoStack() {
		return undoStack;
	}

	public void setUndoStack(Stack<Command> undoStack) {
		this.undoStack = undoStack;
	}
	

	public void clear() {
		undoCounter = 0;
		redoCounter = 0;
		commandStack.clear();
		undoStack.clear();
		cmdDLM.clear();
		undoDLM.clear();
	}

	public List<String> getLog() {
		return logger.getLog();
	}
}
