package mvc;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

import adapter.HexagonAdapter;
import command.*;
import dialogs.DialogCircle;
import dialogs.DialogDonut;
import dialogs.DialogHexagon;
import dialogs.DialogLine;
import dialogs.DialogPoint;
import dialogs.DialogRectangle;
import geometry.*;
import strategy.LoadCommandLog;
import strategy.LoadShapes;
import strategy.LoadStrategyManager;
import strategy.SaveCommandLog;
import strategy.SaveShapes;
import strategy.SaveStrategyManager;

public class Controller {

	private Model model;
	private Frame frame;
	
	private Point firstLinePoint;
	
	private Color borderColor = Color.BLACK, fillColor = Color.WHITE;
	
	private DefaultListModel<Command> cmdDLM = new DefaultListModel<Command>();
	private DefaultListModel<Command> undoDLM = new DefaultListModel<Command>();
	private CommandManager cmdMgr;
	private List<String> loadedLog;
	private int nextPointerIndex;
	
	public Controller(Model model, Frame frame) {
		this.model = model;
		this.frame = frame;
		frame.getListCmds().setModel(getCmdDLM());
		frame.getListUndoCmds().setModel(getUndoDLM());
		cmdMgr = new CommandManager(this);
	}
	
	public void canvasPressed(MouseEvent arg0) {
		try {
			if (frame.getSelectedOperation() != null) {
				selectedOperation(arg0, frame.getSelectedOperation());
				
				frame.getView().repaint();
			}
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}		
	}
	
	private void selectedOperation(MouseEvent arg0, Operation operation) {
		operation.apply(arg0, this.model, this);
		frame.getView().repaint();
	}

