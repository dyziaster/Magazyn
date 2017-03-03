package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Model.Logger;
import Model.Model;
import Model.Utils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Tdoc extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1791439212865423973L;
	private JmTextField textUwagi;
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
	private JTable table;
	private JLabel sum1;
	private JLabel sum2;
	private String t_doc_id;
	private Map<String, Integer> mapProducent;
	private Map<String, Integer> mapCfg;
	private ResultSet rs;
	
	
	
	public String getT_doc_id() {
		return t_doc_id;
	}



	public Tdoc( final Document document, Model model) {
		
		this.model = model;
		this.document = document;
		panel = this;
		savedDoc = false;

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


		textUwagi = new JmTextField();
		GridBagConstraints gbc_textUwagi = new GridBagConstraints();
		gbc_textUwagi.insets = new Insets(0, 0, 5, 0);
		gbc_textUwagi.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUwagi.gridx = 5;
		gbc_textUwagi.gridy = 2;
		panel.add(textUwagi, gbc_textUwagi);
		textUwagi.setColumns(10);


		rs = model.executeQuerry("select * from v_konrahent");
		mapProducent = Utils.getIdNameMapFrom(rs);
		btnProducent = new JmComboBox(true, mapProducent,model);
		GridBagConstraints gbc_btnProducent = new GridBagConstraints();
		gbc_btnProducent.insets = new Insets(0, 0, 0, 5);
		gbc_btnProducent.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProducent.gridx = 1;
		gbc_btnProducent.gridy = 3;
		panel.add(btnProducent, gbc_btnProducent);

		btnDostawca = new JmComboBox(true, mapProducent,model);
		GridBagConstraints gbc_btnDostawca = new GridBagConstraints();
		gbc_btnDostawca.insets = new Insets(0, 0, 0, 5);
		gbc_btnDostawca.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDostawca.gridx = 3;
		gbc_btnDostawca.gridy = 3;
		panel.add(btnDostawca, gbc_btnDostawca);


		btnWlasciciel = new JmComboBox(true, mapProducent,model);
		GridBagConstraints gbc_btnWlasciciel = new GridBagConstraints();
		gbc_btnWlasciciel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnWlasciciel.gridx = 5;
		gbc_btnWlasciciel.gridy = 3;
		panel.add(btnWlasciciel, gbc_btnWlasciciel);

		rs = model.executeQuerry("select * from t_cfg_doc;");
		mapCfg = Utils.getIdNameMapFrom(rs);
		btnCfg = new JmComboBox(true,mapCfg,model);
		GridBagConstraints gbc_btnCfg = new GridBagConstraints();
		gbc_btnCfg.insets = new Insets(0, 0, 5, 5);
		gbc_btnCfg.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCfg.gridx = 1;
		gbc_btnCfg.gridy = 2;
		Utils.addToComboBox(btnCfg, mapCfg.keySet());
		panel.add(btnCfg, gbc_btnCfg);


		saveBtn = new JButton("save");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(5, 0, 5, 0);
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.gridx = 4;
		gbc_button.gridy = 4;
		panel.add(saveBtn, gbc_button);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputValidated()) {
					if (savedDoc == false) {
						generateNrdoc();
						saveDoc();
						document.enablePanelDocs();
						document.clearTdocs();
						savedDoc = true;
					} else
						updateDoc();
				}
			}
		});

		newBtn = new JButton("New");
		gbc_button.gridx = 2;
		panel.add(newBtn, gbc_button);
		newBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newDoc();
				document.disablePanelDocs();
				clearComponents();
				savedDoc = false;
			}
		});

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		JLabel lblNotes = new JLabel("Notes");
		panel_2.add(lblNotes, BorderLayout.NORTH);
		//panel_2.add(scrollTable, BorderLayout.CENTER);
		JPanel panel_3 = new JPanel();
		sum1 = new JLabel("");
		sum2 = new JLabel("");
		panel_3.add(sum1);
		panel_3.add(sum2);
		panel_2.add(panel_3, BorderLayout.SOUTH);

		populateComboBoxes();
		setVisible(true);

		btnCfg.setName("doc_cfg_doc_id");
		btnDostawca.setName("doc_dostawca_id");
		btnProducent.setName("doc_producent_id");
		btnWlasciciel.setName("doc_wlasciciel_id");
		textNrKontener.setName("doc_nr_kontenera");
		textNrSamochod.setName("doc_nr_samochodu");
		textUwagi.setName("doc_uwagi");
		datePickerDoc.setName("doc_data_doc");
		datePickerDostawa.setName("doc_data_dostawy");

	}

	

	private void addLabels() {

		JLabel lblNewLabel = new JLabel("Odbiorca");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("PZ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridheight = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 0;
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
		gbc_lblNewLabel_10.gridx = 4;
		gbc_lblNewLabel_10.gridy = 3;
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

	protected boolean inputValidated() {
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

//	private void generateTableView() {
//
//		String id = Utils.getFirstRecordFromRS(model.executeQuerry("SELECT id_doc FROM t_doc WHERE id_doc = (SELECT MAX(id_doc) FROM t_doc);"));
//		String ID = textNrDoc.getText();
//		ResultSet rs = model.executeQuerry("select * from v_doc_s t1 where t1.doc_id=" + id + ";");
//		DefaultTableModel dtm = Utils.getTableModelFromRS(rs, model);
//		this.setTableData(dtm);
//		rs = model.executeQuerry("select sum(t1.doc_s_ilosc_szt_op) from v_doc_s t1 where t1.doc_id=" + id + " and t1.doc_s_delete=0");
//		sum1.setText(Utils.getFirstRecordFromRS(rs));
//		rs = model.executeQuerry("select sum(t1.doc_s_ilosc_szt_op * t1.doc_s_waga_netto_op) from v_doc_s t1 where t1.doc_id=" + id + " and t1.doc_s_delete=0");
//		sum2.setText(Utils.getFirstRecordFromRS(rs));
//
//	}



	private void close() {
		// model.executeQuerry("grant insert,update on t_doc to PUBLIC");
		document.dispose();
	}

	private void newDoc() {

		saveBtn.setText("Save");
		btnCfg.setEnabled(true);
	}

	private void generateNrdoc() {

		String id_cfg_doc = String.valueOf(mapCfg.get(btnCfg.getSelectedItem()));
		String querry = "select v_doc_nr.nr_doc from v_doc_nr where v_doc_nr.doc_cfg_doc_id=" + id_cfg_doc + ";";
		ResultSet rs = model.executeQuerry(querry);
		try {
			if (!rs.isBeforeFirst()) {
				textNrDoc.setText("1");
			} else {
				textNrDoc.setText(Utils.getFirstRecordFromRS(rs));
			}
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), e.getMessage());
		}

	}

	private void updateDoc() {

		StringBuilder sb = new StringBuilder("");
		sb.append("update t_doc set ");
		// String id = Utils.getFirstRecordFromRS(model.executeQuerry("SELECT
		// id_doc FROM t_doc WHERE id_doc = (SELECT MAX(id_doc) FROM t_doc);"));

		Component[] components = panel.getComponents();
		int i = 1;
		for (Component c : components) {
			if (c instanceof Access) {
				if (i != 1)
					sb.append(",");
				sb.append(c.getName());
				sb.append("='");
				sb.append(((Access) c).getOutput());
				sb.append("'");
				i++;
			}
		}

		sb.append("where id_doc=" + t_doc_id);
		model.executeUpdate(sb.toString());
	}

	private void saveDoc() {

		String nrDoc, producent, dostawca, wlasciciel, nrKontener, nrSamochod, uwagi = "";
		String dataDoc, dataDostawy = "";
		producent = btnProducent.getOutput();
		dostawca = btnDostawca.getOutput();
		wlasciciel = btnWlasciciel.getOutput();
		nrKontener = textNrKontener.getOutput();
		nrSamochod = textNrSamochod.getOutput();
		uwagi = textUwagi.getOutput();
		nrDoc = textNrDoc.getText();
		dataDoc = datePickerDoc.getOutput();
		dataDostawy = datePickerDostawa.getOutput();

		String docDelete = "0";
		String docView = "1";
		String docOdbiorcaId = "1"; // firma Dareks

		String cfgDoc = String.valueOf(mapCfg.get(btnCfg.getSelectedItem()));

		List<String> list = Arrays.asList(nrDoc, docDelete, docView, dataDostawy, dataDoc, nrKontener, nrSamochod, cfgDoc, producent, dostawca, wlasciciel, docOdbiorcaId, uwagi);
		List<String> list2 = Utils.getColumnNamesWithoutID(model.getColumnListFrom("t_doc"));
		String querry2 = Utils.getSqlValuesStringFromList(list, "t_doc", list2);

		model.executeUpdate(querry2);
		t_doc_id = Utils.getFirstRecordFromRS(model.executeQuerry("SELECT id_doc FROM t_doc WHERE id_doc = (SELECT MAX(id_doc) FROM t_doc);"));
		// model.executeQuerry("revoke insert,update on t_doc from PUBLIC"); //
		// RACE CONDITION <--------------------
		btnCfg.setEnabled(false);
		saveBtn.setText("UPDATE");
	}

	private void populateComboBoxes() {
		String querry = "SELECT kon_nazwa FROM v_kontrahent_doc";
		ResultSet rs = model.executeQuerry(querry);

		Utils.addToComboBox(btnDostawca, mapProducent.keySet());
		Utils.addToComboBox(btnProducent, mapProducent.keySet());
		Utils.addToComboBox(btnWlasciciel, mapProducent.keySet());
	}

}
