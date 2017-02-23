package FrontEnd;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class Document extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -573175262406727875L;
	private JPanel contentPane;
	private JTextField textUwagi;
	private JTextField textNrSamochod;
	private JTextField textNrKontener;
	private JTextField textField_5;
	private JTextArea notes;
	private JDatePickerImpl datePickerDoc;
	private JTextField textNrDoc;
	private JDatePickerImpl datePickerDostawa;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JFrame frame;
	private App app;
	private Model model;
	private JComboBox btnProducent;
	private JComboBox btnDostawca;
	private JComboBox btnWlasciciel;
	private Map<String, Integer> map;
	private JComboBox btnCfg;

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

		ResultSet rs = model.executeQuerry("select * from t_cfg_doc;");
		map = Utils.getIdNameMapFrom(rs);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 738, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 15));

		JPanel panel = new JPanel();
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

		textNrDoc = new JTextField();
		datePickerDostawa = new JDatePickerImpl(datePanel);
		GridBagConstraints gbc_textNrDoc = new GridBagConstraints();
		gbc_textNrDoc.insets = new Insets(0, 0, 5, 5);
		gbc_textNrDoc.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNrDoc.gridx = 3;
		gbc_textNrDoc.gridy = 0;
		panel.add(textNrDoc, gbc_textNrDoc);
		textNrDoc.setColumns(10);
		
		UtilDateModel m2 = new UtilDateModel();
		m2.setValue(new Date());
		JDatePanelImpl datePanel2 = new JDatePanelImpl(m2);
		datePickerDoc = new JDatePickerImpl(datePanel2);
		// textField_7 = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 5);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 3;
		gbc_textField_7.gridy = 1;
		panel.add(datePickerDoc, gbc_textField_7);
		// date.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Nr kontrahenta");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 4;
		gbc_lblNewLabel_7.gridy = 0;
		panel.add(lblNewLabel_7, gbc_lblNewLabel_7);

		textNrKontener = new JTextField();
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

		textNrSamochod = new JTextField();
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
		for(String s : map.keySet())
		btnCfg.addItem(s);
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

		textUwagi = new JTextField();
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

		btnProducent = new JComboBox();
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

		btnDostawca = new JComboBox();
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

		btnWlasciciel = new JComboBox();
		GridBagConstraints gbc_btnWlasciciel = new GridBagConstraints();
		gbc_btnWlasciciel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnWlasciciel.gridx = 5;
		gbc_btnWlasciciel.gridy = 3;
		panel.add(btnWlasciciel, gbc_btnWlasciciel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		cancelBtn = new JButton("cancel");
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				frame.dispose();
			}
		});
		panel_1.add(cancelBtn);

		saveBtn = new JButton("save");
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveDoc();
			}
		});
		panel_1.add(saveBtn);

		JPanel panel_2 = new JPanel();

		notes = new JTextArea();
		// notes.setColumns(10);
		// notes.setRows(10);
		// output.setLineWrap(true);
		notes.setWrapStyleWord(true);
		DefaultCaret carret = (DefaultCaret) notes.getCaret();
		carret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		panel_2.setLayout(new BorderLayout());
		JScrollPane scrollJTextArea = new JScrollPane(notes);
		scrollJTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_2.add(scrollJTextArea, BorderLayout.CENTER);

		contentPane.add(panel_2, BorderLayout.CENTER);

		JLabel lblNewLabel_11 = new JLabel("Notes");
		panel_2.add(lblNewLabel_11, BorderLayout.NORTH);
		setVisible(true);

		populateComboBoxes();
	}

	private void saveDoc() {
		ResultSet rs = null;
		String nrDoc,producent, dostawca, wlasciciel, nrKontener, nrSamochod, uwagi = "";
		String dataDoc, dataDostawy = "";
		producent = btnProducent.getSelectedItem().toString();
		dostawca = btnDostawca.getSelectedItem().toString();
		wlasciciel = btnWlasciciel.getSelectedItem().toString();
		nrKontener = textNrKontener.getText();
		nrSamochod = textNrSamochod.getText();
		uwagi = textUwagi.getText();
		nrDoc = textNrDoc.getText();

	    Date selectedDate = (Date) datePickerDoc.getModel().getValue();
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataDoc = df.format(selectedDate);

	    Date selectedDate2 = (Date) datePickerDostawa.getModel().getValue();
		dataDostawy =  df.format(selectedDate2);
		
		

		rs = model.executeQuerry("select id_kon from v_kontrahent_doc where kon_nazwa ='"+producent+"';");
		producent = Utils.getFirstRecordFromRS(rs);
		rs = model.executeQuerry("select id_kon from v_kontrahent_doc where kon_nazwa ='"+dostawca+"';");
		dostawca = Utils.getFirstRecordFromRS(rs);
		rs = model.executeQuerry("select id_kon from v_kontrahent_doc where kon_nazwa ='"+wlasciciel+"';");
		wlasciciel = Utils.getFirstRecordFromRS(rs);

		String docDelete = "0";
		String docView = "1";
		String docOdbiorcaId = "1"; // firma Dareks
		
		String cfgDoc = String.valueOf(map.get(btnCfg.getSelectedItem()));
		
		List<String> list = Arrays.asList(nrDoc,docDelete,docView,dataDostawy,dataDoc,nrKontener,nrSamochod,cfgDoc,producent,dostawca,dostawca,wlasciciel,docOdbiorcaId,uwagi);
		//rs = model.executeQuerry("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='t_doc';");
		//List<String> list2 =  Utils.getNthColumnRecordsFrom(rs, 1);
		//System.out.println("ta allfasldsald"+list2);
		
		String values = "('"+nrDoc+"','"+docDelete+"','"+docView+"','"+dataDostawy+"','"+dataDoc+"','"+nrKontener+"','"+nrSamochod+"','"+cfgDoc+"','"+producent+"','"+dostawca+"','"+wlasciciel+"','"+docOdbiorcaId+"','"+uwagi+"')";
		String querry2 = Utils.getSqlValuesStringFromList(list, "t_doc");
		String querry = "INSERT INTO t_doc (nr_doc,doc_delete,doc_view,doc_data_dostawy,doc_data_doc,doc_nr_kontenera,doc_nr_samochodu,doc_cfg_doc_id,doc_producent_id,doc_dostawca_id,doc_wlasciciel_id,doc_odbiorca_id,doc_uwagi) VALUES "+values+";";

		
		Logger.i(Logger.getMethodName(),querry);
		model.executeUpdate(querry);
		dispose();
	}

	private void populateComboBoxes() {
		String querry = "SELECT kon_nazwa FROM v_kontrahent_doc";
		ResultSet rs = model.executeQuerry(querry);
		for (String s : Utils.getNthColumnRecordsFrom(rs, 1)) {
			btnDostawca.addItem(s);
			btnProducent.addItem(s);
			btnWlasciciel.addItem(s);
		}
	}
}
