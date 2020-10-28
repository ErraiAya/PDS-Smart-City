package smart_city.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class CardView {

	private JFrame frame;
	private JTextField textFieldName;
	private JTextField textFieldShape;
	private JTextField textFieldLength;
	private JTextField textFieldWidth;
	private JTextField textFieldCost;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardView window = new CardView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CardView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(176, 224, 230));
		frame.setBounds(100, 100, 711, 529);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(0, 0, 128));
		panel.setBounds(533, 68, 150, 380);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabelName = new JLabel("Name");
		lblNewLabelName.setForeground(new Color(255, 255, 255));
		lblNewLabelName.setBounds(56, 6, 61, 16);
		panel.add(lblNewLabelName);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(14, 34, 130, 26);
		panel.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldShape = new JTextField();
		textFieldShape.setColumns(10);
		textFieldShape.setBounds(14, 100, 130, 26);
		panel.add(textFieldShape);
		
		textFieldLength = new JTextField();
		textFieldLength.setColumns(10);
		textFieldLength.setBounds(14, 166, 130, 26);
		panel.add(textFieldLength);
		
		textFieldWidth = new JTextField();
		textFieldWidth.setColumns(10);
		textFieldWidth.setBounds(14, 228, 130, 26);
		panel.add(textFieldWidth);
		
		JLabel lblShape = new JLabel("Shape");
		lblShape.setForeground(Color.WHITE);
		lblShape.setBounds(56, 72, 61, 16);
		panel.add(lblShape);
		
		JLabel lblLength = new JLabel("Length");
		lblLength.setForeground(Color.WHITE);
		lblLength.setBounds(56, 138, 61, 16);
		panel.add(lblLength);
		
		JLabel lblWidth = new JLabel("Width ");
		lblWidth.setForeground(Color.WHITE);
		lblWidth.setBounds(56, 200, 61, 16);
		panel.add(lblWidth);
		
		JLabel lblCost = new JLabel("Cost");
		lblCost.setForeground(Color.WHITE);
		lblCost.setBounds(56, 266, 61, 16);
		panel.add(lblCost);
		
		textFieldCost = new JTextField();
		textFieldCost.setColumns(10);
		textFieldCost.setBounds(14, 294, 130, 26);
		panel.add(textFieldCost);
		
		JButton btnNewButtonValidate = new JButton("Validate");
		btnNewButtonValidate.setForeground(new Color(199, 21, 133));
		btnNewButtonValidate.setBounds(14, 330, 117, 29);
		panel.add(btnNewButtonValidate);
		
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(199, 21, 133));
		btnSave.setBounds(553, 447, 117, 29);
		frame.getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setForeground(new Color(199, 21, 133));
		btnCancel.setBounds(553, 472, 117, 29);
		frame.getContentPane().add(btnCancel);
		
		JLabel lblYourCitysCard = new JLabel("City Card");
		lblYourCitysCard.setFont(new Font("Myanmar MN", Font.BOLD | Font.ITALIC, 17));
		lblYourCitysCard.setForeground(new Color(0, 0, 0));
		lblYourCitysCard.setBounds(572, 22, 133, 33);
		frame.getContentPane().add(lblYourCitysCard);
		
		JPanel panelTable = new JPanel();
		panelTable.setBounds(74, 58, 378, 92);
		frame.getContentPane().add(panelTable);
		
		JPanel PanelCard = new JPanel();
		PanelCard.setBounds(74, 162, 378, 290);
		frame.getContentPane().add(PanelCard);
		
		JLabel lblCityChosen = new JLabel("City chosen");
		lblCityChosen.setForeground(Color.BLACK);
		lblCityChosen.setFont(new Font("Myanmar MN", Font.BOLD | Font.ITALIC, 14));
		lblCityChosen.setBounds(212, 22, 133, 33);
		frame.getContentPane().add(lblCityChosen);
	}
}
