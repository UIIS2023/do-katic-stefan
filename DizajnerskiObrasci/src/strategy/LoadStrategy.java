package strategy;

import java.io.IOException;

public interface LoadStrategy {
	public void loadFile(String path) throws IOException, ClassNotFoundException;
}
