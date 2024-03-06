package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Line;
import geometry.Point;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogLine extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtStartY;
	private JTextField txtEndX;
	private JTextField txtEndY;
	private JTextField txtStartX;

	private boolean successful;
	private Line l = new Line();
	private Color border = Color.BLACK;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogLine dialog = new DialogLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogLine() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Linija");
		setBounds(100, 100, 300, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {180, 180};
		gbl_contentPanel.rowHeights = new int[] {31, 31, 31, 31, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblStartX = new JLabel("Početna tačka X:");
			lblStartX.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblStartX = new GridBagConstraints();
			gbc_lblStartX.insets = new Insets(0, 0, 5, 5);
			gbc_lblStartX.gridx = 0;
			gbc_lblStartX.gridy = 0;
			contentPanel.add(lblStartX, gbc_lblStartX);
		}
		{
			txtStartX = new JTextField();
			GridBagConstraints gbc_txtStartX = new GridBagConstraints();
			gbc_txtStartX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtStartX.insets = new Insets(0, 0, 5, 0);
			gbc_txtStartX.gridx = 1;
			gbc_txtStartX.gridy = 0;
			contentPanel.add(txtStartX, gbc_txtStartX);
			txtStartX.setColumns(10);
		}
		{
			JLabel lblStartY = new JLabel("Početna tačka Y:");
			lblStartY.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblStartY = new GridBagConstraints();
			gbc_lblStartY.insets = new Insets(0, 0, 5, 5);
			gbc_lblStartY.gridx = 0;
			gbc_lblStartY.gridy = 1;
			contentPanel.add(lblStartY, gbc_lblStartY);
		}
		{
			txtStartY = new JTextField();
			GridBagConstraints gbc_txtStartY = new GridBagConstraints();
			gbc_txtStartY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtStartY.insets = new Insets(0, 0, 5, 0);
			gbc_txtStartY.gridx = 1;
			gbc_txtStartY.gridy = 1;
			contentPanel.add(txtStartY, gbc_txtStartY);
			txtStartY.setColumns(10);
		}
		{
			JLabel lblEndX = new JLabel("Krajnja tačka X:");
			lblEndX.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblEndX = new GridBagConstraints();
			gbc_lblEndX.insets = new Insets(0, 0, 5, 5);
			gbc_lblEndX.gridx = 0;
			gbc_lblEndX.gridy = 2;
			contentPanel.add(lblEndX, gbc_lblEndX);
		}
		{
			txtEndX = new JTextField();
			GridBagConstraints gbc_txtEndX = new GridBagConstraints();
			gbc_txtEndX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEndX.insets = new Insets(0, 0, 5, 0);
			gbc_txtEndX.gridx = 1;
			gbc_txtEndX.gridy = 2;
			contentPanel.add(txtEndX, gbc_txtEndX);
			txtEndX.setColumns(10);
		}
		{
			JLabel lblEndY = new JLabel("Krajnja tačka Y:");
			lblEndY.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblEndY = new GridBagConstraints();
			gbc_lblEndY.insets = new Insets(0, 0, 5, 5);
			gbc_lblEndY.gridx = 0;
			gbc_lblEndY.gridy = 3;
			contentPanel.add(lblEndY, gbc_lblEndY);
		}
		{
			txtEndY = new JTextField();
			GridBagConstraints gbc_txtEndY = new GridBagConstraints();
			gbc_txtEndY.insets = new Insets(0, 0, 5, 0);
			gbc_txtEndY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEndY.gridx = 1;
			gbc_txtEndY.gridy = 3;
			contentPanel.add(txtEndY, gbc_txtEndY);
			txtEndY.setColumns(10);
		}
		{
			JLabel lblBorderColor = new JLabel("Boja linije:");
			GridBagConstraints gbc_lblBorderColor = new GridBagConstraints();
			gbc_lblBorderColor.insets = new Insets(0, 0, 0, 5);
			gbc_lblBorderColor.gridx = 0;
			gbc_lblBorderColor.gridy = 4;
			contentPanel.add(lblBorderColor, gbc_lblBorderColor);
		}
		{
			JButton btnBorderColor = new JButton("Izaberi");
			btnBorderColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					border  = JColorChooser.showDialog(null, "Odaberite boju ivice", border);
				}
			});
			GridBagConstraints gbc_btnBorderColor = new GridBagConstraints();
			gbc_btnBorderColor.gridx = 1;
			gbc_btnBorderColor.gridy = 4;
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
							l.setStartPoint(new Point(Integer.parseInt(txtStartX.getText()),Integer.parseInt(txtStartY.getText())) );
							l.setEndPoint(new Point(Integer.parseInt(txtEndX.getText()), Integer.parseInt(txtEndY.getText())) );
							l.setBorder(border);
							//l.setSelected(true);
							setSuccessful(true);
							dispose();
						} catch (Exception exc) {
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
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public DialogLine(Color border) {
		this();
		this.border = border;
	}
	
	public void drawDialog(Line line) {
		setTitle("Nacrtaj novu liniju");
		txtStartX.setText(Integer.toString(line.getStartPoint().getX()));
		txtStartY.setText(Integer.toString(line.getStartPoint().getY()));
		txtEndX.setText(Integer.toString(line.getEndPoint().getX()));
		txtEndY.setText(Integer.toString(line.getEndPoint().getY()));
		txtStartX.setEditable(false);
		txtStartY.setEditable(false);
		txtEndX.setEditable(false);
		txtEndY.setEditable(false);
	}

	public void modifyDialog(Line line) {
		txtStartX.setText(Integer.toString(line.getStartPoint().getX()));
		txtStartY.setText(Integer.toString(line.getStartPoint().getY()));
		txtEndX.setText(Integer.toString(line.getEndPoint().getX()));
		txtEndY.setText(Integer.toString(line.getEndPoint().getY()));
		this.border = line.getBorder();
	}

	public Line getLine() {
		return this.l;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

}
