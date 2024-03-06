package command;

import java.awt.event.MouseEvent;

import dialogs.DialogCircle;
import dialogs.DialogDonut;
import dialogs.DialogHexagon;
import dialogs.DialogPoint;
import dialogs.DialogRectangle;
import geometry.Point;
import geometry.Shape;
import mvc.Controller;
import mvc.Model;

public enum Operation {
	SELECT {
		@Override
		public void apply(MouseEvent arg0, Model model, Controller controller) {
			try {
				if (model.getShapes().size() > 0) {
					int selected = 0;
					for (int i = model.getShapes().size() - 1; i >= 0; i--) {
						Shape shape = model.get(i);
						if (shape.contains(arg0.getX(), arg0.getY())) {
							selected++;
							// Selektovanje vec selektovanog oblika -> deselektovati ga
							if (shape.isSelected()) {
								CmdShapeUnselect cmd = new CmdShapeUnselect(model, shape);
								cmd.execute();
								controller.pushCommand(cmd);
							// Ukoliko nije selektovan, selektovati ga
							} else {
								CmdShapeSelect cmd = new CmdShapeSelect(model, shape);
								cmd.execute();
								controller.pushCommand(cmd);
							}
							break;
						}
					}
					if (selected == 0 && (model.getSelectedShapes().size() > 0)) {
						CmdShapeUnselectAll cmd = new CmdShapeUnselectAll(model);
						cmd.execute();
						controller.pushCommand(cmd);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	},
	POINT {
		@Override
		public void apply(MouseEvent arg0, Model model, Controller controller) {
			try {
				Point point = new Point(arg0.getX(), arg0.getY());
				DialogPoint dp = new DialogPoint(controller.getBorderColor());
				dp.drawDialog(point);
				dp.setVisible(true);
				
				if (dp.isSuccessful()) {
					CmdShapeAdd cmd = new CmdShapeAdd(model, dp.getPoint().clone());
					cmd.execute();
					controller.pushCommand(cmd);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	},
	LINE {
		@Override
		public void apply(MouseEvent arg0, Model model, Controller controller) {
			try {
				if (controller.getFirstLinePoint() == null)
					controller.setFirstLinePoint(new Point(arg0.getX(), arg0.getY()));
			} 
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	},
	RECTANGLE {
		@Override
		public void apply(MouseEvent arg0, Model model, Controller controller) {
			try {
				Point rectangleUpperLeftPoint = new Point(arg0.getX(), arg0.getY());
				DialogRectangle dr = new DialogRectangle(controller.getBorderColor(), controller.getFillColor());
				dr.drawDialog(rectangleUpperLeftPoint);
				dr.setVisible(true);
				
				if (dr.isSuccessful()) {
					CmdShapeAdd cmd = new CmdShapeAdd(model, dr.getRectangle().clone());
					cmd.execute();
					controller.pushCommand(cmd);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	},
	CIRCLE {
		@Override
		public void apply(MouseEvent arg0, Model model, Controller controller) {
			try {
				Point circleCenter = new Point(arg0.getX(), arg0.getY());
				DialogCircle dc = new DialogCircle(controller.getBorderColor(), controller.getFillColor());
				dc.drawDialog(circleCenter);
				dc.setVisible(true);
				
				if (dc.isSuccessful()) {
					CmdShapeAdd cmd = new CmdShapeAdd(model, dc.getCircle().clone());
					cmd.execute();
					controller.pushCommand(cmd);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	},
	DONUT {
		@Override
		public void apply(MouseEvent arg0, Model model, Controller controller) {
			try {
				Point donutCenter = new Point(arg0.getX(), arg0.getY());
				DialogDonut dd = new DialogDonut(controller.getBorderColor(), controller.getFillColor());
				dd.drawDialog(donutCenter);
				dd.setVisible(true);
				
				if (dd.isSuccessful()) {
					CmdShapeAdd cmd = new CmdShapeAdd(model, dd.getDonut().clone());
					cmd.execute();
					controller.pushCommand(cmd);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	},
	HEXAGON {
		@Override
		public void apply(MouseEvent arg0, Model model, Controller controller) {
			try {
				Point hexagonCenter = new Point(arg0.getX(), arg0.getY());
				DialogHexagon dh = new DialogHexagon(controller.getBorderColor(), controller.getFillColor());
				dh.drawDialog(hexagonCenter);
				dh.setVisible(true);
				
				if (dh.isSuccessful()) {
					CmdShapeAdd cmd = new CmdShapeAdd(model, dh.getHexagon().clone());
					cmd.execute();
					controller.pushCommand(cmd);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	};
	
	public abstract void apply(MouseEvent arg0, Model model, Controller controller);
}
