package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultCaret;

import Model.Logger;
import Model.Model;
import Model.Utils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.FlowLayout;

public class Document extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -573175262406727875L;
	private JPanel contentPane;
	private JmTextField textUwagi;
	private JmTextField textNrSamochod;
	private JmTextField textNrKontener;
	private JTextField textField_5;
	private JmDatePickerImpl datePickerDoc;
	private JLabel textNrDoc;
	private JmDatePickerImpl datePickerDostawa;
	private JButton saveBtn;
	private JButton closeBtn;
	private JFrame frame;
	private App app;
	private Model model;
	private JmComboBox btnProducent;
	private JmComboBox btnDostawca;
	private JmComboBox btnWlasciciel;
	private Map<String, Integer> map;
	private JComboBox btnCfg;
	private JButton clearBtn;
	private JPanel panel;
	private boolean savedDoc;
	private JTable table;
	private TableColumnModel tcm;
	private JLabel sum1;
	private JLabel sum2;
	private JmTextField wagaNetto;
	private JmTextField iloscSzt;
	private JmTextField razem;
	private JmTextField wagaRyby;
	private JmTextField wagaBrutto;
	private JmTextField podsumowanie;
	private Map<String, Integer> mapTowar;
	private Map<String, Integer> mapMagazyn;
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
	private boolean savedDocs;
	private JButton save2Btn;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Document frame = new Document(new App(), new Model());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Document() {
		this(null, null);
	}

	public Document(App app, Model model) {
		frame = this;
		this.model = model;
		this.app = app;

		savedDoc = false;
		savedDocs = false;
		
		ResultSet rs = model.executeQuerry("select * from t_cfg_doc;");
		map = Utils.getIdNameMapFrom(rs);

		table = new JTable();
		tcm = table.getColumnModel();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollTable = new JScrollPane(table);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 738, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 15));

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

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

		UtilDateModel m = new UtilDateModel();
		m.setValue(new Date());
		JDatePanelImpl datePanel = new JDatePanelImpl(m);

		textNrDoc = new JLabel();
		datePickerDostawa = new JmDatePickerImpl(datePanel);
		GridBagConstraints gbc_textNrDoc = new GridBagConstraints();
		gbc_textNrDoc.insets = new Insets(0, 0, 5, 5);
		gbc_textNrDoc.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNrDoc.gridx = 3;
		gbc_textNrDoc.gridy = 0;
		panel.add(textNrDoc, gbc_textNrDoc);

		UtilDateModel m2 = new UtilDateModel();
		m2.setValue(new Date());
		JDatePanelImpl datePanel2 = new JDatePanelImpl(m2);
		datePickerDoc = new JmDatePickerImpl(datePanel2);
		// textField_7 = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 5);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 3;
		gbc_textField_7.gridy = 1;
		panel.add(datePickerDoc, gbc_textField_7);
		// date.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Nr kontenera");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 4;
		gbc_lblNewLabel_7.gridy = 0;
		panel.add(lblNewLabel_7, gbc_lblNewLabel_7);

		textNrKontener = new JmTextField();
		GridBagConstraints gbc_textNrKontener = new GridBagConstraints();
		gbc_textNrKontener.insets = new Insets(0, 0, 5, 0);
		gbc_textNrKontener.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNrKontener.gridx = 5;
		gbc_textNrKontener.gridy = 0;
		panel.add(textNrKontener, gbc_textNrKontener);
		textNrKontener.setColumns(10);

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

		textNrSamochod = new JmTextField();
		GridBagConstraints gbc_textNrSamochod = new GridBagConstraints();
		gbc_textNrSamochod.insets = new Insets(0, 0, 5, 0);
		gbc_textNrSamochod.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNrSamochod.gridx = 5;
		gbc_textNrSamochod.gridy = 1;
		panel.add(textNrSamochod, gbc_textNrSamochod);
		textNrSamochod.setColumns(10);

		btnCfg = new JComboBox();
		GridBagConstraints gbc_btnCfg = new GridBagConstraints();
		gbc_btnCfg.insets = new Insets(0, 0, 5, 5);
		gbc_btnCfg.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCfg.gridx = 1;
		gbc_btnCfg.gridy = 2;
		Utils.addToComboBox(btnCfg, map.keySet());
		panel.add(btnCfg, gbc_btnCfg);

		JLabel lblNewLabel_5 = new JLabel("Data dostawy");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 2;
		gbc_lblNewLabel_5.gridy = 2;
		panel.add(lblNewLabel_5, gbc_lblNewLabel_5);

		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 2;
		panel.add(datePickerDostawa, gbc_textField_5);
		textField_5.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Uwagi");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 4;
		gbc_lblNewLabel_9.gridy = 2;
		panel.add(lblNewLabel_9, gbc_lblNewLabel_9);

		textUwagi = new JmTextField();
		GridBagConstraints gbc_textUwagi = new GridBagConstraints();
		gbc_textUwagi.insets = new Insets(0, 0, 5, 0);
		gbc_textUwagi.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUwagi.gridx = 5;
		gbc_textUwagi.gridy = 2;
		panel.add(textUwagi, gbc_textUwagi);
		textUwagi.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Producent:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		btnProducent = new JmComboBox(true,"select id_kon from v_kontrahent_doc where kon_nazwa =");
		GridBagConstraints gbc_btnProducent = new GridBagConstraints();
		gbc_btnProducent.insets = new Insets(0, 0, 0, 5);
		gbc_btnProducent.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProducent.gridx = 1;
		gbc_btnProducent.gridy = 3;
		panel.add(btnProducent, gbc_btnProducent);

		JLabel lblNewLabel_6 = new JLabel("Dostawca");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_6.gridx = 2;
		gbc_lblNewLabel_6.gridy = 3;
		panel.add(lblNewLabel_6, gbc_lblNewLabel_6);

		btnDostawca = new JmComboBox(true,"select id_kon from v_kontrahent_doc where kon_nazwa =");
		GridBagConstraints gbc_btnDostawca = new GridBagConstraints();
		gbc_btnDostawca.insets = new Insets(0, 0, 0, 5);
		gbc_btnDostawca.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDostawca.gridx = 3;
		gbc_btnDostawca.gridy = 3;
		panel.add(btnDostawca, gbc_btnDostawca);

		JLabel lblNewLabel_10 = new JLabel("W\u0142a\u015Bciciel");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_10.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_10.gridx = 4;
		gbc_lblNewLabel_10.gridy = 3;
		panel.add(lblNewLabel_10, gbc_lblNewLabel_10);

		btnWlasciciel = new JmComboBox(true,"select id_kon from v_kontrahent_doc where kon_nazwa =");
		GridBagConstraints gbc_btnWlasciciel = new GridBagConstraints();
		gbc_btnWlasciciel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnWlasciciel.gridx = 5;
		gbc_btnWlasciciel.gridy = 3;
		panel.add(btnWlasciciel, gbc_btnWlasciciel);

		closeBtn = new JButton("close");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(5, 0, 5, 0);
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.gridx = 4;
		gbc_button.gridy = 4;
		panel.add(closeBtn, gbc_button);
		closeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				close();
			}
		});

		saveBtn = new JButton("save");
		gbc_button.gridx = 5;
		panel.add(saveBtn, gbc_button);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (savedDoc == false) {
					generateNrdoc();
					saveDoc();
				} else
					updateDoc();
			}
		});

