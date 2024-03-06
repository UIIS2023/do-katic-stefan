package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import adapter.HexagonAdapter;
import geometry.Point;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogHexagon extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JTextField txtCenterX;
	private JTextField txtCenterY;
	private JTextField txtRadius;
	
	private HexagonAdapter ha = new HexagonAdapter();
	private Color border = Color.BLACK;
	private Color fill = Color.WHITE;
	private boolean successful;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogHexagon dialog = new DialogHexagon();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogHexagon() {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Heksagon");
		setBounds(100, 100, 350, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {0, 0};
		gbl_contentPanel.rowHeights = new int[] {0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCenterX = new JLabel("Centar X:");
			GridBagConstraints gbc_lblCenterX = new GridBagConstraints();
			gbc_lblCenterX.insets = new Insets(0, 0, 5, 5);
			gbc_lblCenterX.gridx = 0;
			gbc_lblCenterX.gridy = 0;
			contentPanel.add(lblCenterX, gbc_lblCenterX);
		}
		{
			txtCenterX = new JTextField();
			GridBagConstraints gbc_txtCenterX = new GridBagConstraints();
			gbc_txtCenterX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCenterX.insets = new Insets(0, 0, 5, 0);
			gbc_txtCenterX.gridx = 1;
			gbc_txtCenterX.gridy = 0;
			contentPanel.add(txtCenterX, gbc_txtCenterX);
			txtCenterX.setColumns(10);
		}
		{
			JLabel lblCenterY = new JLabel("Centar Y:");
			GridBagConstraints gbc_lblCenterY = new GridBagConstraints();
			gbc_lblCenterY.insets = new Insets(0, 0, 5, 5);
			gbc_lblCenterY.gridx = 0;
			gbc_lblCenterY.gridy = 1;
			contentPanel.add(lblCenterY, gbc_lblCenterY);
		}
		{
			txtCenterY = new JTextField();
			GridBagConstraints gbc_txtCenterY = new GridBagConstraints();
			gbc_txtCenterY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCenterY.insets = new Insets(0, 0, 5, 0);
			gbc_txtCenterY.gridx = 1;
			gbc_txtCenterY.gridy = 1;
			contentPanel.add(txtCenterY, gbc_txtCenterY);
			txtCenterY.setColumns(10);
		}
		{
			JLabel lblRadius = new JLabel("Polupre\u010Dnik do ivice:");
			GridBagConstraints gbc_lblRadius = new GridBagConstraints();
			gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblRadius.gridx = 0;
			gbc_lblRadius.gridy = 2;
			contentPanel.add(lblRadius, gbc_lblRadius);
		}
		{
			txtRadius = new JTextField();
			GridBagConstraints gbc_txtRadius = new GridBagConstraints();
			gbc_txtRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtRadius.fill = GridBagConstraints.BOTH;
			gbc_txtRadius.gridx = 1;
			gbc_txtRadius.gridy = 2;
			contentPanel.add(txtRadius, gbc_txtRadius);
			txtRadius.setColumns(10);
		}
		{
			JLabel lblBorderColor = new JLabel("Boja ivice:");
			GridBagConstraints gbc_lblBorderColor = new GridBagConstraints();
			gbc_lblBorderColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblBorderColor.gridx = 0;
			gbc_lblBorderColor.gridy = 3;
			contentPanel.add(lblBorderColor, gbc_lblBorderColor);
		}
		{
			JButton bntBorderColor = new JButton("Izaberi");
			bntBorderColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					border = JColorChooser.showDialog(null, "Odaberi boju ivice", border);
				}
			});
			GridBagConstraints gbc_bntBorderColor = new GridBagConstraints();
			gbc_bntBorderColor.insets = new Insets(0, 0, 5, 0);
			gbc_bntBorderColor.gridx = 1;
			gbc_bntBorderColor.gridy = 3;
			contentPanel.add(bntBorderColor, gbc_bntBorderColor);
		}
		{
			JLabel lblFillColor = new JLabel("Boja unutra\u0161njosti:");
			GridBagConstraints gbc_lblFillColor = new GridBagConstraints();
			gbc_lblFillColor.insets = new Insets(0, 0, 0, 5);
			gbc_lblFillColor.gridx = 0;
			gbc_lblFillColor.gridy = 4;
			contentPanel.add(lblFillColor, gbc_lblFillColor);
		}
		{
			JButton bntFillColor = new JButton("Izaberi");
			bntFillColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fill = JColorChooser.showDialog(null, "Odaberi boju unutrašnjosti", fill);
				}
			});
			GridBagConstraints gbc_bntFillColor = new GridBagConstraints();
			gbc_bntFillColor.gridx = 1;
			gbc_bntFillColor.gridy = 4;
			contentPanel.add(bntFillColor, gbc_bntFillColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							//TODO konstruktor u hexagonadapter
							ha = new HexagonAdapter(
									new Point(
										Integer.parseInt(txtCenterX.getText()), 
										Integer.parseInt(txtCenterY.getText())),
									Integer.parseInt(txtRadius.getText()),
									border,
									fill);
							//ha.setSelected(true);
							setSuccessful(true);
							dispose();
						} catch(Exception ex) {
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
	
	public DialogHexagon(Color border, Color fill) {
		this();
		this.border = border;
		this.fill = fill;
	}

	public void drawDialog(Point hexagonCenter) {
		setTitle("Nacrtaj novi heksagon");
		txtCenterX.setText(Integer.toString(hexagonCenter.getX()));
		txtCenterY.setText(Integer.toString(hexagonCenter.getY()));
		txtCenterX.setEditable(false);
		txtCenterY.setEditable(false);
	}
	
	public void modifyDialog(HexagonAdapter hexagon) {
		setTitle("Izmeni heksagon");
		txtCenterX.setText(Integer.toString(hexagon.getX()));
		txtCenterY.setText(Integer.toString(hexagon.getY()));;
		txtRadius.setText(Integer.toString(hexagon.getRadius()));
		this.border = hexagon.getBorder();
		this.fill = hexagon.getFill();
	}


	public HexagonAdapter getHexagon() {
		return ha;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean succesful) {
		this.successful = succesful;
	}

}
