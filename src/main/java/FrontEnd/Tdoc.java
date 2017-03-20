package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import Model.Logger;
import Model.Model;
import Model.Utils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Tdoc extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1791439212865423973L;
	private JmTextArea textUwagi;
	private JmTextField textNrSamochod;
	private JmTextField textNrKontener;
	private JTextField textField_5;
	private JmDatePickerImpl datePickerDoc;
	private JLabel textNrDoc;
	private JmDatePickerImpl datePickerDostawa;
	private JButton saveBtn;
	private JButton closeBtn;
	private Document document;
	private Model model;
	private JmComboBox btnProducent;
	private JmComboBox btnDostawca;
	private JmComboBox btnWlasciciel;
	private JmComboBox btnCfg;
	private JButton newBtn;
	private Tdoc panel;
	private boolean savedDoc;
	private JLabel sum1;
	private JLabel sum2;
	private String t_doc_id;
	private Map<String, Integer> mapCfg;
	private JButton saveDocBtn;
	private ResultSet rsCfg;
	private JmComboBox btnOdbiorca;

	public String getT_doc_id() {
		return t_doc_id;
	}

	public Tdoc(final Document document, final Model model) {

		this.model = model;
		this.document = document;
		panel = this;
		savedDoc = false;
		getRSForCombobox();

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		this.addLabels();

		UtilDateModel m = new UtilDateModel();
		m.setValue(new Date());
		JDatePanelImpl datePanel = new JDatePanelImpl(m);

		datePickerDostawa = new JmDatePickerImpl(datePanel);

		UtilDateModel m2 = new UtilDateModel();
		m2.setValue(new Date());
		JDatePanelImpl datePanel2 = new JDatePanelImpl(m2);
		datePickerDoc = new JmDatePickerImpl(datePanel2);
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 5);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 3;
		gbc_textField_7.gridy = 1;
		panel.add(datePickerDoc, gbc_textField_7);

		textNrKontener = new JmTextField();
		GridBagConstraints gbc_textNrKontener = new GridBagConstraints();
		gbc_textNrKontener.insets = new Insets(0, 0, 5, 0);
		gbc_textNrKontener.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNrKontener.gridx = 5;
		gbc_textNrKontener.gridy = 0;
		panel.add(textNrKontener, gbc_textNrKontener);
		textNrKontener.setColumns(10);

		textNrSamochod = new JmTextField();
		GridBagConstraints gbc_textNrSamochod = new GridBagConstraints();
		gbc_textNrSamochod.insets = new Insets(0, 0, 5, 0);
		gbc_textNrSamochod.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNrSamochod.gridx = 5;
		gbc_textNrSamochod.gridy = 1;
		panel.add(textNrSamochod, gbc_textNrSamochod);
		textNrSamochod.setColumns(10);

		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 2;
		panel.add(datePickerDostawa, gbc_textField_5);
		textField_5.setColumns(10);

		textUwagi = new JmTextArea();
		GridBagConstraints gbc_textUwagi = new GridBagConstraints();
		gbc_textUwagi.insets = new Insets(0, 0, 5, 0);
		gbc_textUwagi.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUwagi.gridx = 5;
		gbc_textUwagi.gridy = 2;
		gbc_textUwagi.gridheight = 2;
		panel.add(textUwagi, gbc_textUwagi);
		//textUwagi.setColumns(10);
		textUwagi.setRows(3);
		textUwagi.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				    check();
				  }
				  public void removeUpdate(DocumentEvent e) {
				    check();
				  }
				  public void insertUpdate(DocumentEvent e) {
				    check();
				  }

				  public void check() {
				     if (textUwagi.getLineCount()>3){//make sure no more than 4 lines
				       JOptionPane.showMessageDialog(null, "Error: Cant have more than 4 lines");
				     }
				  }
				});

		btnProducent = new JmComboBox(true, null, model);
		GridBagConstraints gbc_btnProducent = new GridBagConstraints();
		gbc_btnProducent.insets = new Insets(0, 0, 0, 5);
		gbc_btnProducent.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProducent.gridx = 1;
		gbc_btnProducent.gridy = 3;
		panel.add(btnProducent, gbc_btnProducent);

		btnDostawca = new JmComboBox(true, null, model);
		GridBagConstraints gbc_btnDostawca = new GridBagConstraints();
		gbc_btnDostawca.insets = new Insets(0, 0, 0, 5);
		gbc_btnDostawca.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDostawca.gridx = 3;
		gbc_btnDostawca.gridy = 3;
		panel.add(btnDostawca, gbc_btnDostawca);
		

		btnOdbiorca = new JmComboBox(true, null, model);
		GridBagConstraints gbc_btnOdbiorca = new GridBagConstraints();
		gbc_btnOdbiorca.insets = new Insets(0, 0, 0, 5);
		gbc_btnOdbiorca.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOdbiorca.gridx = 1;
		gbc_btnOdbiorca.gridy = 4;
		panel.add(btnOdbiorca, gbc_btnOdbiorca);

		btnWlasciciel = new JmComboBox(true, null, model);
		GridBagConstraints gbc_btnWlasciciel = new GridBagConstraints();
		gbc_btnWlasciciel.insets = new Insets(0, 0, 0, 5);
		gbc_btnWlasciciel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnWlasciciel.gridx = 3;
		gbc_btnWlasciciel.gridy = 4;
		panel.add(btnWlasciciel, gbc_btnWlasciciel);

		mapCfg = Utils.getIdNameMapFrom(rsCfg);
		btnCfg = new JmComboBox(true, mapCfg, model);
		GridBagConstraints gbc_btnCfg = new GridBagConstraints();
		gbc_btnCfg.insets = new Insets(0, 0, 5, 5);
		gbc_btnCfg.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCfg.gridx = 1;
		gbc_btnCfg.gridy = 2;
		Utils.addToComboBox(btnCfg, mapCfg.keySet());
		btnCfg.setActionCommand("TDOC_CFG");
		btnCfg.addActionListener(document);
		panel.add(btnCfg, gbc_btnCfg);

		saveBtn = new JButton("save");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(5, 0, 5, 0);
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.gridx = 4;
		gbc_button.gridy = 4;
		panel.add(saveBtn, gbc_button);

		saveBtn.setActionCommand("TDOC_SAVE");
		saveBtn.addActionListener(document);

		saveDocBtn = new JButton("save document");
		gbc_button.insets = new Insets(5, 0, 5, 0);
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.gridx = 5;
		gbc_button.gridy = 4;
		panel.add(saveDocBtn, gbc_button);

		saveDocBtn.setActionCommand("TDOC_SAVEDOC");
		saveDocBtn.addActionListener(document);

		newBtn = new JButton("New");
		gbc_button.gridx = 4;
		gbc_button.gridy = 3;
		panel.add(newBtn, gbc_button);

		newBtn.setActionCommand("TDOC_NEW");
		newBtn.addActionListener(document);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		JLabel lblNotes = new JLabel("Notes");
		panel_2.add(lblNotes, BorderLayout.NORTH);
		JPanel panel_3 = new JPanel();
		sum1 = new JLabel("");
		sum2 = new JLabel("");
		panel_3.add(sum1);
		panel_3.add(sum2);
		panel_2.add(panel_3, BorderLayout.SOUTH);

		setVisible(true);

		btnCfg.setName("doc_cfg_doc_id");
		btnDostawca.setName("doc_dostawca_id");
		btnProducent.setName("doc_producent_id");
		btnOdbiorca.setName("doc_odbiorca_cfg");
		btnWlasciciel.setName("doc_wlasciciel_id");
		textNrKontener.setName("doc_nr_kontenera");
		textNrSamochod.setName("doc_nr_samochodu");
		textUwagi.setName("doc_uwagi");
		datePickerDoc.setName("doc_data_doc");
		datePickerDostawa.setName("doc_data_dostawy");

	}

	private void addLabels() {

		JLabel lblNewLabel = new JLabel("CDS Europe sp. z o.o.");
		lblNewLabel.setFont(new Font("Serif", Font.PLAIN,20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 2;
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("PZ");
		lblNewLabel_2.setFont(new Font("Serif", Font.PLAIN,20));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		//gbc_lblNewLabel_2.gridheight = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Nr doc");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 0;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		textNrDoc = new JLabel();
		GridBagConstraints gbc_textNrDoc = new GridBagConstraints();
		gbc_textNrDoc.insets = new Insets(0, 0, 5, 5);
		gbc_textNrDoc.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNrDoc.gridx = 3;
		gbc_textNrDoc.gridy = 0;
		panel.add(textNrDoc, gbc_textNrDoc);

		JLabel lblNewLabel_7 = new JLabel("Nr kontenera");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 4;
		gbc_lblNewLabel_7.gridy = 0;
		panel.add(lblNewLabel_7, gbc_lblNewLabel_7);

		JLabel lblNewLabel_4 = new JLabel("Data doc");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 2;
		gbc_lblNewLabel_4.gridy = 1;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);

		JLabel lblNewLabel_8 = new JLabel("Nr samochodu");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 4;
		gbc_lblNewLabel_8.gridy = 1;
		panel.add(lblNewLabel_8, gbc_lblNewLabel_8);

		JLabel lblNewLabel_5 = new JLabel("Data dostawy");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 2;
		gbc_lblNewLabel_5.gridy = 2;
		panel.add(lblNewLabel_5, gbc_lblNewLabel_5);

		JLabel lblNewLabel_9 = new JLabel("Uwagi");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 4;
		gbc_lblNewLabel_9.gridy = 2;
		panel.add(lblNewLabel_9, gbc_lblNewLabel_9);

		JLabel lblNewLabel_1 = new JLabel("Producent:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);


		JLabel lblNewLabel_11 = new JLabel("Odbiorca:");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_11.gridx = 0;
		gbc_lblNewLabel_11.gridy = 4;
		panel.add(lblNewLabel_11, gbc_lblNewLabel_11);
		
		JLabel lblNewLabel_6 = new JLabel("Dostawca");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_6.gridx = 2;
		gbc_lblNewLabel_6.gridy = 3;
		panel.add(lblNewLabel_6, gbc_lblNewLabel_6);

		JLabel lblNewLabel_10 = new JLabel("W\u0142a\u015Bciciel");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_10.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_10.gridx = 2;
		gbc_lblNewLabel_10.gridy = 4;
		panel.add(lblNewLabel_10, gbc_lblNewLabel_10);

	}

	private void clearComponents() {
		Component[] components = panel.getComponents();

		for (Component c : components) {
			if (c instanceof Access) {
				((Access) c).clear();
			}
		}

		textNrDoc.setText("");
	}

	public boolean inputValidated() {
		Component[] components = panel.getComponents();

		for (Component c : components) {
			if (c instanceof Access) {
				if (((Access) c).getOutput().equals("")) {
					Logger.e(Logger.getMethodName(), c.getName() + " is empty");
					return false;
				}
			}
		}
		return true;
	}

	public int generateNrdoc() throws SQLException {

		try {
			String id_cfg_doc = String.valueOf(mapCfg.get(btnCfg.getSelectedItem()));
			String querry = "select v_doc_nr.nr_doc from v_doc_nr where v_doc_nr.doc_cfg_doc_id=" + id_cfg_doc + ";";
			ResultSet rs = model.executeQuerry(querry);
			if (!rs.isBeforeFirst()) {
				textNrDoc.setText("1");
				return 1;
			} else {
				String nr = Utils.getFirstRecordFromRS(rs);
				textNrDoc.setText(nr);
				return Integer.valueOf(nr);
			}
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
			return 0;
		}

	}


	public void saveDoc() throws SQLException {

		String nrDoc, producent, dostawca, odbiorca, wlasciciel, nrKontener, nrSamochod, uwagi = "";
		String dataDoc, dataDostawy = "";
		producent = btnProducent.getOutput();
		dostawca = btnDostawca.getOutput();
		wlasciciel = btnWlasciciel.getOutput();
		odbiorca = btnOdbiorca.getOutput();
		nrKontener = textNrKontener.getOutput();
		nrSamochod = textNrSamochod.getOutput();
		uwagi = textUwagi.getOutput();
		// nrDoc = textNrDoc.getText();
		nrDoc = "-1";
		dataDoc = datePickerDoc.getOutput();
		dataDostawy = datePickerDostawa.getOutput();

		String docDelete = "0";
		String docView = "1";
		//String docOdbiorcaId = "1"; // firma Dareks

		String cfgDoc = String.valueOf(mapCfg.get(btnCfg.getSelectedItem()));

		List<String> list = Arrays.asList(nrDoc, docDelete, docView, dataDostawy, dataDoc, nrKontener, nrSamochod, cfgDoc, producent, dostawca, wlasciciel, odbiorca, uwagi);
		List<String> list2 = Utils.getColumnNamesWithoutID(model.getColumnListFrom("t_doc"));
		String querry2 = Utils.getSqlValuesStringFromList(list, "t_doc", list2);

		model.executeUpdate(querry2);

	}

	public void populateContrahents(List<String> producent,List<String> dostawca,List<String> wlasciciel,List<String> odbiorca) {
		Utils.addToComboBox(btnDostawca, dostawca);
		Utils.addToComboBox(btnProducent, producent);
		Utils.addToComboBox(btnWlasciciel, wlasciciel);
		Utils.addToComboBox(btnOdbiorca, odbiorca);
	}

	private void getRSForCombobox() {

		try {
			rsCfg = model.executeQuerry("select * from v_cfg_doc_pz;");
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), " " + e.getMessage());
		}
	}

	public void enterUpdateState() {
		btnCfg.setEnabled(false);
		saveBtn.setText("UPDATE");
		saveBtn.setActionCommand("TDOC_UPDATE");
	}

	public void setCfgEnabled(boolean bol) {
		btnCfg.setEnabled(bol);
	}

	public void newDoc() {
		saveBtn.setText("Save");
		saveBtn.setActionCommand("TDOC_SAVE");
		btnCfg.setEnabled(true);
		textNrDoc.setText("");
		btnCfg.clear();
		saveDocBtn.setEnabled(true);
	}

	public String getSelectedCfg() {
		return btnCfg.getSelectedItem().toString();
	}

	public void clearContrahents() {
		Utils.removeAllFromComboBox(btnDostawca);
		Utils.removeAllFromComboBox(btnProducent);
		Utils.removeAllFromComboBox(btnWlasciciel);	
		Utils.removeAllFromComboBox(btnOdbiorca);	
	}

	public void setContrahMappings(Map<String, Integer> mapProd, Map<String, Integer> mapDost, Map<String, Integer> mapWlasc, Map<String, Integer> mapOdb) {
		btnDostawca.setMap(mapDost);
		btnProducent.setMap(mapProd);
		btnWlasciciel.setMap(mapWlasc);
		btnOdbiorca.setMap(mapOdb);
	}

	public void setSaveDocEnabled(boolean b) {
		saveDocBtn.setEnabled(false);
	}

}
