package command;

import java.util.ArrayList;
import java.util.List;

public class CommandLogger {
	List<String> logger = new ArrayList<String>();
	
	public void log(Command cmd) {
		logger.add(cmd.toString());
		System.out.println(cmd.toString());
	}
	
	public List<String> getLog() {
		return logger;
	}
}
