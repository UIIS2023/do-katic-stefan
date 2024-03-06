package strategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mvc.Controller;

public class LoadCommandLog implements LoadStrategy {
	
	private Controller controller;
	
	public LoadCommandLog(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void loadFile(String path) throws IOException, ClassNotFoundException {
	    BufferedReader br = new BufferedReader(new FileReader(path));
	    String row;
	    List<String> log = new ArrayList<String>();
	    while ((row = br.readLine()) != null) {
	    	log.add(row);
	    }
	    controller.setLoadedLog(log);
	    br.close();
	}
}
