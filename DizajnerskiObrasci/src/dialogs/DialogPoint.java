package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Point;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogPoint extends JDialog {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final JPanel contentPanel = new JPanel();
	private JTextField txtPointX;
	private JTextField txtPointY;

	private Color border = Color.BLACK;
	
	private boolean successful;
	private Point p = new Point();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogPoint dialog = new DialogPoint();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogPoint() {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Tačka");
		setBounds(100, 100, 250, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {0, 0};
		gbl_contentPanel.rowHeights = new int[] {0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblPointX = new JLabel("Tačka X:");
			lblPointX.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblPointX = new GridBagConstraints();
			gbc_lblPointX.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblPointX.insets = new Insets(0, 0, 5, 5);
			gbc_lblPointX.gridx = 0;
			gbc_lblPointX.gridy = 0;
			contentPanel.add(lblPointX, gbc_lblPointX);
		}
		{
			txtPointX = new JTextField();
			GridBagConstraints gbc_txtPointX = new GridBagConstraints();
			gbc_txtPointX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPointX.insets = new Insets(0, 0, 5, 0);
			gbc_txtPointX.gridx = 1;
			gbc_txtPointX.gridy = 0;
			contentPanel.add(txtPointX, gbc_txtPointX);
			txtPointX.setColumns(10);
		}
		{
			JLabel lblPointY = new JLabel("Tačka Y:");
			lblPointY.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblPointY = new GridBagConstraints();
			gbc_lblPointY.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblPointY.insets = new Insets(0, 0, 5, 5);
			gbc_lblPointY.gridx = 0;
			gbc_lblPointY.gridy = 1;
			contentPanel.add(lblPointY, gbc_lblPointY);
		}
		{
			txtPointY = new JTextField();
			GridBagConstraints gbc_txtPointY = new GridBagConstraints();
			gbc_txtPointY.insets = new Insets(0, 0, 5, 0);
			gbc_txtPointY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPointY.gridx = 1;
			gbc_txtPointY.gridy = 1;
			contentPanel.add(txtPointY, gbc_txtPointY);
			txtPointY.setColumns(10);
		}
		{
			JLabel lblBorderColor = new JLabel("Boja tačke:");
			GridBagConstraints gbc_lblBorderColor = new GridBagConstraints();
			gbc_lblBorderColor.insets = new Insets(0, 0, 0, 5);
			gbc_lblBorderColor.gridx = 0;
			gbc_lblBorderColor.gridy = 2;
			contentPanel.add(lblBorderColor, gbc_lblBorderColor);
		}
		{
			JButton btnBorderColor = new JButton("Izaberi");
			btnBorderColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					border = JColorChooser.showDialog(null, "Odaberite boju ivice", border);
				}
			});
			GridBagConstraints gbc_btnBorderColor = new GridBagConstraints();
			gbc_btnBorderColor.gridx = 1;
			gbc_btnBorderColor.gridy = 2;
			contentPanel.add(btnBorderColor, gbc_btnBorderColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							p.setX(Integer.parseInt(txtPointX.getText()));
							p.setY(Integer.parseInt(txtPointY.getText()));
							p.setBorder(border);
							//p.setSelected(true);
							setSuccessful(true);
							dispose();
						} catch (Exception exception) {
							JOptionPane.showMessageDialog(null, "Nepravilan unos parametara.", "Greška", 0);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public DialogPoint(Color border) {
		this();
		this.border = border;
	}
	
	public void drawDialog(Point point) {
		setTitle("Nacrtaj novu tačku");
		txtPointX.setText(Integer.toString(point.getX()));
		txtPointY.setText(Integer.toString(point.getY()));
		txtPointX.setEditable(false);
		txtPointY.setEditable(false);
	}
	
	public void modifyDialog(Point point) {
		setTitle("Izmeni tačku");
		txtPointX.setText(Integer.toString(point.getX()));
		txtPointY.setText(Integer.toString(point.getY()));
		this.border = point.getBorder();
	}

	public Point getPoint() {
		return this.p;
	}
	
	public boolean isSuccessful() {
		return this.successful;
	}
	
	public void setSuccessful(boolean bool) {
		this.successful = bool;
	}
}
