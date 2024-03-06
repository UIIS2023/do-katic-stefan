package strategy;

import java.io.IOException;

public class SaveStrategyManager implements SaveStrategy {
	private SaveStrategy strategy;

	public void saveToFile(String path) throws ClassNotFoundException, IOException {
		strategy.saveToFile(path);
	}
	
	public SaveStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(SaveStrategy strategy) {
		this.strategy = strategy;
	}
}
