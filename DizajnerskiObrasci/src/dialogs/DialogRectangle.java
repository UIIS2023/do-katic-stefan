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
import geometry.Rectangle;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogRectangle extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUpperLeftX;
	private JTextField txtUpperLeftY;
	private JTextField txtWidth;
	private JTextField txtHeight;


	private Rectangle r = new Rectangle();

	private boolean successful;
	private Color border = Color.BLACK;
	private Color fill = Color.WHITE;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogRectangle dialog = new DialogRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogRectangle() {
		setResizable(false);
		setTitle("Pravougaonik");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 320, 260);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.rowHeights = new int[] {0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWidths = new int[] {0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUppeLeftrX = new JLabel("Gornja leva tačka X:");
			GridBagConstraints gbc_lblUppeLeftrX = new GridBagConstraints();
			gbc_lblUppeLeftrX.insets = new Insets(0, 0, 5, 5);
			gbc_lblUppeLeftrX.gridx = 0;
			gbc_lblUppeLeftrX.gridy = 0;
			contentPanel.add(lblUppeLeftrX, gbc_lblUppeLeftrX);
		}
		{
			txtUpperLeftX = new JTextField();
			GridBagConstraints gbc_txtUpperLeftX = new GridBagConstraints();
			gbc_txtUpperLeftX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftX.insets = new Insets(0, 0, 5, 0);
			gbc_txtUpperLeftX.gridx = 1;
			gbc_txtUpperLeftX.gridy = 0;
			contentPanel.add(txtUpperLeftX, gbc_txtUpperLeftX);
			txtUpperLeftX.setColumns(10);
		}
		{
			JLabel lblUpperLeftY = new JLabel("Gornja leva tačka Y:");
			GridBagConstraints gbc_lblUpperLeftY = new GridBagConstraints();
			gbc_lblUpperLeftY.insets = new Insets(0, 0, 5, 5);
			gbc_lblUpperLeftY.gridx = 0;
			gbc_lblUpperLeftY.gridy = 1;
			contentPanel.add(lblUpperLeftY, gbc_lblUpperLeftY);
		}
		{
			txtUpperLeftY = new JTextField();
			GridBagConstraints gbc_txtUpperLeftY = new GridBagConstraints();
			gbc_txtUpperLeftY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftY.insets = new Insets(0, 0, 5, 0);
			gbc_txtUpperLeftY.gridx = 1;
			gbc_txtUpperLeftY.gridy = 1;
			contentPanel.add(txtUpperLeftY, gbc_txtUpperLeftY);
			txtUpperLeftY.setColumns(10);
		}
		{
			JLabel lblWidth = new JLabel("Širina:");
			GridBagConstraints gbc_lblWidth = new GridBagConstraints();
			gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
			gbc_lblWidth.gridx = 0;
			gbc_lblWidth.gridy = 2;
			contentPanel.add(lblWidth, gbc_lblWidth);
		}
		{
			txtWidth = new JTextField();
			GridBagConstraints gbc_txtWidth = new GridBagConstraints();
			gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWidth.insets = new Insets(0, 0, 5, 0);
			gbc_txtWidth.gridx = 1;
			gbc_txtWidth.gridy = 2;
			contentPanel.add(txtWidth, gbc_txtWidth);
			txtWidth.setColumns(10);
		}
		{
			JLabel lblHeight = new JLabel("Visina:");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeight.gridx = 0;
			gbc_lblHeight.gridy = 3;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			txtHeight = new JTextField();
			GridBagConstraints gbc_txtHeight = new GridBagConstraints();
			gbc_txtHeight.insets = new Insets(0, 0, 5, 0);
			gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtHeight.gridx = 1;
			gbc_txtHeight.gridy = 3;
			contentPanel.add(txtHeight, gbc_txtHeight);
			txtHeight.setColumns(10);
		}
		{
			JLabel lblBorderColor = new JLabel("Boja ivice:");
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
					border = JColorChooser.showDialog(null, "Odaberi boju ivice", border);
				}
			});
			GridBagConstraints gbc_btnBorderColor = new GridBagConstraints();
			gbc_btnBorderColor.gridx = 1;
			gbc_btnBorderColor.gridy = 4;
			contentPanel.add(btnBorderColor, gbc_btnBorderColor);
		}
		{
			JLabel lblFillColor = new JLabel("Unutrašnja boja:");
			GridBagConstraints gbc_lblFillColor = new GridBagConstraints();
			gbc_lblFillColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblFillColor.gridx = 0;
			gbc_lblFillColor.gridy = 5;
			contentPanel.add(lblFillColor, gbc_lblFillColor);
		}
		{
			JButton btnFillColor = new JButton("Izaberi");
			btnFillColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fill = JColorChooser.showDialog(null, "Odaberi boju unutrašnjosti", fill);
				}
			});
			GridBagConstraints gbc_btnFillColor = new GridBagConstraints();
			gbc_btnFillColor.insets = new Insets(0, 0, 5, 0);
			gbc_btnFillColor.gridx = 1;
			gbc_btnFillColor.gridy = 5;
			contentPanel.add(btnFillColor, gbc_btnFillColor);
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
							r.setUpperLeftPoint(new Point(Integer.parseInt(txtUpperLeftX.getText()), Integer.parseInt(txtUpperLeftY.getText())) );
							r.setWidth(Integer.parseInt(txtWidth.getText()));
							r.setHeight(Integer.parseInt(txtHeight.getText()));
							r.setBorder(border);
							r.setFill(fill);
							//r.setSelected(true);
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
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public DialogRectangle(Color border, Color fill) {
		this();
		this.border = border;
		this.fill = fill;
	}

	public void drawDialog(Point point) {
		setTitle("Nacrtaj novi pravougaonik");
		txtUpperLeftX.setText(Integer.toString(point.getX()));
		txtUpperLeftY.setText(Integer.toString(point.getY()));
		txtUpperLeftX.setEditable(false);
		txtUpperLeftY.setEditable(false);
	}
	
	public void modifyDialog(Rectangle rectangle) {
		setTitle("Izmeni pravougaonik");
		txtUpperLeftX.setText(Integer.toString(rectangle.getUpperLeftPoint().getX()));
		txtUpperLeftY.setText(Integer.toString(rectangle.getUpperLeftPoint().getY()));
		txtWidth.setText(Integer.toString(rectangle.getWidth()));
		txtHeight.setText(Integer.toString(rectangle.getHeight()));
		this.border = rectangle.getBorder();
		this.fill = rectangle.getFill();
	}
	
	public Rectangle getRectangle() {
		return this.r;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
}
