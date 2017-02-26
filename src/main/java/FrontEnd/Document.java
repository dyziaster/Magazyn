package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

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
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
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
	private JTextArea notes;
	private JmDatePickerImpl datePickerDoc;
	private JLabel textNrDoc;
	private JmDatePickerImpl datePickerDostawa;
	private JButton saveBtn;
	private JButton cancelBtn;
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
	private boolean saved;

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

		saved = false;
		ResultSet rs = model.executeQuerry("select * from t_cfg_doc;");
		map = Utils.getIdNameMapFrom(rs);

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
		for (String s : map.keySet())
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

		btnProducent = new JmComboBox();
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

		btnDostawca = new JmComboBox();
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

		btnWlasciciel = new JmComboBox();
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

				cancel();
			}
		});
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_1.add(cancelBtn);

		saveBtn = new JButton("save");
		saveBtn.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				if (saved == false) {
					generateNrdoc();
					saveDoc();
				}
				else
					updateDoc();
			}
		});
		panel_1.add(saveBtn);

		clearBtn = new JButton("clear");
		clearBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearDoc();
			}
		});
		panel_1.add(clearBtn);

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

		btnDostawca.setName("doc_dostawca_id");
		btnProducent.setName("doc_producent_id");
		btnWlasciciel.setName("doc_wlasciciel_id");
		textNrKontener.setName("doc_nr_kontenera");
		textNrSamochod.setName("doc_nr_samochodu");
		textUwagi.setName("doc_uwagi");
		datePickerDoc.setName("doc_data_doc");
		datePickerDostawa.setName("doc_data_dostawy");

	}

	private void cancel() {
		model.executeQuerry("grant insert,update on t_doc to PUBLIC");		
		this.dispose();
	}

	private void clearDoc() {
		// TODO Auto-generated method stub

	}

	private void generateNrdoc() {

		String id_cfg_doc = String.valueOf(map.get(btnCfg.getSelectedItem()));
		String querry = "select v_doc_nr.nr_doc from v_doc_nr where v_doc_nr.doc_cfg_doc_id=" + id_cfg_doc + ";";
		ResultSet rs = model.executeQuerry(querry);
		if (rs == null) {
			textNrDoc.setText("1");
		} else {
			textNrDoc.setText(Utils.getFirstRecordFromRS(rs));
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
				if(i != 1)
					sb.append(",");	
				sb.append(c.getName());
				sb.append("='");
				sb.append(((Access) c).getOutput());
				sb.append("'");
				i++;
			}
		}

		sb.append("where id_doc="+id);
		model.executeUpdate(sb.toString());
	}

	private void saveDoc() {
		saved = true;
		
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
		model.executeQuerry("revoke insert,update on t_doc from PUBLIC"); // RACE CONDITION <--------------------
		btnCfg.setEnabled(false);
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

	class JmTextField extends JTextField implements Access {

		@Override
		public String getOutput() {
			// TODO Auto-generated method stub
			return super.getText();
		}

	}

	class JmComboBox extends JComboBox implements Access {

		@Override
		public String getOutput() {
			ResultSet rs = model.executeQuerry("select id_kon from v_kontrahent_doc where kon_nazwa ='" + super.getSelectedItem() + "';");
			return Utils.getFirstRecordFromRS(rs);
		}

	}

	class JmDatePickerImpl extends JDatePickerImpl implements Access {

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

		@Override
		public String getOutput() {
			// TODO Auto-generated method stub
			return super.getText();
		}

	}
}