//		clearBtn = new JButton("clear");
//		gbc_button.gridx = 1;
//		panel.add(clearBtn, gbc_button);
//		clearBtn.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				clearDoc();
//			}
//		});

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		JLabel lblNotes = new JLabel("Notes");
		panel_2.add(lblNotes, BorderLayout.NORTH);
		panel_2.add(scrollTable, BorderLayout.CENTER);
		JPanel panel_3 = new JPanel();
		sum1 = new JLabel("");
		sum2 = new JLabel("");
		panel_3.add(sum1);
		panel_3.add(sum2);
		panel_2.add(panel_3, BorderLayout.SOUTH);

		contentPane.add(panel_2, BorderLayout.CENTER);
		contentPane.add(this.makePanel3(), BorderLayout.SOUTH);
		populateComboBoxes();
		setVisible(true);

		btnDostawca.setName("doc_dostawca_id");
		btnProducent.setName("doc_producent_id");
		btnWlasciciel.setName("doc_wlasciciel_id");
		textNrKontener.setName("doc_nr_kontenera");
		textNrSamochod.setName("doc_nr_samochodu");
		textUwagi.setName("doc_uwagi");
		datePickerDoc.setName("doc_data_doc");
		datePickerDostawa.setName("doc_data_dostawy");

	}

	private JPanel makePanel3() {
		panelDocs = new JPanel();

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelDocs.setLayout(gbl_panel);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		nazwaTowaru = new JmComboBox(true,"select id_tow from v_towar_show where Towar =");
		nazwaTowaru.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cbox = (JComboBox) e.getSource();
				String item = cbox.getSelectedItem().toString();
				if(item.equals("")){
					resetWeights();
					return;	
				}
				int id = mapTowar.get(item);
				ResultSet rs = model.executeQuerry("select * from v_towar_show where id_tow=" + id + ";");

				try {
					while (rs.next()) {
						wagaRyby.setText(rs.getString("tow_waga_ryby"));
						wagaRyby.grabFocus();
						wagaNetto.setText(rs.getString("tow_waga_netto"));
						wagaBrutto.setText(rs.getString("tow_waga_brutto"));
						iloscSzt.grabFocus();
					}
				} catch (SQLException e1) {
					Logger.e(Logger.getMethodName(), e1.getMessage());
				}
			}
		});
		nazwaTowaru.setName("doc_s_nazwa_tow_id");
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		panelDocs.add(nazwaTowaru, gbc);
		ResultSet rs = model.executeQuerry("select * from v_towar_show");
		mapTowar = Utils.getIdNameMapFrom(rs);

		Utils.addToComboBox(nazwaTowaru, mapTowar.keySet());

		magazyn = new JmComboBox(true,"select id_ from v_magazyn where mag_nr =");
		magazyn.setName("doc_s_magazyn_id");
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		panelDocs.add(magazyn, gbc);
		rs = model.executeQuerry("select * from v_magazyn");
		mapMagazyn = Utils.getIdNameMapFrom(rs);
		
		Utils.addToComboBox(magazyn, mapMagazyn.keySet());

		save2Btn = new JButton("save");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(5, 0, 5, 0);
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.gridx = 4;
		gbc_button.gridy = 7;
		panelDocs.add(save2Btn, gbc_button);
		save2Btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (savedDocs == false) {
					saveDocs();
				} else
					updateDocs();
			}
		});

		UtilDateModel m = new UtilDateModel();
		m.setValue(new Date());
		JDatePanelImpl datePanel = new JDatePanelImpl(m);
		dataPolowu = new JmDatePickerImpl(datePanel);
		dataPolowu.setName("doc_s_data_polowu");
		gbc.gridx = 2;
		gbc.gridy = 3;
		panelDocs.add(dataPolowu, gbc);

		UtilDateModel m2 = new UtilDateModel();
		m2.setValue(new Date());
		JDatePanelImpl datePanel2 = new JDatePanelImpl(m2);
		dataZamrozenia = new JmDatePickerImpl(datePanel2);
		dataZamrozenia.setName("doc_s_data_zamrozenia");
		gbc.gridx = 2;
		gbc.gridy = 5;
		panelDocs.add(dataZamrozenia, gbc);

		UtilDateModel m3 = new UtilDateModel();
		m3.setValue(new Date());
		JDatePanelImpl datePanel3 = new JDatePanelImpl(m3);
		dataProdukcji = new JmDatePickerImpl(datePanel3);
		dataProdukcji.setName("doc_s_data_produkcji");
		gbc.gridx = 2;
		gbc.gridy = 7;
		panelDocs.add(dataProdukcji, gbc);

		nrPartiiZew = new JmTextField(panelDocs, "doc_s_nr_partii_zw", 0, 5, null,true);
		nrPartiiWew = new JmTextField(panelDocs, "doc_s_nr_partii_wew", 0, 7, null,true);
		kod1 = new JmTextField(panelDocs, "doc_s_kod_kreskowy_1", 1, 3, null, true);
		kod2 = new JmTextField(panelDocs, "doc_s_kod_kreskowy_2", 1, 5, null, true);

		FocusListener fl = new TextFocusListener();
		wagaNetto = new JmTextField(panelDocs, "doc_s_waga_netto_op", 3, 1, fl, true);// A
		iloscSzt = new JmTextField(panelDocs, "doc_s_ilosc_szt_op", 4, 1, fl,true);// B
		razem = new JmTextField(panelDocs, "", 5, 1, 1, 1, 3, null, false);//C
		wagaRyby = new JmTextField(panelDocs, "doc_s_waga_ryby", 3, 3, fl, true);
		wagaBrutto = new JmTextField(panelDocs, "doc_s_waga_brutto", 3, 5, fl, true);// D
		podsumowanie = new JmTextField(panelDocs, "", 4, 5, fl, false);// E

		new JmLabel(panelDocs, "Towar", 0, 0, 3, 1,false);
		new JmLabel(panelDocs, "Waga Netto", 3, 0);
		new JmLabel(panelDocs, "Ilosc", 4, 0);
		new JmLabel(panelDocs, "Razem", 5, 0);
		new JmLabel(panelDocs, "Magazyn", 0, 2);
		new JmLabel(panelDocs, "kod kreskowy 1", 1, 2);
		new JmLabel(panelDocs, "data polowu", 2, 2);
		new JmLabel(panelDocs, "waga ryby", 3, 2);
		new JmLabel(panelDocs, "Nr partii zew", 0, 4);
		new JmLabel(panelDocs, "kod kreskowy 2", 1, 4);
		new JmLabel(panelDocs, "data zamrozenia", 2, 4);
		new JmLabel(panelDocs, "Waga brutto", 3, 4);
		new JmLabel(panelDocs, "Waga total", 4, 4);
		new JmLabel(panelDocs, "Nr partii wew", 0, 6);
		new JmLabel(panelDocs, "data produkcji", 2, 6);

		return panelDocs;
	}

	private void save2Doc() {

		StringBuilder sb = new StringBuilder("");
		StringBuilder sbb = new StringBuilder("");
		sb.append("insert into t_doc_s (");
		sbb.append(" values (");
		// String id = Utils.getFirstRecordFromRS(model.executeQuerry("SELECT id
		// FROM t_doc_s WHERE id = (SELECT MAX(id) FROM t_doc_s);"));

		Component[] components = panelDocs.getComponents();
		int i = 1;
		for (Component c : components) {
			if (c instanceof Access) {
				if(((Access) c).isAccessable() == false)
					continue ;
				if (i != 1) {
					sb.append(",");
					sbb.append(",");
				}
				sb.append(c.getName());
				sbb.append("'");
				sbb.append(((Access) c).getOutput());
				sbb.append("'");
				i++;
			}
		}

		sb.append(")");
		sbb.append(");");
		System.out.println(sb);
		System.out.println(sbb);
		sb.append(sbb);
		System.out.println(sb);
		model.executeUpdate(sb.toString());

	}

	private void saveDocs(){
		
		List<String> columns = model.getColumnNamesWithoutID("t_doc_s");
		
		String docId = "2";
		String delete = "0";
		String view = "1";
		String magId = magazyn.getOutput();
		String partiaZew = nrPartiiZew.getOutput();
		String partiaWew = nrPartiiWew.getOutput();
		String towId = nazwaTowaru.getOutput();
		String dataPolowu = this.dataPolowu.getOutput();
		String dataZamrozenia = this.dataZamrozenia.getOutput();
		String dataProdukcji = this.dataProdukcji.getOutput();
		String netto = wagaNetto.getOutput();
		String waga = wagaRyby.getOutput();
		String brutto = wagaBrutto.getOutput();
		String szt = iloscSzt.getOutput();
		String kod1 = this.kod1.getOutput();
		String kod2 = this.kod2.getOutput();
		String uwag = "uwaga";
		
		List<String> values = Arrays.asList(docId,delete,view,magId,partiaZew,partiaWew,towId,dataPolowu,dataZamrozenia,dataProdukcji,netto,waga,brutto,szt,kod1,kod2,uwag);
		String querry = Utils.getSqlValuesStringFromList(values, "t_doc_s", columns);
		System.out.println("size v="+values.size()+" size col="+columns.size());
		model.executeUpdate(querry);
		save2Btn.setText("UPDATE");
		
	}
	
	private void generateTableView() {

		String id = Utils.getFirstRecordFromRS(model.executeQuerry("SELECT id_doc FROM t_doc WHERE id_doc = (SELECT MAX(id_doc) FROM t_doc);"));
		String ID = textNrDoc.getText();
		ResultSet rs = model.executeQuerry("select * from v_doc_s t1 where t1.doc_id=" + id + ";");
		DefaultTableModel dtm = Utils.getTableModelFromRS(rs, model);
		this.setTableData(dtm);
		rs = model.executeQuerry("select sum(t1.doc_s_ilosc_szt_op) from v_doc_s t1 where t1.doc_id=" + id + " and t1.doc_s_delete=0");
		sum1.setText(Utils.getFirstRecordFromRS(rs));
		rs = model.executeQuerry("select sum(t1.doc_s_ilosc_szt_op * t1.doc_s_waga_netto_op) from v_doc_s t1 where t1.doc_id=" + id + " and t1.doc_s_delete=0");
		sum2.setText(Utils.getFirstRecordFromRS(rs));

	}

	public void setTableData(DefaultTableModel model) {

		TableColumnModel columnModel = table.getColumnModel();
		table.setModel(model);

		int lmax = 2;
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			lmax = model.getColumnName(i).toString().length();
			int lmaxx = 0;
			for (int j = 0; j < model.getRowCount(); j++) {
				Object value = this.getValueAt(j, i);
				if (value != null)
					lmaxx = value.toString().length();
				if (lmaxx > lmax)
					lmax = lmaxx;
			}
			columnModel.getColumn(i).setPreferredWidth(lmax * 8);
		}

		((DefaultTableModel) table.getModel()).fireTableDataChanged();
	}

	public String getValueAt(int row, int column) {
		Object value = table.getModel().getValueAt(row, column);
		if (value == null)
			return "";
		else
			return value.toString();
	}

	private void close() {
		// model.executeQuerry("grant insert,update on t_doc to PUBLIC");
		this.dispose();
	}

	private void clearDoc() {
		// TODO Auto-generated method stub

	}

	private void generateNrdoc() {

		String id_cfg_doc = String.valueOf(map.get(btnCfg.getSelectedItem()));
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
		String id = Utils.getFirstRecordFromRS(model.executeQuerry("SELECT id_doc FROM t_doc WHERE id_doc = (SELECT MAX(id_doc) FROM t_doc);"));

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

		sb.append("where id_doc=" + id);
		model.executeUpdate(sb.toString());
	}
	
	private void updateDocs() {

		StringBuilder sb = new StringBuilder("");
		sb.append("update t_doc_s set ");
		String id = Utils.getFirstRecordFromRS(model.executeQuerry("SELECT id FROM t_doc_s WHERE id = (SELECT MAX(id) FROM t_doc_s);"));

		Component[] components = panelDocs.getComponents();
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

		sb.append("where id=" + id);
		model.executeUpdate(sb.toString());
	}

	private void saveDoc() {
		savedDoc = true;

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

		String cfgDoc = String.valueOf(map.get(btnCfg.getSelectedItem()));

		List<String> list = Arrays.asList(nrDoc, docDelete, docView, dataDostawy, dataDoc, nrKontener, nrSamochod, cfgDoc, producent, dostawca, wlasciciel, docOdbiorcaId, uwagi);
		List<String> list2 = Utils.getColumnNamesWithoutID(model.getColumnListFrom("t_doc"));
		String querry2 = Utils.getSqlValuesStringFromList(list, "t_doc", list2);

		model.executeUpdate(querry2);
		// model.executeQuerry("revoke insert,update on t_doc from PUBLIC"); //
		// RACE CONDITION <--------------------
		btnCfg.setEnabled(false);
		saveBtn.setText("UPDATE");
	}

	private void populateComboBoxes() {
		String querry = "SELECT kon_nazwa FROM v_kontrahent_doc";
		ResultSet rs = model.executeQuerry(querry);
		
		Utils.addToComboBox(btnDostawca,  Utils.getNthColumnRecordsFrom(rs, 1));
		Utils.addToComboBox(btnProducent,  Utils.getNthColumnRecordsFrom(rs, 1));
		Utils.addToComboBox(btnWlasciciel,  Utils.getNthColumnRecordsFrom(rs, 1));
	}
	
	private void resetWeights(){
		wagaBrutto.setText("");
		wagaNetto.setText("");
		wagaRyby.setText("");
		podsumowanie.setText("");
		iloscSzt.setText("");
		razem.setText("");
	}

	class JmTextField extends JTextField implements Access {
		
		private boolean isAccessable = false;
		
		public boolean isAccessable() {
			return isAccessable;
		}
		public JmTextField() {

		}

		public JmTextField(JPanel panel, String name, int x, int y, FocusListener fl, boolean isAccessable) {

			this(panel, name, x, y, 1, 1, 1, fl, isAccessable);
		}

		public JmTextField(JPanel panel, String name, int x, int y, int w, int h, int weight,FocusListener fl,boolean isAccessable) {

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridwidth = w;
			gbc.gridheight = h;
			gbc.gridx = x;
			gbc.gridy = y;
			gbc.weightx = weight;
			this.setName(name);
			this.setActionCommand(name);
			this.addFocusListener(fl);
			this.isAccessable = isAccessable;
			panel.add(this, gbc);
		}

		@Override
		public String getOutput() {
			return super.getText();
		}

	}

	class JmComboBox extends JComboBox implements Access {

		private boolean isQuerry = false;
		private boolean isAccessable = false;
		String querry;
		
		public boolean isAccessable() {
			return isAccessable;
		}
		
		public JmComboBox() {
			this(false, "");
		}

		public JmComboBox(boolean isQuerry, String querry) {
			super();
			this.isQuerry = isQuerry;
			this.querry = querry;
		}

		@Override
		public String getOutput() {
			if (isQuerry) {
				ResultSet rs = model.executeQuerry(querry+"'" + super.getSelectedItem() + "';");
				return Utils.getFirstRecordFromRS(rs);
			}
			else{
				return super.getSelectedItem().toString();
			}
		}

	}

	class JmDatePickerImpl extends JDatePickerImpl implements Access {
		private boolean isAccessable = true;
		
		public boolean isAccessable() {
			return isAccessable;
		}
		public JmDatePickerImpl(JDatePanelImpl datePanel) {
			super(datePanel);
		}

		@Override
		public String getOutput() {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date) this.getModel().getValue();
			return df.format(date);
		}

	}

	class JmLabel extends JLabel implements Access {
		
		private boolean isAccessable = false;
		
		public boolean isAccessable() {
			return isAccessable;
		}

		public JmLabel(JPanel panel, String text, int x, int y) {

			this(panel, text, x, y, 1, 1,false);
		}

		public JmLabel(JPanel panel, String text, int x, int y, int w, int h, boolean isAccessable) {

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 5);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridwidth = w;
			gbc.gridheight = h;
			gbc.gridx = x;
			gbc.gridy = y;
			gbc.weightx = 1;
			this.isAccessable = isAccessable;
			this.setText(text);
			panel.add(this, gbc);
		}

		@Override
		public String getOutput() {
			return super.getText();
		}

	}

	public class TextFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

		}

		@Override
		public void focusLost(FocusEvent e) {

			try {

				String name = e.getComponent().getName();

				switch (name) {

				case "doc_s_waga_netto_op":
					double net = Double.valueOf(wagaNetto.getText());
					double ilosc = Double.valueOf(iloscSzt.getText());
					razem.setText(String.valueOf(net * ilosc));
					break;
				case "doc_s_ilosc_szt_op":
					net = Double.valueOf(wagaNetto.getText());
					ilosc = Double.valueOf(iloscSzt.getText());
					razem.setText(String.valueOf(net * ilosc));
					break;
				case "razem":
					net = Double.valueOf(wagaNetto.getText());
					double total = Double.valueOf(razem.getText());
					iloscSzt.setText(String.valueOf(total / net));
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
				podsumowanie.setText(String.valueOf(ilosc * brutto));

			} catch (Exception ee) {
				Logger.e(Logger.getMethodName(), "Error - " + ee.getMessage());
			}

		}
	}

}
