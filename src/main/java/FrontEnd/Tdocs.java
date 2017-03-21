package FrontEnd;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import Model.Logger;
import Model.Model;
import Model.Utils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Tdocs extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2701160318677915682L;
	/**
	 * 
	 */
	private Model model;
	private JmTextField wagaNetto;
	private JmTextField iloscSzt;
	private JmTextField razem;
	private JmTextField wagaRyby;
	private JmTextField wagaBrutto;
	private JmTextField podsumowanie;
	private JPanel panelDocs;
	private JmTextField nrPartiiZew;
	private JmTextField nrPartiiWew;
	private JmTextField kod1;
	private JmTextField kod2;
	private JmDatePickerImpl dataPolowu;
	private JmDatePickerImpl dataZamrozenia;
	private JmDatePickerImpl dataProdukcji;
	private JmComboBox nazwaTowaru;
	private JmComboBox magazyn;
	private JButton save2Btn;
	private String idToUpdate;
	private Document document;
	private UtilDateModel m3;
	private UtilDateModel m2;
	private UtilDateModel m;
	private JmTextField trwalosc;
	private JmTextArea uwagi;

	public Tdocs() {
		this(null, null);
	}

	public Tdocs(final Document document, final Model model) { // final ????

		this.document = document;
		this.model = model;
		panelDocs = this;

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelDocs.setLayout(gbl_panel);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		nazwaTowaru = new JmComboBox(true, null, model);
		nazwaTowaru.addActionListener(document);
		nazwaTowaru.setActionCommand("TDOCS_TOWAR");
		nazwaTowaru.setName("doc_s_nazwa_tow_id");
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		panelDocs.add(nazwaTowaru, gbc);

		magazyn = new JmComboBox(true, null, model);
		magazyn.setName("doc_s_magazyn_id");
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		panelDocs.add(magazyn, gbc);
		save2Btn = new JButton("save");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(5, 0, 5, 0);
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.gridx = 4;
		gbc_button.gridy = 7;
		panelDocs.add(save2Btn, gbc_button);
		save2Btn.setActionCommand("TDOCS_SAVE");
		save2Btn.addActionListener(document);
		m = new UtilDateModel();
		m.setValue(new Date());
		JDatePanelImpl datePanel = new JDatePanelImpl(m);
		dataPolowu = new JmDatePickerImpl(datePanel);
		dataPolowu.setName("doc_s_data_polowu");
		gbc.gridx = 2;
		gbc.gridy = 3;
		panelDocs.add(dataPolowu, gbc);

		m2 = new UtilDateModel();
		m2.setValue(new Date());
		JDatePanelImpl datePanel2 = new JDatePanelImpl(m2);
		dataZamrozenia = new JmDatePickerImpl(datePanel2);
		dataZamrozenia.setName("doc_s_data_zamrozenia");
		gbc.gridx = 2;
		gbc.gridy = 5;
		panelDocs.add(dataZamrozenia, gbc);

		m3 = new UtilDateModel();
		m3.setValue(new Date());
		JDatePanelImpl datePanel3 = new JDatePanelImpl(m3);
		dataProdukcji = new JmDatePickerImpl(datePanel3);
		dataProdukcji.setName("doc_s_data_produkcji");
		gbc.gridx = 2;
		gbc.gridy = 7;
		panelDocs.add(dataProdukcji, gbc);

		nrPartiiZew = new JmTextField(panelDocs, "doc_s_nr_partii_zw", 0, 5, null, true);
		nrPartiiWew = new JmTextField(panelDocs, "doc_s_nr_partii_wew", 0, 7, null, true);
		kod1 = new JmTextField(panelDocs, "doc_s_kod_kreskowy_1", 1, 3, null, true);
		kod2 = new JmTextField(panelDocs, "doc_s_kod_kreskowy_2", 1, 5, null, true);
		trwalosc = new JmTextField(panelDocs, "doc_s_data_trwalosci", 1, 7, null, true);
		uwagi = new JmTextArea(panelDocs, "doc_s_uwagi",5 , 4, 3, 2, 3, 1, null, true);
		FocusListener fl = new TextFocusListener();
		wagaNetto = new JmTextField(panelDocs, "doc_s_waga_netto_op", 3, 1, fl, true);// A
		iloscSzt = new JmTextField(panelDocs, "doc_s_ilosc_szt_op", 4, 1, fl, true);// B
		razem = new JmTextField(panelDocs, "razem", 5, 1, 1, 1, 3, fl, false);// C
		wagaRyby = new JmTextField(panelDocs, "doc_s_waga_ryby", 3, 3, fl, true);
		wagaBrutto = new JmTextField(panelDocs, "doc_s_waga_brutto", 3, 5, fl, true);// D
		podsumowanie = new JmTextField(panelDocs, "", 3, 7, fl, false);// E

		new JmLabel(panelDocs, "Towar", 0, 0, 3, 1, false);
		new JmLabel(panelDocs, "Waga Netto", 3, 0);
		new JmLabel(panelDocs, "Ilosc", 4, 0);
		new JmLabel(panelDocs, "Razem", 5, 0);
		new JmLabel(panelDocs, "Magazyn", 0, 2);
		new JmLabel(panelDocs, "kod kreskowy 1", 1, 2);
		new JmLabel(panelDocs, "data polowu", 2, 2);
		new JmLabel(panelDocs, "waga ryby", 3, 2);
		new JmLabel(panelDocs, "uwagi", 4, 2);
		new JmLabel(panelDocs, "Nr partii zew", 0, 4);
		new JmLabel(panelDocs, "kod kreskowy 2", 1, 4);
		new JmLabel(panelDocs, "data zamrozenia", 2, 4);
		new JmLabel(panelDocs, "Waga brutto", 3, 4);
		new JmLabel(panelDocs, "Waga total", 3, 6);
		new JmLabel(panelDocs, "Nr partii wew", 0, 6);
		new JmLabel(panelDocs, "data trwalosci", 1, 6);
		new JmLabel(panelDocs, "data produkcji", 2, 6);

	}

	public void fillTowarCombo(Iterable<?> list) {
		Utils.removeAllFromComboBox(nazwaTowaru);
		Utils.addToComboBox(nazwaTowaru, list);
	}

	public void fillMagazynCombo(Iterable<?> list) {
		Utils.removeAllFromComboBox(magazyn);
		Utils.addToComboBox(magazyn, list);
	}

	public void disablePanelDocs() {
		for (Component c : panelDocs.getComponents()) {
			if (c instanceof JDatePickerImpl)
				((JDatePickerImpl) c).getComponent(1).setEnabled(false);
			c.setEnabled(false);
		}

	}

	public void enablePanelDocs() {
		for (Component c : panelDocs.getComponents()) {
			if (c instanceof JDatePickerImpl)
				((JDatePickerImpl) c).getComponent(1).setEnabled(true);
			c.setEnabled(true);
		}
	}

	public void saveDocs(String t_doc_id) throws SQLException {

		List<String> columns = model.getColumnNamesWithoutID("t_doc_s");
		
		String docId = t_doc_id;
		String delete = "0";
		String view = "1";
		String magId = magazyn.getOutput();
		String partiaZew = nrPartiiZew.getOutput();
		String partiaWew = nrPartiiWew.getOutput();
		String towId = nazwaTowaru.getOutput();
		String dataPolowu = this.dataPolowu.getOutput();
		String dataZamrozenia = this.dataZamrozenia.getOutput();
		String dataProdukcji = this.dataProdukcji.getOutput();
		String dataTrwalosci = this.trwalosc.getOutput();
		String netto = wagaNetto.getOutput();
		String waga = wagaRyby.getOutput();
		String brutto = wagaBrutto.getOutput();
		String szt = iloscSzt.getOutput();
		String uwag = uwagi.getOutput();
		String kod1 = this.kod1.getOutput();
		String kod2 = this.kod2.getOutput();
		List<String> values = Arrays.asList(docId, delete, view, magId, partiaZew, partiaWew, towId, dataPolowu, dataZamrozenia, dataProdukcji, dataTrwalosci, netto, waga, brutto, szt, kod1, kod2, uwag, "-1");
		String querry = Utils.getSqlValuesStringFromList(values, "t_doc_s", columns);
		System.out.println("size v=" + values.size() + " size col=" + columns.size());
		System.out.println("columns : " + columns);
		model.executeUpdate(querry);
		idToUpdate = Utils.getFirstRecordFromRS(model.executeQuerry("SELECT id FROM t_doc_s WHERE id = (SELECT MAX(id) FROM t_doc_s);"));
		model.executeUpdate("update t_doc_s set doc_s_join_doc ='"+idToUpdate+"' where id ='"+idToUpdate+"';");

	}

	public void refreshTable() throws SQLException {
		document.refreshTable();
	}

	public void setIdToUpdate(String idToUpdate) {
		this.idToUpdate = idToUpdate;
	}

	public String getIdToUpdate() {
		return idToUpdate;
	}

	public void setBtn(String name) {
		save2Btn.setText(name);
		if (name.equals("Save")) {
			save2Btn.setActionCommand("TDOCS_SAVE");
		} else if (name.equals("Update"))
			save2Btn.setActionCommand("TDOCS_UPDATE");
	}

	public void enterUpdateState() {
		setBtn("Update");
	}

	public void enterSaveState() {
		setBtn("Save");
	}
	public void updateDocs(String t_doc_id) throws SQLException {

		StringBuilder sb = new StringBuilder("");
		sb.append("update t_doc_s set ");
		String id = t_doc_id;

		Component[] components = panelDocs.getComponents();
		int i = 1;
		for (Component c : components) {
			if (c instanceof Access) {
				if (((Access) c).isAccessable()) {
					if (i != 1)
						sb.append(",");
					sb.append(c.getName());
					sb.append("='");
					sb.append(((Access) c).getOutput());
					sb.append("'");
					i++;
				}
			}
		}

		sb.append("where id=" + id);
		model.executeUpdate(sb.toString());
	}

	public void resetWeights() {
		wagaBrutto.setText("");
		wagaNetto.setText("");
		wagaRyby.setText("");
		podsumowanie.setText("");
		iloscSzt.setText("");
		razem.setText("");
	}

	public class TextFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

		}

		@Override
		public void focusLost(FocusEvent e) {

			try {

				String name = e.getComponent().getName();
				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);
				
				switch (name) {

				case "doc_s_waga_netto_op":
					double net = Double.valueOf(wagaNetto.getText());
					double ilosc = Double.valueOf(iloscSzt.getText());
					razem.setText(String.format("%.2f", net * ilosc));
					break;
				case "doc_s_ilosc_szt_op":
					net = Double.valueOf(wagaNetto.getText());
					ilosc = Double.valueOf(iloscSzt.getText());
					double d = net * ilosc;
					razem.setText(String.valueOf(Utils.round(d, 2)));
					break;
				case "razem":
					net = Double.valueOf(wagaNetto.getText());
					double total = Double.valueOf(razem.getText());
					d= total/net;
					iloscSzt.setText(String.valueOf(Utils.round(d, 2)));
					break;
				case "doc_s_waga_ryby":
					break;
				case "doc_s_waga_brutto":
					break;
				case "podsumowanie":
					break;
				}

				double brutto = Double.valueOf(wagaBrutto.getText());
				double ilosc = Double.valueOf(iloscSzt.getText());
				Double d= ilosc * brutto;
				podsumowanie.setText(String.valueOf(Utils.round(d, 2)));

			} catch (Exception ee) {
				ee.printStackTrace();
				Logger.e(Logger.getMethodName(), "Error - " + ee.getMessage());
			}

		}
	}

	public void clearComponents() {
		Component[] components = panelDocs.getComponents();

		for (Component c : components) {
			if (c instanceof Access) {
				((Access) c).clear();
			}
		}
	}

	public boolean inputValidated() {
		Component[] components = panelDocs.getComponents();

		for (Component c : components) {
			if (c instanceof Access) {
				if (((Access) c).isAccessable()) {
					if (((Access) c).getOutput().equals("")) {
						Logger.e(Logger.getMethodName(), c.getName() + " is empty");
						return false;
					}
				}
			}
		}
		return true;
	}

	public void writeTdocs(int rowId) throws NumberFormatException, SQLException {

		ResultSet rs = model.executeQuerry("select * from t_doc_s where id = '" + rowId + "';");
		ResultSet rs1 = model.executeQuerry("select Towar from v_towar_show where id_tow = (select doc_s_nazwa_tow_id from t_doc_s where id = '" + rowId + "');");
		ResultSet rs2 = model.executeQuerry("select magazyn from v_magazyn where id_ = (select doc_s_magazyn_id from t_doc_s where id = '" + rowId + "');");
		ResultSet rs3 = model.executeQuerry("select razem from v_doc_s_view where id = '" + rowId + "';");

		while (rs.next()) {
			nrPartiiWew.setText(rs.getString(nrPartiiWew.getName()));
			nrPartiiZew.setText(rs.getString(nrPartiiZew.getName()));
			wagaNetto.setText(rs.getString(wagaNetto.getName()));
			wagaBrutto.setText(rs.getString(wagaBrutto.getName()));
			wagaRyby.setText(rs.getString(wagaRyby.getName()));
			kod1.setText(rs.getString(kod1.getName()));
			kod2.setText(rs.getString(kod2.getName()));
			iloscSzt.setText(rs.getString(iloscSzt.getName()));
			uwagi.setText(rs.getString(uwagi.getName()));
			razem.setText(Utils.getFirstRecordFromRS(rs3));
			trwalosc.setText(rs.getString(trwalosc.getName()));
			
			String[] date = rs.getString("doc_s_data_polowu").split("-");
			int y = Integer.valueOf(date[0]);
			int mm = Integer.valueOf(date[1]) - 1;
			int d = Integer.valueOf(date[2]);
			m.setDate(y, mm, d);
			date = rs.getString("doc_s_data_zamrozenia").split("-");
			y = Integer.valueOf(date[0]);
			mm = Integer.valueOf(date[1]) - 1;
			d = Integer.valueOf(date[2]);
			m2.setDate(y, mm, d);
			date = rs.getString("doc_s_data_produkcji").split("-");
			y = Integer.valueOf(date[0]);
			mm = Integer.valueOf(date[1]) - 1;
			d = Integer.valueOf(date[2]);
			m3.setDate(y, mm, d);

			nazwaTowaru.setSelectedItem(Utils.getFirstRecordFromRS(rs1));
			magazyn.setSelectedItem(Utils.getFirstRecordFromRS(rs2));

		}

	}

	public void addToTowar(List<String> list) {
		Utils.addToComboBox(nazwaTowaru, list);
	}

	public void addToMagazyn(List<String> list) {
		Utils.addToComboBox(magazyn, list);
	}

	public void clearTowarMagazyn() {
		Utils.removeAllFromComboBox(magazyn);
		Utils.removeAllFromComboBox(nazwaTowaru);
	}

	public void setTowarMap(Map<String, Integer> mapTowar) {
		nazwaTowaru.setMap(mapTowar);
	}

	public void setMagazynMap(Map<String, Integer> mapMagazyn) {
		magazyn.setMap(mapMagazyn);
	}

	public void setWeights(String wagaRyby, String netto, String brutto) {
		this.wagaRyby.setText(wagaRyby);
		this.wagaRyby.grabFocus();
		this.wagaNetto.setText(netto);
		this.wagaBrutto.setText(brutto);
		this.iloscSzt.grabFocus();
		
	}
}