	public void canvasReleased(MouseEvent arg0) {
		try {
			if (getFirstLinePoint() != null) {
				Line line = new Line(getFirstLinePoint(), new Point(arg0.getX(), arg0.getY()));
				DialogLine dl = new DialogLine(getBorderColor());
				dl.drawDialog(line);
				dl.setVisible(true);
				
				if (dl.isSuccessful()) {
					CmdShapeAdd cmd = new CmdShapeAdd(model, dl.getLine());
					cmd.execute();
					pushCommand(cmd);
				}
				setFirstLinePoint(null);
				
				frame.getView().repaint();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void shapeDelete() {
		try {
			List<Shape> shapes = model.getSelectedShapes();
			CmdBulkShapeRemove cmd = new CmdBulkShapeRemove(model, shapes);
			cmd.execute();
			pushCommand(cmd);	
			
			frame.getView().repaint();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void shapeEdit() {
		try {
			if (model.getSelectedShapes().size() == 1) {
				Shape shape = model.getSelectedShapes().get(0);
				int index = model.getIndex(shape);
				if (shape instanceof Point) {
					DialogPoint dp = new DialogPoint();
					dp.modifyDialog((Point) shape);
					dp.setVisible(true);
					
					if (dp.isSuccessful()) {
						CmdShapeUnselect unselectCmd = new CmdShapeUnselect(model, shape);
						unselectCmd.execute();
						
						CmdPointEdit cmd = new CmdPointEdit((Point) model.get(index), dp.getPoint());
						cmd.execute();

						CmdShapeSelect selectCmd = new CmdShapeSelect(model, shape);
						selectCmd.execute();
						
						pushCommand(cmd);
					}
				} else if (shape instanceof Line) {
					DialogLine dl = new DialogLine();
					dl.modifyDialog((Line) shape);
					dl.setVisible(true);
					
					if (dl.isSuccessful()) {
						CmdShapeUnselect unselectCmd = new CmdShapeUnselect(model, shape);
						unselectCmd.execute();

						CmdLineEdit cmd = new CmdLineEdit((Line) model.get(index), dl.getLine());
						cmd.execute();
						
						CmdShapeSelect selectCmd = new CmdShapeSelect(model, shape);
						selectCmd.execute();
						
						pushCommand(cmd);
					}
				}
				else if (shape instanceof Rectangle) {
					DialogRectangle dr = new DialogRectangle();
					dr.modifyDialog((Rectangle) shape);
					dr.setVisible(true);
					
					if (dr.isSuccessful()) {
						CmdShapeUnselect unselectCmd = new CmdShapeUnselect(model, shape);
						unselectCmd.execute();

						CmdRectangleEdit cmd = new CmdRectangleEdit((Rectangle) model.get(index), dr.getRectangle());
						cmd.execute();
						
						CmdShapeSelect selectCmd = new CmdShapeSelect(model, shape);
						selectCmd.execute();
						
						pushCommand(cmd);
					}
				} else if (shape instanceof Donut) {
					Donut donut = (Donut) shape;
					DialogDonut dd = new DialogDonut();
					dd.modifyDialog(donut);
					dd.setVisible(true);
					
					if (dd.isSuccessful()) {
						CmdShapeUnselect unselectCmd = new CmdShapeUnselect(model, shape);
						unselectCmd.execute();

						CmdDonutEdit cmd = new CmdDonutEdit((Donut) model.get(index), dd.getDonut());
						cmd.execute();
						
						CmdShapeSelect selectCmd = new CmdShapeSelect(model, shape);
						selectCmd.execute();
						
						pushCommand(cmd);
					}	
				} else if (shape instanceof Circle) {
					Circle circle = (Circle) shape;
					DialogCircle dc = new DialogCircle();
					dc.modifyDialog(circle);
					dc.setVisible(true);
					
					if (dc.isSuccessful()) {
						CmdShapeUnselect unselectCmd = new CmdShapeUnselect(model, shape);
						unselectCmd.execute();

						CmdCircleEdit cmd = new CmdCircleEdit((Circle) model.get(index), dc.getCircle());
						cmd.execute();
						
						CmdShapeSelect selectCmd = new CmdShapeSelect(model, shape);
						selectCmd.execute();
						
						pushCommand(cmd);
					}
				} else if (shape instanceof HexagonAdapter) {
					HexagonAdapter hexagon = (HexagonAdapter) shape;
					DialogHexagon dh = new DialogHexagon();
					dh.modifyDialog(hexagon);
					dh.setVisible(true);
					
					if (dh.isSuccessful()) {
						CmdShapeUnselect unselectCmd = new CmdShapeUnselect(model, shape);
						unselectCmd.execute();
						
						CmdHexagonEdit cmd = new CmdHexagonEdit((HexagonAdapter) model.get(index), dh.getHexagon());
						cmd.execute();
						
						CmdShapeSelect selectCmd = new CmdShapeSelect(model, (HexagonAdapter) shape);
						selectCmd.execute();
						
						pushCommand(cmd);
					}
				}
				frame.getView().repaint();
			}
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void bringToFront() {
		try {
			if (model.getSelectedShapes().size() == 1) {
				CmdShapeBringToFront cmd = new CmdShapeBringToFront(model, model.getSelectedShapes().get(0));
				cmd.execute();
				pushCommand(cmd);
				
				frame.getView().repaint();
			}			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void bringToBack() {
		try {
			if (model.getSelectedShapes().size() == 1) {
				CmdShapeBringToBack cmd = new CmdShapeBringToBack(model, model.getSelectedShapes().get(0));
				cmd.execute();
				pushCommand(cmd);
				
				frame.getView().repaint();
			}			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void toFront() {
		try {
			if (model.getSelectedShapes().size() == 1) {
				CmdShapeToFront cmd = new CmdShapeToFront(model, model.getSelectedShapes().get(0));
				cmd.execute();
				pushCommand(cmd);
				
				frame.getView().repaint();
			}			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void toBack() {
		try {
			if (model.getSelectedShapes().size() == 1) {
				CmdShapeToBack cmd = new CmdShapeToBack(model, model.getSelectedShapes().get(0));
				cmd.execute();
				pushCommand(cmd);
				
				frame.getView().repaint();
			}			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void undo() {
		try {
			cmdMgr.undo();
			frame.getView().repaint();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}	
	}

	public void redo() {
		try {
			cmdMgr.redo();
			frame.getView().repaint();			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public Color getBorderColor() {
		if (borderColor != null)
			return borderColor;
		
		return Color.BLACK;
	}
	
	public void setBorderColor(Color border) {
		Color oldBorder = this.borderColor;
		this.borderColor = border;
		
		model.getSupport().firePropertyChange("borderColor", oldBorder, this.borderColor);
	}
	
	public Color getFillColor() {
		if (fillColor != null)
			return fillColor;
				
		return Color.WHITE;
	}

	public void setFillColor(Color fill) {
		Color oldFill = this.fillColor;
		this.fillColor = fill;
		
		model.getSupport().firePropertyChange("fillColor", oldFill, this.fillColor);
	}
	
	public void newDrawing() {
		List<Shape> oldSelectedShapes = new ArrayList<Shape>(model.getSelectedShapes());
		model.getShapes().clear();
		model.getSelectedShapes().clear();
		model.getSupport().firePropertyChange("selectedShapes", oldSelectedShapes, model.getSelectedShapes());
		getCmdMgr().clear();
		model.getSupport().firePropertyChange("disableUndo", false, true);
		model.getSupport().firePropertyChange("disableRedo", false, true);
		model.getSupport().firePropertyChange("disableNext", false, true);
		
		frame.getView().repaint();
	}

	public void saveDrawing() throws ClassNotFoundException, IOException {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Choose folder and file name");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				SaveStrategyManager strategy = new SaveStrategyManager();
				SaveCommandLog saveCommandLog = new SaveCommandLog();
				SaveShapes saveShapes = new SaveShapes();
				
				saveCommandLog.setCommandLog(getCmdMgr().getLog());
				strategy.setStrategy(saveCommandLog);
				strategy.saveToFile(fileChooser.getSelectedFile().getAbsolutePath() + ".txt");
				
				saveShapes.setShapes(model.getShapes());
				strategy.setStrategy(saveShapes);
				strategy.saveToFile(fileChooser.getSelectedFile().getAbsolutePath() + ".bin");
				
			}
		} catch (IOException e) {
			System.out.println("File error" + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found error" + e.getMessage());
		}
	}

	public void loadDrawing() throws ClassNotFoundException, IOException {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Choose folder and file name");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				File binFile = new File(fileChooser.getSelectedFile().toString());
				
				if (binFile.exists()) {
					newDrawing();
					
					LoadStrategyManager strategy = new LoadStrategyManager();
					LoadShapes loadShapes = new LoadShapes(model);
					
					strategy.setStrategy(loadShapes);
					strategy.loadFile(fileChooser.getSelectedFile().getAbsolutePath());
				} else {
					throw new IOException("File not found.");
				}
			}
			frame.getView().repaint();
		} catch (IOException e) {
			System.out.println("File Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found error" + e.getMessage());
		}
	}
	
	public void loadCommandLog() throws IOException, ClassNotFoundException {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Choose folder and file name");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				File txtFile = new File(fileChooser.getSelectedFile().toString());
				
				if (txtFile.exists()) {
					newDrawing();
					
					nextPointerIndex = 0;
					loadedLog = new ArrayList<String>();
					
					LoadStrategyManager strategy = new LoadStrategyManager();
					LoadCommandLog loadCommandLog = new LoadCommandLog(this);
					
					strategy.setStrategy(loadCommandLog);
					strategy.loadFile(fileChooser.getSelectedFile().getAbsolutePath());

					model.getSupport().firePropertyChange("enableNext", false, true);
				} else {
					throw new IOException("File not found.");
				}
			}
			frame.getView().repaint();
		} catch (IOException e) {
			System.out.println("File Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found error" + e.getMessage());
		}
	}

	public List<Shape> getShapes() {
		return model.getShapes();
	}
	
	public Point getFirstLinePoint() {
		return firstLinePoint;
	}

	public void setFirstLinePoint(Point firstLinePoint) {
		this.firstLinePoint = firstLinePoint;
	}
	
	public void pushCommand(Command cmd) {
		this.cmdMgr.pushCommand(cmd);
	}

	public CommandManager getCmdMgr() {
		return cmdMgr;
	}

	public void setCmdMgr(CommandManager cmdMgr) {
		this.cmdMgr = cmdMgr;
	}

	public DefaultListModel<Command> getCmdDLM() {
		return cmdDLM;
	}
	
	public void setCmdDLM(DefaultListModel<Command> cmdDLM) {
		this.cmdDLM = cmdDLM;
	}

	public DefaultListModel<Command> getUndoDLM() {
		return undoDLM;
	}

	public void setUndoDLM(DefaultListModel<Command> undoDLM) {
		this.undoDLM = undoDLM;
	}
	
	public PropertyChangeSupport getSupport() {
		return model.getSupport();
	}

	public void next() {
		readLogLine();
		nextPointerIndex++;
		if (nextPointerIndex >= loadedLog.size())
			model.getSupport().firePropertyChange("disableNext", false,true);
		frame.getView().repaint();
	}

	private void readLogLine() {
    	Command cmd = null;
    	String row = loadedLog.get(nextPointerIndex);
    	String[] lineSeparated = row.split(" ");
    	Shape shape = null;
    	List<Shape> shapes = null;
		Color border;
		Color fill;
    	switch (lineSeparated[0]) {
	    	case "ADD":
    			switch (lineSeparated[1]) {
	    			case "POINT": 
	    				// 0	1		2		3		4
	    				// ADD 	POINT 	(x,y) 	BORDER 	java.awt.Color[r=0,g=0,b=0]
	    				Integer[] coords = extractCoordinatesFromString(lineSeparated[2]);
	    				border = extractColorFromString(lineSeparated[4]);
	    				shape = new Point(coords[0], coords[1], border);
	    				break;
	    			case "LINE": 		    				
	    				// 0	1		2			3			4			5			6		7
	    				// ADD 	LINE 	START_POINT (135,149) 	END_POINT 	(427,218) 	BORDER 	java.awt.Color[r=0,g=0,b=0]
	    				Integer[] coordsStartPoint = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer[] coordsEndPoint = extractCoordinatesFromString(lineSeparated[5]);
	    				border = extractColorFromString(lineSeparated[7]);
	    				shape = new Line(new Point(coordsStartPoint[0], coordsStartPoint[1]), new Point(coordsEndPoint[0], coordsEndPoint[1]), border);
	    				break;
	    			case "RECTANGLE": 
	    				// 0	1			2					3			4		5	6		7	8		9							10		11
	    				// ADD 	RECTANGLE 	UPPER_LEFT_POINT 	(506,91) 	WIDTH 	55 	HEIGHT 	55 	BORDER 	java.awt.Color[r=0,g=0,b=0] FILL 	java.awt.Color[r=255,g=255,b=255]
	    				Integer[] coordsUpperLeftPoint = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer width = Integer.parseInt(lineSeparated[5]);
	    				Integer height = Integer.parseInt(lineSeparated[7]);
	    				border = extractColorFromString(lineSeparated[9]);
	    				fill = extractColorFromString(lineSeparated[11]);
	    				shape = new Rectangle(new Point(coordsUpperLeftPoint[0], coordsUpperLeftPoint[1]), height, width, border, fill);
	    				break;
	    			case "CIRCLE": 
	    				// 0	1		2		3			4		5	6		7							8		9
	    				// ADD 	CIRCLE 	CENTER 	(709,119) 	RADIUS 	55 	BORDER 	java.awt.Color[r=0,g=0,b=0] FILL 	java.awt.Color[r=255,g=255,b=255]
	    				Integer[] coordsCenter = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer radius = Integer.parseInt(lineSeparated[5]);
	    				border = extractColorFromString(lineSeparated[7]);
	    				fill = extractColorFromString(lineSeparated[9]);
	    				shape = new Circle(new Point(coordsCenter[0], coordsCenter[1]), radius, border, fill);
	    				break;
	    			case "DONUT": 
	    				//	0	1		2		3			4		5	6				7	8		9							10		11
	    				// 	ADD DONUT 	CENTER 	(713,303) 	RADIUS 	55 	INNER_RADIUS 	44 	BORDER 	java.awt.Color[r=0,g=0,b=0]	FILL 	java.awt.Color[r=255,g=255,b=255]
	    				Integer[] coordsDonutCenter = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer donutRadius = Integer.parseInt(lineSeparated[5]);
	    				Integer donutInnerRadius = Integer.parseInt(lineSeparated[7]);
	    				border = extractColorFromString(lineSeparated[9]);
	    				fill = extractColorFromString(lineSeparated[11]);
	    				shape = new Donut(new Point(coordsDonutCenter[0], coordsDonutCenter[1]), donutRadius, donutInnerRadius, border, fill);
	    				break;
	    			case "HEXAGON": 
	    				//	0	1		2		3			4		5	6		7							8		9
	    				//	ADD	HEXAGON	CENTER 	(910,272) 	RADIUS 	33 	BORDER 	java.awt.Color[r=0,g=0,b=0] FILL 	java.awt.Color[r=255,g=255,b=255]
	    				Integer[] coordsHexagonCenter = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer hexagonRadius = Integer.parseInt(lineSeparated[5]);
	    				border = extractColorFromString(lineSeparated[7]);
	    				fill = extractColorFromString(lineSeparated[9]);
	    				shape = new HexagonAdapter(new Point(coordsHexagonCenter[0], coordsHexagonCenter[1]), hexagonRadius, border, fill);
	    				break;
    			}
    			if (shape != null) {
    				cmd = new CmdShapeAdd(model, shape);
    				cmd.execute();
    				pushCommand(cmd);	    				
    			}
    			break;
    		case "SELECT":
				switch (lineSeparated[1]) {
	    			case "POINT": 
	    				Integer[] coords = extractCoordinatesFromString(lineSeparated[2]);
	    				border = extractColorFromString(lineSeparated[4]);
	    				shape = new Point(coords[0], coords[1], border);
	    				break;
	    			case "LINE": 
	    				Integer[] coordsStartPoint = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer[] coordsEndPoint = extractCoordinatesFromString(lineSeparated[5]);
	    				border = extractColorFromString(lineSeparated[7]);
	    				shape = new Line(new Point(coordsStartPoint[0], coordsStartPoint[1]), new Point(coordsEndPoint[0], coordsEndPoint[1]), border);
	    				break;
	    			case "RECTANGLE": 
	    				Integer[] coordsUpperLeftPoint = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer width = Integer.parseInt(lineSeparated[5]);
	    				Integer height = Integer.parseInt(lineSeparated[7]);
	    				border = extractColorFromString(lineSeparated[9]);
	    				fill = extractColorFromString(lineSeparated[11]);
	    				shape = new Rectangle(new Point(coordsUpperLeftPoint[0], coordsUpperLeftPoint[1]), height, width, border, fill);
	    				break;
	    			case "CIRCLE": 
	    				Integer[] coordsCenter = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer radius = Integer.parseInt(lineSeparated[5]);
	    				border = extractColorFromString(lineSeparated[7]);
	    				fill = extractColorFromString(lineSeparated[9]);
	    				shape = new Circle(new Point(coordsCenter[0], coordsCenter[1]), radius, border, fill);
	    				break;
	    			case "DONUT": 
	    				Integer[] coordsDonutCenter = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer donutRadius = Integer.parseInt(lineSeparated[5]);
	    				Integer donutInnerRadius = Integer.parseInt(lineSeparated[7]);
	    				border = extractColorFromString(lineSeparated[9]);
	    				fill = extractColorFromString(lineSeparated[11]);
	    				shape = new Donut(new Point(coordsDonutCenter[0], coordsDonutCenter[1]), donutRadius, donutInnerRadius, border, fill);
	    				break;
	    			case "HEXAGON": 
	    				Integer[] coordsHexagonCenter = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer hexagonRadius = Integer.parseInt(lineSeparated[5]);
	    				border = extractColorFromString(lineSeparated[7]);
	    				fill = extractColorFromString(lineSeparated[9]);
	    				shape = new HexagonAdapter(new Point(coordsHexagonCenter[0], coordsHexagonCenter[1]), hexagonRadius, border, fill);
	    				break;
    			}
				if (shape != null) {
					cmd = new CmdShapeSelect(getModel(), matchSelection(shape));
					cmd.execute();
					pushCommand(cmd);    					
				}
				break;
    		case "UNSELECT": 
    			switch (lineSeparated[1]) {
	    			case "POINT": 
	    				Integer[] coords = extractCoordinatesFromString(lineSeparated[2]);
	    				border = extractColorFromString(lineSeparated[4]);
	    				shape = new Point(coords[0], coords[1], border);
	    				break;
	    			case "LINE": 
	    				Integer[] coordsStartPoint = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer[] coordsEndPoint = extractCoordinatesFromString(lineSeparated[5]);
	    				border = extractColorFromString(lineSeparated[7]);
	    				shape = new Line(new Point(coordsStartPoint[0], coordsStartPoint[1]), new Point(coordsEndPoint[0], coordsEndPoint[1]), border);
	    				break;
	    			case "RECTANGLE": 
	    				Integer[] coordsUpperLeftPoint = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer width = Integer.parseInt(lineSeparated[5]);
	    				Integer height = Integer.parseInt(lineSeparated[7]);
	    				border = extractColorFromString(lineSeparated[9]);
	    				fill = extractColorFromString(lineSeparated[11]);
	    				shape = new Rectangle(new Point(coordsUpperLeftPoint[0], coordsUpperLeftPoint[1]), height, width, border, fill);
	    				break;
	    			case "CIRCLE": 
	    				Integer[] coordsCenter = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer radius = Integer.parseInt(lineSeparated[5]);
	    				border = extractColorFromString(lineSeparated[7]);
	    				fill = extractColorFromString(lineSeparated[9]);
	    				shape = new Circle(new Point(coordsCenter[0], coordsCenter[1]), radius, border, fill);
	    				break;
	    			case "DONUT": 
	    				Integer[] coordsDonutCenter = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer donutRadius = Integer.parseInt(lineSeparated[5]);
	    				Integer donutInnerRadius = Integer.parseInt(lineSeparated[7]);
	    				border = extractColorFromString(lineSeparated[9]);
	    				fill = extractColorFromString(lineSeparated[11]);
	    				shape = new Donut(new Point(coordsDonutCenter[0], coordsDonutCenter[1]), donutRadius, donutInnerRadius, border, fill);
	    				break;
	    			case "HEXAGON": 
	    				Integer[] coordsHexagonCenter = extractCoordinatesFromString(lineSeparated[3]);
	    				Integer hexagonRadius = Integer.parseInt(lineSeparated[5]);
	    				border = extractColorFromString(lineSeparated[7]);
	    				fill = extractColorFromString(lineSeparated[9]);
	    				shape = new HexagonAdapter(new Point(coordsHexagonCenter[0], coordsHexagonCenter[1]), hexagonRadius, border, fill);
	    				break;
    			}
				if (shape != null) {
					cmd = new CmdShapeUnselect(getModel(), matchSelection(shape));
					cmd.execute();
					pushCommand(cmd);    					
				}
				break;
    		case "UNSELECT_ALL":
    			cmd = new CmdShapeUnselectAll(getModel());
    			cmd.execute();
    			pushCommand(cmd);
    			break;
    		case "REMOVE_BULK":
    			shapes = getShapesFromIndexes(row);
    			cmd = new CmdBulkShapeRemove(getModel(), shapes);
    			cmd.execute();
    			pushCommand(cmd);
    			break;
    		case "EDIT":
    			switch (lineSeparated[1]) {
	    			case "OLD_POINT": 
	    				Point[] points = getPointsFromLogEditString(row);
	    				cmd = new CmdPointEdit((Point) model.get(model.getIndex(points[0])), points[1]);
	    				break;
	    			case "OLD_LINE": 
	    				Line[] lines = getLinesFromLogEditString(row);
	    				cmd = new CmdLineEdit((Line) model.get(model.getIndex(lines[0])), lines[1]);
	    				break;
	    			case "OLD_RECTANGLE":
	    				Rectangle[] rectangles = getRectanglesFromLogEditString(row);
	    				cmd = new CmdRectangleEdit((Rectangle) model.get(model.getIndex(rectangles[0])),rectangles[1]);
	    				break;
	    			case "OLD_CIRCLE": 
	    				Circle[] circles = getCirclesFromLogEditString(row);
	    				cmd = new CmdCircleEdit((Circle) model.get(model.getIndex(circles[0])), circles[1]);
	    				break;
	    			case "OLD_DONUT": 
	    				Donut[] donuts = getDonutsFromLogEditString(row);
	    				cmd = new CmdDonutEdit((Donut) model.get(model.getIndex(donuts[0])), donuts[1]);
	    				break;
	    			case "OLD_HEXAGON": 
	    				HexagonAdapter[] hexagons = getHexagonsFromLogEditString(row);
	    				cmd = new CmdHexagonEdit((HexagonAdapter) model.get(model.getIndex(hexagons[0])), hexagons[1]);
	    				break;
    			}
    			if (cmd != null) {
        			cmd.execute();
    				pushCommand(cmd);	    				
    			}
    			break;
    		case "TO_FRONT":
    			toFront();
    			break;
    		case "TO_BACK":
    			toBack();
    			break;
    		case "BRING_TO_FRONT":
    			bringToFront();
    			break;
    		case "BRING_TO_BACK":
    			bringToBack();
    			break;
    		case "UNDO": 
    			undo();
    			break;
    		case "REDO": 
    			redo();
    			break;
    	}
	}
	
	private HexagonAdapter[] getHexagonsFromLogEditString(String str) {
		HexagonAdapter[] hexagons = new HexagonAdapter[2];
		String[] hexagonsStrings = str.substring(str.indexOf("OLD_HEXAGON HEXAGON ") + 20).split("NEW_HEXAGON HEXAGON ");
		for (int i = 0; i < hexagons.length; i++) {
			String[] hexagonStrings = hexagonsStrings[i].split(" ");
			// CENTER (931,163) RADIUS 33 BORDER java.awt.Color[r=0,g=0,b=0] FILL java.awt.Color[r=255,g=255,b=255]
			// 0		1		2		3	4		5							6	7
			Integer[] coords = extractCoordinatesFromString(hexagonStrings[1]);
			Integer radius = Integer.parseInt(hexagonStrings[3]);
			Color border = extractColorFromString(hexagonStrings[5]);
			Color fill = extractColorFromString(hexagonStrings[7]);
			hexagons[i] = new HexagonAdapter(new Point(coords[0], coords[1]), radius, border, fill);
		}
		return hexagons;
	}

	private Donut[] getDonutsFromLogEditString(String str) {
		Donut[] donuts = new Donut[2];
		String[] donutsStrings = str.substring(str.indexOf("OLD_DONUT DONUT ") + 16).split("NEW_DONUT DONUT ");
		for (int i = 0; i < donuts.length; i++) {
			String[] donutStrings = donutsStrings[i].split(" ");
			// CENTER (595,106) RADIUS 77 INNER_RADIUS 33 BORDER java.awt.Color[r=0,g=0,b=0] FILL java.awt.Color[r=255,g=255,b=255]
			//	0		1		2		3	4			5	6		7							8	9
			Integer[] coords = extractCoordinatesFromString(donutStrings[1]);
			Integer radius = Integer.parseInt(donutStrings[3]);
			Integer innerRadius = Integer.parseInt(donutStrings[5]);
			Color border = extractColorFromString(donutStrings[7]);
			Color fill = extractColorFromString(donutStrings[9]);
			donuts[i] = new Donut(new Point(coords[0], coords[1]), radius, innerRadius, border, fill);
		}
		return donuts;
	}

	private Circle[] getCirclesFromLogEditString(String str) {
		Circle[] circles = new Circle[2];
		String[] circlesStrings = str.substring(str.indexOf("OLD_CIRCLE CIRCLE ") + 18).split("NEW_CIRCLE CIRCLE ");
		for (int i = 0; i < circles.length; i++) {
			String[] circleStrings = circlesStrings[i].split(" ");
			// CENTER (432,148) RADIUS 55 BORDER java.awt.Color[r=0,g=0,b=0] FILL java.awt.Color[r=255,g=255,b=255] 
			// 		0	1		2		3	4		5							6	7
			Integer[] coords = extractCoordinatesFromString(circleStrings[1]);
			Integer radius = Integer.parseInt(circleStrings[3]);
			Color border = extractColorFromString(circleStrings[5]);
			Color fill = extractColorFromString(circleStrings[7]);
			circles[i] = new Circle(new Point(coords[0], coords[1]), radius, border, fill);
		}
		return circles;
	}

	private Rectangle[] getRectanglesFromLogEditString(String str) {
		Rectangle[] rectangles = new Rectangle[2];
		String[] rectanglesStrings = str.substring(str.indexOf("OLD_RECTANGLE RECTANGLE ") + 24).split("NEW_RECTANGLE RECTANGLE ");
		for (int i = 0; i < rectangles.length; i++) {
			String[] rectangleStrings = rectanglesStrings[i].split(" ");
			// UPPER_LEFT_POINT (170,128) WIDTH 77 HEIGHT 44 BORDER java.awt.Color[r=0,g=0,b=0] FILL java.awt.Color[r=255,g=255,b=255]
			Integer[] coords = extractCoordinatesFromString(rectangleStrings[1]);
			Integer width = Integer.parseInt(rectangleStrings[3]);
			Integer height = Integer.parseInt(rectangleStrings[5]);
			Color border = extractColorFromString(rectangleStrings[7]);
			Color fill = extractColorFromString(rectangleStrings[9]);
			rectangles[i] = new Rectangle(new Point(coords[0], coords[1]), height, width, border, fill);
		}
		return rectangles;
	}

	private Line[] getLinesFromLogEditString(String str) {
		Line[] lines = new Line[2];
		String[] linesStrings = str.substring(str.indexOf("OLD_LINE LINE ") + 14).split("NEW_LINE LINE ");
		for (int i = 0; i < lines.length; i++) {
			String[] lineStrings = linesStrings[i].split(" ");
			// START_POINT (22,22) END_POINT (44,44) BORDER java.awt.Color[r=0,g=0,b=0]
			Integer[] coordsStart = extractCoordinatesFromString(lineStrings[1]);
			Integer[] coordsEnd = extractCoordinatesFromString(lineStrings[3]);
			Color border = extractColorFromString(lineStrings[5]);
			lines[i] = new Line(new Point(coordsStart[0], coordsStart[1]), new Point(coordsEnd[0], coordsEnd[1]), border);
		}
		return lines;
	}
	
	private Point[] getPointsFromLogEditString(String str) {
		Point[] points = new Point[2];
		String[] pointsStrings = str.substring(str.indexOf("OLD_POINT POINT ") + 16).split("NEW_POINT POINT ");
		for (int i = 0; i < points.length; i++) {
			String[] pointStrings = pointsStrings[i].split(" ");
			Integer[] coords = extractCoordinatesFromString(pointStrings[0]);
			Color border = extractColorFromString(pointStrings[2]);
			points[i] = new Point(coords[0], coords[1], border);
		}
		return points;
	}
	
	private List<Shape> getShapesFromIndexes(String row) {
		// 0									?		
		// REMOVE_BULK SHAPES 	[Shape, Shape] 	INDEXES [6, 2]
		int index = row.indexOf("INDEXES");
		List<Shape> shapes = new ArrayList<Shape>();
		String[] shapesIndexes = row.substring(index).replace("INDEXES [", "").replace("]", "").replace(" ", "").split(",");
		for (int i = 0; i < shapesIndexes.length; i++) {
			shapes.add(getShapes().get(Integer.parseInt(shapesIndexes[i])));
		}
		return shapes;
	}
	
	private Integer[] extractCoordinatesFromString(String str) {
		String[] coords = str.replace("(", "").replace(")", "").split(",");
		Integer[] _coords = new Integer[2];
		_coords[0] = Integer.parseInt(coords[0]);
		_coords[1] = Integer.parseInt(coords[1]);
		return _coords;
	}
	
	private Color extractColorFromString(String string) {
		// remove java.awt.Color from string, 14 chars
		String[] rgb = string.substring(14) .split(",");
		// 0: [r=R 1: g=G 2: b=B]
		rgb[0] = rgb[0].substring(3);
		rgb[1] = rgb[1].substring(2);
		rgb[2] = rgb[2].substring(2).replace("]", "");
		Color color = new Color(Float.parseFloat(rgb[0]) / 255, Float.parseFloat(rgb[1]) / 255, Float.parseFloat(rgb[2]) / 255);
		return color;
	}

	private Shape matchSelection(Shape shape) {
		for (int i = getShapes().size() - 1; i >= 0; i--) {
			if (shape.equals(getShapes().get(i))) {
				return getShapes().get(i);
			}
		}
		return null;
	}
	
	public Model getModel() {
		return model;
	}

	public List<String> getLoadedLog() {
		return loadedLog;
	}

	public void setLoadedLog(List<String> loadedLog) {
		this.loadedLog = loadedLog;
	}
}
