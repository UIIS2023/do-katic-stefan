package strategy;

import java.io.IOException;

public interface SaveStrategy {
	public void saveToFile(String path) throws IOException, ClassNotFoundException;
}
