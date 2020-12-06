package edu.smartcity.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import edu.smartcity.commons.AccessServer;
import net.proteanit.sql.DbUtils;

import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;

public class CardView {
	// les composants de l IHM
	Graphics graphics;
	private JFrame frame;
	private JTextField textFieldName;
	private JTextField textFieldLength;
	private JTextField textFieldWidth;
	private JTextField textFieldCost;
	private JTextField textFieldCostStation;
	private JButton btnCancel;
	private JButton btnSave;
	private JComboBox comboBoxShape;
	private JPanel PanelCard;
	private JTextField textFieldSearch;
	private JScrollPane scrollPane_1;
	private JLabel lblNewLabel;
	JViewport viewport;

	int xscroll = 0;
	int yscroll = 0;
	String s = "";
	boolean firstRun = false;
	int x = 10;
	int y = 30;
	double sWidth = 0;
	double sLength = 0;
	double pWidth = 0;
	double pHeigth = 0;
	double oldWidth = 0;
	double oldPoint = 0;
	double oldHeigth = 0;
	String oldShape = "";
	int point = 0;
	String ville = "";
	String oldVille = "";
	private double cost = 0;
	private Random r;
	private List<Integer> listOfPoints;
	private final double ENERGY_PRICE = 0.15; // (euro)
	private final double AVERAGE_SPEED = 19.6; // (km/h)
	private final double AVERAGE_CONSUMPTION = 120; // (kw/h)
	private double cost_station; // (euro/station)
	private JTable table;
	private SocketClient client = new SocketClient();
	private DefaultTableModel dtm1;
	SelectCardByName selectCardByName;
	String header[] = new String[] { "Name", "Shape", "Length", "Width", "Nb Stations", "Budget" };

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public CardView() throws IOException {
		client.startConnection(AccessServer.getSERVER(), AccessServer.getPORT_SERVER());
		
		initialize();
		btnCancel.setVisible(false);
		btnSave.setVisible(false);
		scrollPane_1.getViewport().addChangeListener(new ListenAdditionsScrolled());

		textFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				System.out.println("insertUpdate");

				String ville = textFieldSearch.getText();
				try {
					selectCardByName = new SelectCardByName(ville, client);
					dtm1 = new DefaultTableModel(header, 0);
					ArrayList<JSONObject> reponseServ = selectCardByName.getReponseServ();

					Object[] temp = new Object[6];

					for (int i = 0; i < reponseServ.size(); i++) {
						temp[0] = reponseServ.get(i).get("libelle");
						temp[1] = reponseServ.get(i).get("shape");
						temp[2] = reponseServ.get(i).get("length");
						temp[3] = reponseServ.get(i).get("width");
						temp[4] = reponseServ.get(i).get("nb_points");
						temp[5] = reponseServ.get(i).get("cost");

						dtm1.addRow(temp);

					}

					table.setModel(dtm1);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("Vous avez recupere vos données stockées en base");
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				System.out.println("removeUpdate");
				String ville = textFieldSearch.getText();
				try {
					selectCardByName = new SelectCardByName(ville, client);
					dtm1 = new DefaultTableModel(header, 0);
					ArrayList<JSONObject> reponseServ = selectCardByName.getReponseServ();

					Object[] temp = new Object[6];

					for (int i = 0; i < reponseServ.size(); i++) {
						temp[0] = reponseServ.get(i).get("libelle");
						temp[1] = reponseServ.get(i).get("shape");
						temp[2] = reponseServ.get(i).get("length");
						temp[3] = reponseServ.get(i).get("width");
						temp[4] = reponseServ.get(i).get("nb_points");
						temp[5] = reponseServ.get(i).get("cost");

						dtm1.addRow(temp);

					}

					table.setModel(dtm1);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("changedUpdate");
				String ville = textFieldSearch.getText();
				try {
					selectCardByName = new SelectCardByName(ville, client);
					dtm1 = new DefaultTableModel(header, 0);
					ArrayList<JSONObject> reponseServ = selectCardByName.getReponseServ();

					Object[] temp = new Object[6];

					for (int i = 0; i < reponseServ.size(); i++) {
						temp[0] = reponseServ.get(i).get("libelle");
						temp[1] = reponseServ.get(i).get("shape");
						temp[2] = reponseServ.get(i).get("length");
						temp[3] = reponseServ.get(i).get("width");
						temp[4] = reponseServ.get(i).get("nb_points");
						temp[5] = reponseServ.get(i).get("cost");

						dtm1.addRow(temp);

					}

					table.setModel(dtm1);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("Vous avez choisi la ville que vous voulez recuperer");
			}

		});

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(51, 255, 204));
		frame.setBounds(100, 100, 711, 529);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(0, 102, 153));
		panel.setBounds(520, 58, 150, 308);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabelName = new JLabel("Name");
		lblNewLabelName.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblNewLabelName.setForeground(new Color(255, 255, 255));
		lblNewLabelName.setBounds(56, 2, 61, 16);
		panel.add(lblNewLabelName);

		textFieldName = new JTextField();
		textFieldName.setBounds(10, 18, 130, 26);
		panel.add(textFieldName);
		textFieldName.setColumns(10);

		textFieldLength = new JTextField();
		textFieldLength.setColumns(10);
		textFieldLength.setBounds(10, 114, 130, 26);
