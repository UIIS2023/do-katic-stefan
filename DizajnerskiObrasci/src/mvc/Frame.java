package mvc;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.border.SoftBevelBorder;

import command.Command;
import command.Operation;
import geometry.Shape;

import javax.swing.border.BevelBorder;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EtchedBorder;

public class Frame extends JFrame implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private View view = new View();
	private final ButtonGroup btngrCmdOperations = new ButtonGroup();
	
	private Operation selectedOperation;
	private JButton buttonEdit, buttonDelete, buttonBringToFront, buttonBringToBack, buttonToFront, buttonToBack;
	private JButton buttonUndo, buttonRedo;
	private JButton buttonBorder, buttonFill;
	private JButton buttonNext;
	
	private JList<Command> listCmds;
	private JList<Command> listUndoCmds; 
	
	public Frame() {
		setTitle("Stefan Katic IT55-2017");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		topPanel.add(menuBar, BorderLayout.NORTH);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.newDrawing();
			}
		});
		mnFile.add(mntmNew);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.saveDrawing();
				} catch (ClassNotFoundException e1) {
					System.out.println("ERROR during save, class not found");
					e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("ERROR during save, file input/output error");
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmLoadDrawing = new JMenuItem("Load drawing");
		mntmLoadDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.loadDrawing();
				} catch (ClassNotFoundException e1) {
					System.out.println("ERROR during load, class not found");
					e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("ERROR during load, file input/output error");
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmLoadDrawing);
		
		JMenuItem mntmLoadCommandLog = new JMenuItem("Load command log");
		mntmLoadCommandLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.loadCommandLog();
				} catch (ClassNotFoundException e1) {
					System.out.println("ERROR during load, class not found");
					e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("ERROR during load, file input/output error");
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmLoadCommandLog);
		
		JPanel operationsPanel = new JPanel();
		topPanel.add(operationsPanel, BorderLayout.CENTER);
		GridBagLayout gbl_operationsPanel = new GridBagLayout();
		gbl_operationsPanel.columnWidths = new int[] {54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 0, 54, 0};
		gbl_operationsPanel.rowHeights = new int[] {0, 24};
		gbl_operationsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_operationsPanel.rowWeights = new double[]{0.0, 0.0};
		operationsPanel.setLayout(gbl_operationsPanel);
		
		JButton btnRedo = new JButton("Redo");
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		
		JButton btnToFront = new JButton("To Front");
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
			}
		});
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.shapeEdit();
			}
		});
		
		JLabel lblBorder = new JLabel("Border");
		GridBagConstraints gbc_lblBorder = new GridBagConstraints();
		gbc_lblBorder.insets = new Insets(0, 0, 5, 5);
		gbc_lblBorder.gridx = 6;
		gbc_lblBorder.gridy = 0;
		operationsPanel.add(lblBorder, gbc_lblBorder);
		
		JLabel lblNewLabel_1 = new JLabel("Fill");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 7;
		gbc_lblNewLabel_1.gridy = 0;
		operationsPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.fill = GridBagConstraints.BOTH;
		gbc_btnEdit.insets = new Insets(0, 0, 0, 5);
		gbc_btnEdit.gridx = 0;
		gbc_btnEdit.gridy = 1;
		operationsPanel.add(btnEdit, gbc_btnEdit);
		
		btnEdit.setEnabled(false);
		
		// Buttons Edit & Delete Observer References
		buttonEdit = btnEdit;
		
		JButton btnBringToFront = new JButton("Bring To Front");
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.shapeDelete();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.fill = GridBagConstraints.BOTH;
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 1;
		gbc_btnDelete.gridy = 1;
		operationsPanel.add(btnDelete, gbc_btnDelete);
		btnDelete.setEnabled(false);
		buttonDelete = btnDelete;
		GridBagConstraints gbc_btnBringToFront = new GridBagConstraints();
		gbc_btnBringToFront.fill = GridBagConstraints.BOTH;
		gbc_btnBringToFront.insets = new Insets(0, 0, 0, 5);
		gbc_btnBringToFront.gridx = 2;
		gbc_btnBringToFront.gridy = 1;
		operationsPanel.add(btnBringToFront, gbc_btnBringToFront);
		
		btnBringToFront.setEnabled(false);
		
		// Buttons BringToFront, BringToBack, ToFront, ToBack Observer References
		buttonBringToFront = btnBringToFront;
		
		JButton btnBringToBack = new JButton("Bring To Back");
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		GridBagConstraints gbc_btnBringToBack = new GridBagConstraints();
		gbc_btnBringToBack.fill = GridBagConstraints.BOTH;
		gbc_btnBringToBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBringToBack.gridx = 3;
		gbc_btnBringToBack.gridy = 1;
		operationsPanel.add(btnBringToBack, gbc_btnBringToBack);
		btnBringToBack.setEnabled(false);
		buttonBringToBack = btnBringToBack;
		GridBagConstraints gbc_btnToFront = new GridBagConstraints();
		gbc_btnToFront.fill = GridBagConstraints.BOTH;
		gbc_btnToFront.insets = new Insets(0, 0, 0, 5);
		gbc_btnToFront.gridx = 4;
		gbc_btnToFront.gridy = 1;
		operationsPanel.add(btnToFront, gbc_btnToFront);
		btnToFront.setEnabled(false);
		buttonToFront = btnToFront;
		
		JButton btnFillColor = new JButton("");
		btnFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newFill = JColorChooser.showDialog(null, "Odaberi boju unutrašnjosti", controller.getFillColor());
				if ((newFill != controller.getFillColor()) && (newFill != null))
					controller.setFillColor(newFill);
			}
		});
		
		JButton btnToBack = new JButton("To Back");
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		GridBagConstraints gbc_btnToBack = new GridBagConstraints();
		gbc_btnToBack.fill = GridBagConstraints.BOTH;
		gbc_btnToBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnToBack.gridx = 5;
		gbc_btnToBack.gridy = 1;
		operationsPanel.add(btnToBack, gbc_btnToBack);
		btnToBack.setEnabled(false);
		buttonToBack = btnToBack;
		
		JButton btnBorderColor = new JButton("");
		btnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newBorder = JColorChooser.showDialog(null, "Odaberi boju ivice", controller.getBorderColor());
				if ((newBorder != controller.getBorderColor()) && (newBorder != null))
					controller.setBorderColor(newBorder);
			}
		});
		btnBorderColor.setOpaque(true);
		btnBorderColor.setBackground(Color.BLACK);
		GridBagConstraints gbc_btnBorderColor = new GridBagConstraints();
		gbc_btnBorderColor.fill = GridBagConstraints.BOTH;
		gbc_btnBorderColor.insets = new Insets(0, 0, 0, 5);
		gbc_btnBorderColor.gridx = 6;
		gbc_btnBorderColor.gridy = 1;
		operationsPanel.add(btnBorderColor, gbc_btnBorderColor);
		
		buttonBorder = btnBorderColor;
		btnFillColor.setOpaque(true);
		btnFillColor.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnFillColor = new GridBagConstraints();
		gbc_btnFillColor.fill = GridBagConstraints.BOTH;
		gbc_btnFillColor.insets = new Insets(0, 0, 0, 5);
		gbc_btnFillColor.gridx = 7;
		gbc_btnFillColor.gridy = 1;
		operationsPanel.add(btnFillColor, gbc_btnFillColor);
		buttonFill = btnFillColor;
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.undo();
			}
		});
		GridBagConstraints gbc_btnUndo = new GridBagConstraints();
		gbc_btnUndo.fill = GridBagConstraints.BOTH;
		gbc_btnUndo.insets = new Insets(0, 0, 0, 5);
		gbc_btnUndo.gridx = 8;
		gbc_btnUndo.gridy = 1;
		operationsPanel.add(btnUndo, gbc_btnUndo);
		
		btnUndo.setEnabled(false);
		
		// Buttons Undo, Redo Observer References
		buttonUndo = btnUndo;
		GridBagConstraints gbc_btnRedo = new GridBagConstraints();
		gbc_btnRedo.insets = new Insets(0, 0, 0, 5);
		gbc_btnRedo.fill = GridBagConstraints.BOTH;
		gbc_btnRedo.gridx = 9;
		gbc_btnRedo.gridy = 1;
		operationsPanel.add(btnRedo, gbc_btnRedo);
		btnRedo.setEnabled(false);
		buttonRedo = btnRedo;
		
		JButton btnNext = new JButton("Next");
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.next();
			}
		});
		buttonNext = btnNext;
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridx = 10;
		gbc_btnNext.gridy = 1;
		operationsPanel.add(btnNext, gbc_btnNext);
		
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				controller.canvasPressed(arg0);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				controller.canvasReleased(arg0);
			}
		});
		getContentPane().add(view);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel cmdOperationsPanel = new JPanel();
		leftPanel.add(cmdOperationsPanel, BorderLayout.CENTER);
		GridBagLayout gbl_cmdOperationsPanel = new GridBagLayout();
		gbl_cmdOperationsPanel.columnWidths = new int[]{81, 0};
		gbl_cmdOperationsPanel.rowHeights = new int[] {5, 20, 5, 5, 5, 5, 5, 5, 0};
		gbl_cmdOperationsPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_cmdOperationsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		cmdOperationsPanel.setLayout(gbl_cmdOperationsPanel);
		
		JToggleButton tglbtnCircle = new JToggleButton("Circle");
		tglbtnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSelectedOperation(Operation.CIRCLE);
			}
		});
		
		JToggleButton tglbtnLine = new JToggleButton("Line");
		tglbtnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSelectedOperation(Operation.LINE);
			}
		});
		
		JToggleButton tglbtnPoint = new JToggleButton("Point");
		tglbtnPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSelectedOperation(Operation.POINT);
			}
		});
		
		JToggleButton tglbtnSelect = new JToggleButton("Select");
		tglbtnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSelectedOperation(Operation.SELECT);
			}
		});
		btngrCmdOperations.add(tglbtnSelect);
		GridBagConstraints gbc_tglbtnSelect = new GridBagConstraints();
		gbc_tglbtnSelect.fill = GridBagConstraints.BOTH;
		gbc_tglbtnSelect.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnSelect.gridx = 0;
		gbc_tglbtnSelect.gridy = 0;
		cmdOperationsPanel.add(tglbtnSelect, gbc_tglbtnSelect);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		cmdOperationsPanel.add(label, gbc_label);
		btngrCmdOperations.add(tglbtnPoint);
		GridBagConstraints gbc_tglbtnPoint = new GridBagConstraints();
		gbc_tglbtnPoint.fill = GridBagConstraints.BOTH;
		gbc_tglbtnPoint.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnPoint.gridx = 0;
		gbc_tglbtnPoint.gridy = 2;
		cmdOperationsPanel.add(tglbtnPoint, gbc_tglbtnPoint);
		btngrCmdOperations.add(tglbtnLine);
		GridBagConstraints gbc_tglbtnLine = new GridBagConstraints();
		gbc_tglbtnLine.fill = GridBagConstraints.BOTH;
		gbc_tglbtnLine.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnLine.gridx = 0;
		gbc_tglbtnLine.gridy = 3;
		cmdOperationsPanel.add(tglbtnLine, gbc_tglbtnLine);
		
		JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
		tglbtnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSelectedOperation(Operation.RECTANGLE);
			}
		});
		btngrCmdOperations.add(tglbtnRectangle);
		GridBagConstraints gbc_tglbtnRectangle = new GridBagConstraints();
		gbc_tglbtnRectangle.anchor = GridBagConstraints.WEST;
		gbc_tglbtnRectangle.fill = GridBagConstraints.VERTICAL;
		gbc_tglbtnRectangle.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnRectangle.gridx = 0;
		gbc_tglbtnRectangle.gridy = 4;
		cmdOperationsPanel.add(tglbtnRectangle, gbc_tglbtnRectangle);
		btngrCmdOperations.add(tglbtnCircle);
		GridBagConstraints gbc_tglbtnCircle = new GridBagConstraints();
		gbc_tglbtnCircle.fill = GridBagConstraints.BOTH;
		gbc_tglbtnCircle.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnCircle.gridx = 0;
		gbc_tglbtnCircle.gridy = 5;
		cmdOperationsPanel.add(tglbtnCircle, gbc_tglbtnCircle);
		
		JToggleButton tglbtnDonut = new JToggleButton("Donut");
		tglbtnDonut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSelectedOperation(Operation.DONUT);
			}
		});
		btngrCmdOperations.add(tglbtnDonut);
		GridBagConstraints gbc_tglbtnDonut = new GridBagConstraints();
		gbc_tglbtnDonut.fill = GridBagConstraints.BOTH;
		gbc_tglbtnDonut.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnDonut.gridx = 0;
		gbc_tglbtnDonut.gridy = 6;
		cmdOperationsPanel.add(tglbtnDonut, gbc_tglbtnDonut);
		
		JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");
		tglbtnHexagon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSelectedOperation(Operation.HEXAGON);
			}
		});
		btngrCmdOperations.add(tglbtnHexagon);
		GridBagConstraints gbc_tglbtnHexagon = new GridBagConstraints();
		gbc_tglbtnHexagon.fill = GridBagConstraints.BOTH;
		gbc_tglbtnHexagon.gridx = 0;
		gbc_tglbtnHexagon.gridy = 7;
		cmdOperationsPanel.add(tglbtnHexagon, gbc_tglbtnHexagon);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new GridLayout(4, 1, 0, -10));
		
		JLabel lblCommands = new JLabel("Commands");
		bottomPanel.add(lblCommands);
		
		JList<Command> listCommands = new JList<Command>();
		listCommands.setVisibleRowCount(3);
		setListCmds(listCommands);
		
		JScrollPane scrollPaneCmds = new JScrollPane(listCommands);
		scrollPaneCmds.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCmds.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		bottomPanel.add(scrollPaneCmds);
		
		JLabel lblUndoCommands = new JLabel("Undos");
		bottomPanel.add(lblUndoCommands);
		
		JList<Command> listUndoCommands = new JList<Command>();
		listUndoCommands.setVisibleRowCount(3);
		setListUndoCmds(listUndoCommands);
		
		JScrollPane scrollPaneUndoCmds = new JScrollPane(listUndoCommands);
		scrollPaneUndoCmds.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneUndoCmds.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		bottomPanel.add(scrollPaneUndoCmds);
	}
	
	public View getView() {
		return view;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Operation getSelectedOperation() {
		return selectedOperation;
	}

	public void setSelectedOperation(Operation selectedOperation) {
		this.selectedOperation = selectedOperation;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "selectedShapes") {
			ArrayList<Shape> selectedShapes = (ArrayList<Shape>) evt.getNewValue();
			if (selectedShapes.size() == 1) {
				buttonEdit.setEnabled(true);
				if (controller.getShapes().indexOf(selectedShapes.get(0)) == 0) {
					buttonToFront.setEnabled(true);
					buttonBringToFront.setEnabled(true);
					buttonToBack.setEnabled(false);
					buttonBringToBack.setEnabled(false);
				}
				else if (controller.getShapes().indexOf(selectedShapes.get(0)) > 0 && controller.getShapes().indexOf(selectedShapes.get(0)) < controller.getShapes().size()-1) {
					buttonToFront.setEnabled(true);
					buttonBringToFront.setEnabled(true);
					buttonToBack.setEnabled(true);
					buttonBringToBack.setEnabled(true);
				}
				else if (controller.getShapes().indexOf(selectedShapes.get(0)) == controller.getShapes().size()-1) {
					buttonToFront.setEnabled(false);
					buttonBringToFront.setEnabled(false);
					buttonToBack.setEnabled(true);
					buttonBringToBack.setEnabled(true);
				}
			} else {
				buttonEdit.setEnabled(false); 
				buttonBringToFront.setEnabled(false);
				buttonBringToBack.setEnabled(false);
				buttonToFront.setEnabled(false);
				buttonToBack.setEnabled(false);
			}
			if (selectedShapes.size() >= 1) {
				buttonDelete.setEnabled(true);
			} else { 
				buttonDelete.setEnabled(false); 
			}
		} 
		if (evt.getPropertyName() == "enableUndo") {
			buttonUndo.setEnabled(true);
		}
		if (evt.getPropertyName() == "enableRedo") {
			buttonRedo.setEnabled(true);
		}
		if (evt.getPropertyName() == "disableUndo") {
			buttonUndo.setEnabled(false);
		}
		if (evt.getPropertyName() == "disableRedo") {
			buttonRedo.setEnabled(false);
		}
		if (evt.getPropertyName() == "borderColor") {
			buttonBorder.setBackground(controller.getBorderColor());
		}
		if (evt.getPropertyName() == "fillColor") {
			buttonFill.setBackground(controller.getFillColor());
		}
		if (evt.getPropertyName() == "enableNext") {
			buttonNext.setEnabled(true);
		}
		if (evt.getPropertyName() == "disableNext") {
			buttonNext.setEnabled(false);
		}
	}

	public JList<Command> getListCmds() {
		return listCmds;
	}

	public void setListCmds(JList<Command> listCmds) {
		this.listCmds = listCmds;
	}

	public JList<Command> getListUndoCmds() {
		return listUndoCmds;
	}

	public void setListUndoCmds(JList<Command> listUndoCmds) {
		this.listUndoCmds = listUndoCmds;
	}
}