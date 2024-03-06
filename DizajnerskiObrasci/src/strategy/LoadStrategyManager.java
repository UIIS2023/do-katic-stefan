package strategy;

import java.io.IOException;

public class LoadStrategyManager implements LoadStrategy {
	private LoadStrategy strategy;
	
	@Override
	public void loadFile(String path) throws ClassNotFoundException, IOException {
		strategy.loadFile(path);
	}

	public LoadStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(LoadStrategy strategy) {
		this.strategy = strategy;
	}
}