/*		textFieldLength.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (s.equals("Square")) {
					textFieldWidth.setText(textFieldLength.getText());
				}
				System.out.println("Vous avez choisi -Square -");
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (s.equals("Square")) {
					textFieldWidth.setText(textFieldLength.getText());
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (s.equals("Square")) {
					textFieldWidth.setText(textFieldLength.getText());
				}
			}
		});
*/
		panel.add(textFieldLength);

		textFieldWidth = new JTextField();
		textFieldWidth.setColumns(10);
		textFieldWidth.setBounds(10, 162, 130, 26);
		panel.add(textFieldWidth);

		JLabel lblShape = new JLabel("Shape");
		lblShape.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblShape.setForeground(Color.WHITE);
		lblShape.setBounds(56, 48, 61, 16);
		panel.add(lblShape);

		JLabel lblLength = new JLabel("Length");
		lblLength.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblLength.setForeground(Color.WHITE);
		lblLength.setBounds(56, 94, 61, 16);
		panel.add(lblLength);

		JLabel lblWidth = new JLabel("Width ");
		lblWidth.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblWidth.setForeground(Color.WHITE);
		lblWidth.setBounds(56, 144, 61, 16);
		panel.add(lblWidth);

		JLabel lblCost = new JLabel("Budget");
		lblCost.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblCost.setForeground(Color.WHITE);
		lblCost.setBounds(56, 190, 61, 16);
		panel.add(lblCost);
		
		JLabel lblCostStation = new JLabel("Cost of a station");
		lblCostStation.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblCostStation.setForeground(Color.WHITE);
		lblCostStation.setBounds(17, 247, 117, 16);
		panel.add(lblCostStation);

		textFieldCost = new JTextField();
		textFieldCost.setColumns(10);
		textFieldCost.setBounds(10, 213, 130, 26);
		panel.add(textFieldCost);
		
		textFieldCostStation = new JTextField();
		textFieldCostStation.setColumns(10);
		textFieldCostStation.setBounds(10, 270, 130, 26);
		panel.add(textFieldCostStation);

		comboBoxShape = new JComboBox();
		comboBoxShape.setBounds(10, 65, 130, 28);
		comboBoxShape.setModel(new DefaultComboBoxModel(new String[] { "Ellipse", "Rectangle" }));
		comboBoxShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxShapeActionPerformed(e);
			}
		});
		panel.add(comboBoxShape);

		btnSave = new JButton("Save");
		btnSave.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnSave.setBackground(new Color(0, 102, 0));
		btnSave.setForeground(Color.WHITE);
		btnSave.setBounds(535, 415, 117, 29);
		btnSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jBtnSaveActionPerformed(evt);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnSave);

		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnCancel.setBackground(new Color(0, 102, 255));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBounds(535, 450, 117, 29);
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});
		frame.getContentPane().add(btnCancel);

		JLabel lblYourCitysCard = new JLabel("City Card");
		lblYourCitysCard.setFont(new Font("Myanmar MN", Font.BOLD | Font.ITALIC, 17));
		lblYourCitysCard.setForeground(new Color(0, 0, 0));
		lblYourCitysCard.setBounds(566, 11, 98, 33);
		frame.getContentPane().add(lblYourCitysCard);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 87, 466, 96);
		frame.getContentPane().add(scrollPane);
		table = new JTable();
		fillTable();
		table.setModel(dtm1);
		scrollPane.setViewportView(table);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableRechMouseClicked(evt);
			}
		});

		table.setBorder(BorderFactory.createCompoundBorder());
		table.setForeground(Color.BLACK);
		table.setShowGrid(true);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 183, 471, 269);
		frame.getContentPane().add(scrollPane_1);
		scrollPane_1.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				jScrollPane2ComponentResized(evt);
			}
		});

		lblNewLabel = new JLabel();
		Image imgSensor = new ImageIcon(this.getClass().getResource("/maps.jpg")).getImage();
		Image newimgSensor = imgSensor.getScaledInstance(600, 840, java.awt.Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(newimgSensor));
		PanelCard = new JPanel();
		PanelCard.add(lblNewLabel);
		PanelCard.setLayout(new CardLayout(0, 0));
		PanelCard.setBounds(33, 195, 466, 284);
		scrollPane_1.setViewportView(PanelCard);

		JLabel lblCityChosen = new JLabel("City chosen");
		lblCityChosen.setForeground(Color.BLACK);
		lblCityChosen.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		lblCityChosen.setBounds(215, 12, 133, 33);
		frame.getContentPane().add(lblCityChosen);

		JButton btnNewButtonValidate = new JButton("Validate");
		btnNewButtonValidate.setFont(new Font("SansSerif", Font.BOLD, 13));
		btnNewButtonValidate.setBackground(new Color(204, 0, 0));
		btnNewButtonValidate.setBounds(535, 378, 117, 29);
		frame.getContentPane().add(btnNewButtonValidate);
		btnNewButtonValidate.setForeground(Color.WHITE);
		btnNewButtonValidate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNewButtonActionPerformed(evt);
			}
		});

		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(100, 53, 150, 28);
		frame.getContentPane().add(textFieldSearch);
		textFieldSearch.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Search ...");
		lblNewLabel_1.setBounds(33, 59, 55, 16);
		frame.getContentPane().add(lblNewLabel_1);

	}

	private void fillTable() throws JSONException, IOException {
		SelectCard selectCard = new SelectCard(client);
		dtm1 = new DefaultTableModel(header, 0);
		ArrayList<JSONObject> reponseServ = selectCard.getReponseServ();

		Object[] temp = new Object[6];

		for (int i = 0; i < reponseServ.size(); i++) {
			temp[0] = reponseServ.get(i).get("libelle");
			temp[1] = reponseServ.get(i).get("shape");
			temp[2] = reponseServ.get(i).get("length");
			temp[3] = reponseServ.get(i).get("width");
			temp[4] = reponseServ.get(i).get("nb_points");
			temp[5] = reponseServ.get(i).get("cost");

			dtm1.addRow(temp);

		}

	}
	
	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		btnSave.setVisible(false);
		btnCancel.setVisible(false);
		enables(true);
	}// GEN-LAST:event_jButton2ActionPerformed

	private void jScrollPane2ComponentResized(java.awt.event.ComponentEvent evt) {// GEN-FIRST:event_jScrollPane2ComponentResized

		if (firstRun != false) {
			prepare();
			drawing(s);
		}
	}// GEN-LAST:event_jScrollPane2ComponentResized
		// Listener sur le comboBox pour changer la valeur de la longueur suite Ã  la
		// valeur de la hauteur dans le cas du "Square"

	private void comboBoxShapeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_comboBoxFormeActionPerformed
		s = (String) comboBoxShape.getItemAt(comboBoxShape.getSelectedIndex());
		/*if (s.equals("Square")) {
			textFieldWidth.setEnabled(false);
			textFieldWidth.setText(textFieldLength.getText());
		} else {*/
			textFieldWidth.setEnabled(true);
		
	}// GEN-LAST:event_comboBoxFormeActionPerformed

	private void tableRechMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tableRechMouseClicked
		if (table.isEnabled() == true) {

			int index = table.getSelectedRow();
			TableModel model = table.getModel();
			System.out.println(model.getValueAt(index, 1));

			s = (String) model.getValueAt(index, 1);

			try {
				sLength = Integer.parseInt(model.getValueAt(index, 2).toString());
				System.out.println("Vous avez recupere la longueur voulue");
				sWidth = Integer.parseInt(model.getValueAt(index, 3).toString());
				System.out.println("Vous avez recupere la largeur voulue");
				point = Integer.parseInt(model.getValueAt(index, 4).toString());
				System.out.println("Vous avez recupere le nombre de stations voulues");

				firstRun = true;
				prepare();

				drawing(s);
				PanelCard.setVisible(true);

				System.out.println("it's working");

			} catch (NumberFormatException exc) {
				// JOptionPane.showMessageDialog(null, "You must enter valid parameters (number)
				// to continue", "Warning : wrong parameter(s) type",
				// JOptionPane.WARNING_MESSAGE);
				// System.out.println("Vous devez saisir des entiers pour continuer");
			}

		}
	}// GEN-LAST:event_tableRechMouseClicked

	// Action du boutton Valider

	private void btnNewButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewButtonActionPerformed
		s = (String) comboBoxShape.getItemAt(comboBoxShape.getSelectedIndex());
		ville = textFieldName.getText();
		if (textFieldLength.getText().length() == 0 || textFieldWidth.getText().length() == 0
				|| textFieldCost.getText().length() == 0 || textFieldCostStation.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "You must fill all the fields to continue",
					"Warning : Empty parameter(s)", JOptionPane.WARNING_MESSAGE);
			System.out.println("Vous devez remplir tous les champs de saisie pour continuer");

		} else if (Double.parseDouble(textFieldWidth.getText()) >= Double.parseDouble(textFieldLength.getText())) {
			JOptionPane.showMessageDialog(null, "The length must be greater than the width ",
					"Warning : Empty parameter(s)", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				sWidth = Double.parseDouble(textFieldWidth.getText());
				sLength = Double.parseDouble(textFieldLength.getText());
				cost = Double.parseDouble(textFieldCost.getText());
				cost_station = Double.parseDouble(textFieldCostStation.getText());
				point = nbStation(cost, cost_station );
				// Condition pour vÃ©rifier si les valeurs sont changÃ©es
				if ((sWidth != oldWidth || sLength != oldHeigth || point != oldPoint || !s.equals(oldShape))) {
					if (sWidth < 0 || sLength < 0 || cost < 0 || cost_station <= 0 ) {
						JOptionPane.showMessageDialog(null, "You must enter positive numbers",
								"Warning : negative numbers", JOptionPane.WARNING_MESSAGE);
						System.out.println("Vous devez saisir des valeurs positives pour procéder à la validation");
					} else {
						firstRun = true;
						prepare();
						drawing(s);
						btnSave.setVisible(true);
						btnCancel.setVisible(true);
						enables(false);
					}
				}
				
				PanelCard.setVisible(true);

			} catch (NumberFormatException exc) {
				JOptionPane.showMessageDialog(null, "You must enter valid parameters (number) to continue",
						"Warning : wrong parameter(s) type", JOptionPane.WARNING_MESSAGE);
				System.out.println("Vous devez saisir des entiers pour continuer");
			}

		}
	}
	
	public int nbStation(double budget, double cost) {
		int nb = (int) Math.round(budget / cost);
		      
		return nb;
	}

	// GEN-LAST:event_btnNewButtonActionPerformed

	private void jBtnSaveActionPerformed(java.awt.event.ActionEvent evt) throws JSONException, IOException {// GEN-FIRST:event_jButton1ActionPerformed
		int station = nbStation(Double.parseDouble(textFieldCost.getText()), Double.parseDouble(textFieldCostStation.getText()));
		
		JSONObject obj = new JSONObject();

		obj.put("demandType", String.valueOf("INSERT_CARD"));
		obj.put("libelle", textFieldName.getText()); // change with ville
		obj.put("shape", String.valueOf(comboBoxShape.getItemAt(comboBoxShape.getSelectedIndex()))); // change with s
		obj.put("length", Double.valueOf(Double.parseDouble(textFieldLength.getText())));
		obj.put("width", Double.valueOf(Double.parseDouble(textFieldWidth.getText())));
		obj.put("nb_points", station);
		obj.put("cost", Double.valueOf(Double.parseDouble(textFieldCost.getText())));

		System.out.println(obj);
		JSONObject reponse = new JSONObject();
		reponse = SocketClient.sendMessage(obj);
		System.out.println(reponse);
		if (reponse.get("reponse").equals("Error")) {
			JOptionPane.showMessageDialog(null, "Those informations already exist",
					"Warning : Duplicate record", JOptionPane.WARNING_MESSAGE);
			System.out.println("Vous devez saisir des valeurs");
		} else {
			btnSave.setVisible(false);
			btnCancel.setVisible(false);
			enables(true);
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[] { reponse.get("libelle"), reponse.get("shape"), reponse.get("length"),
					reponse.get("width"), reponse.get("nb_points"), reponse.get("cost") });
		}

	}// GEN-LAST:event_jButton1ActionPerformed

	public void prepare() {
		// 350 500
		graphics = PanelCard.getGraphics();
	
		
		viewport = scrollPane_1.getViewport();
		xscroll = viewport.getViewPosition().x;
		yscroll = viewport.getViewPosition().y;
		graphics.clearRect(x - xscroll - 1, y - yscroll - 20, (int) Math.round(pWidth), (int) Math.round(pHeigth));
		//graphics.setColor(new Color(255, 255, 255));
		graphics.fillRect(x - xscroll - 1, y - yscroll - 20, (int) Math.round(pWidth), (int) Math.round(pHeigth));
		scrollPane_1.setPreferredSize(new Dimension((int) Math.round(pWidth) + 320, (int) Math.round(sLength) + 440));
		
		PanelCard.setPreferredSize(new Dimension((int) Math.round(sWidth) + 320, (int) Math.round(sLength) + 440));
		
		draw((int) Math.round(sWidth), (int) Math.round(sLength));
	}

	// draw shape
	public void draw(int width, int length) {

/*		if (s.equals("Square") || s.equals("square")) {

			graphics.setColor(Color.BLUE);
			graphics.drawRect(x, y, width, length);

		} else 
		*/
		if (s.equals("Rectangle") || s.equals("rectangle")) {

			graphics.setColor(Color.RED);
			graphics.drawRect(x, y, width, length);

		} else if (s.equals("Ellipse") || s.equals("ellipse")) {

			graphics.setColor(Color.BLACK);
			graphics.drawOval(x, y, width, length);

		}

		oldWidth = sWidth;
		oldHeigth = sLength;
		oldPoint = point;
		oldShape = s;

	}

	public void drawing(String s) {

		Graphics2D g2d = (Graphics2D) graphics;

		g2d.setPaint(Color.red);

		g2d.translate(x, y);
		int w = (int) Math.round(sWidth);
		int h = (int) Math.round(sLength);

		int size = point;
		int xPos = x;
		int yPos = y;
		int h2 = Math.max(h, w);
		int w2 = Math.min(h, w);
		double tester = 0;
		r = new Random(2);
		listOfPoints = new ArrayList<>();
		if (s.equals("Ellipse")) {
			for (int i = 0; i < size; i++) {
				do {
					xPos = Math.abs(r.nextInt(w * 10)) % w;
					yPos = Math.abs(r.nextInt(h * 10)) % h;
					if (w >= h) {
						tester = (Math.pow(xPos - h2 / 2, 2) / Math.pow(w / 2, 2)
								+ Math.pow(yPos - w2 / 2, 2) / Math.pow(h / 2, 2));
					} else {
						tester = (Math.pow(xPos - w2 / 2, 2) / Math.pow(w / 2, 2)
								+ Math.pow(yPos - h2 / 2, 2) / Math.pow(h / 2, 2));
					}
				} while (tester > 1);

				for (int l = 1; l <= 6; l += 1) {
					g2d.drawOval(xPos, yPos, l, l);
				}

				listOfPoints.add(xPos);
				listOfPoints.add(yPos);
			}

		} else {
			for (int i = 0; i < size; i++) {
				xPos = Math.abs(r.nextInt(w * 10)) % w;
				yPos = Math.abs(r.nextInt(h * 10)) % h;

				for (int l = 1; l <= 6; l += 1) {
					g2d.drawOval(xPos, yPos, l, l);
				}
				// g2d.drawLine(x, y, x, y);
				listOfPoints.add(xPos);
				listOfPoints.add(yPos);
			}

		}

		int currentIndex = 0;
		int closestIndex = 0;
		double sumDistance = 0;
		List<Integer> alreadyFlagged = new ArrayList<>();
		for (int i = 0; i < listOfPoints.size() / 2 - 1; i++) {
			closestIndex = getMinimumDistanceAbscissaIndex(listOfPoints.get(currentIndex),
					listOfPoints.get(currentIndex + 1), listOfPoints, alreadyFlagged);
			g2d.drawLine(listOfPoints.get(currentIndex), listOfPoints.get(currentIndex + 1),
					listOfPoints.get(closestIndex), listOfPoints.get(closestIndex + 1));
			sumDistance += calculateDistanceBetweenPoints(listOfPoints.get(currentIndex),
					listOfPoints.get(currentIndex + 1), listOfPoints.get(closestIndex),
					listOfPoints.get(closestIndex + 1));
			alreadyFlagged.add(currentIndex);
			currentIndex = closestIndex;
		}
		// Cost
		/*
		 * double cost = ENERGY_PRICE * ((sumDistance * AVERAGE_CONSUMPTION) /
		 * AVERAGE_SPEED); this.cost = cost; g2d.setFont(new Font("TimesRoman",
		 * Font.PLAIN, 20)); g2d.drawString("Cost: " + (int) cost + "â‚¬", 0, -5);
		 */
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		//g2d.drawString("Cost of station : 100€", 0, -5);

	}

	public void enables(Boolean enabled) {
		textFieldName.setEnabled(enabled);
		comboBoxShape.setEnabled(enabled);
		textFieldLength.setEnabled(enabled);
		textFieldWidth.setEnabled(enabled);
		textFieldCost.setEnabled(enabled);
		textFieldSearch.setEnabled(enabled);
		textFieldCostStation.setEnabled(enabled);
		table.setEnabled(enabled);
	}
	
	

	public double calculateDistanceBetweenPoints(int x1, int y1, int x2, int y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	public boolean notInList(int e, List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == e) {
				return false;
			}
		}
		return true;
	}

	public int getMinimumDistanceAbscissaIndex(int x, int y, List<Integer> listOfPoints, List<Integer> alreadyFlagged) {
		int minIndex = 0;
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < listOfPoints.size(); i += 2) {
			if (calculateDistanceBetweenPoints(x, y, listOfPoints.get(i), listOfPoints.get(i + 1)) < minDistance
					&& (listOfPoints.get(i) != x && listOfPoints.get(i + 1) != y) && notInList(i, alreadyFlagged)) {
				minDistance = calculateDistanceBetweenPoints(x, y, listOfPoints.get(i), listOfPoints.get(i + 1));
				minIndex = i;
			}
		}
		return minIndex;
	}

	// Listener du statut du scrollPane pour reproduire le drawing dans chaque
	// changement de position du scrollbar
	public class ListenAdditionsScrolled implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			// firstRun est une valeur Boolean Ã  vÃ©rifier pour empÃ©cher d'Ã©xecuter les
			// drawing lors de l'Ã©xecution du programme
			if (firstRun != false) {
				prepare();
				drawing(s);
			}

		}

	}

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 */
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
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
}
