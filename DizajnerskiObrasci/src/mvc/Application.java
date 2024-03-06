package mvc;

import javax.swing.JFrame;

public class Application {

	public static void main(String[] args) {
		Model model = new Model();
		Frame frame = new Frame();
		frame.getView().setModel(model);
		model.addPropertyChangeListener(frame);
		Controller controller = new Controller(model, frame);
		frame.setController(controller);
		frame.setSize(1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
