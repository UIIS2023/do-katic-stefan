package strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveCommandLog implements SaveStrategy {

	private List<String> commandLog;
	
	@Override
	public void saveToFile(String path) throws IOException {
		FileWriter fileWriter = new FileWriter(path);
		for (int i = 0; i < commandLog.size(); i++) {
			fileWriter.write(commandLog.get(i).toString() + "\n");			
		}
		fileWriter.close();
	}

	public List<String> getCommandLog() {
		return commandLog;
	}

	public void setCommandLog(List<String> commandLog) {
		this.commandLog = commandLog;
	}

}
