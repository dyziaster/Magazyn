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

import com.itextpdf.text.pdf.qrcode.Mode;

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

public class Document extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -573175262406727875L;
	private JPanel contentPane;
	private Tdocs tdocs;
	private Tdoc tdoc;
	private Ttable table;
	private DefaultTableModel tableModel;
	private Model model;
	// private JButton saveBtn;
	private List<String> idList;
	private boolean tdocSavedState = false;
	private boolean tdocsSavedState = false;
	private String t_doc_id;

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

		this.model = model;
		this.setTitle("Document");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 738, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(10, 15));

		// tableModel = Utils.getTableModelFromRS(model.executeQuerry("select *
		// from v_doc_view where doc_id ='"+xx+"';"));
		// List<String> ids =
		// Utils.getNthColumnRecordsFrom(model.executeQuerry("select id,Towar
		// from v_doc_s"), 1);
		// Utils.printResultSet(model.executeQuerry("select id,Towar from
		// v_doc_s"));
		table = new Ttable(this, null, null);
		tdoc = new Tdoc(this, model);
		tdocs = new Tdocs(this, model);
		contentPane.add(tdoc, BorderLayout.NORTH);
		contentPane.add(table, BorderLayout.CENTER);
		contentPane.add(tdocs, BorderLayout.SOUTH);

		// saveBtn = new JButton("save");
		// saveBtn.addActionListener(this);
		// contentPane.add(saveBtn, BorderLayout.AFTER_LINE_ENDS);

		setVisible(true);

		disablePanel(2);
		disablePanel(3);

	}

	public void enablePanel(int num) {
		switch (num) {
		case 1:

			break;
		case 2:
			table.enableTable();
			break;
		case 3:
			tdocs.enablePanelDocs();
			break;
		}
	}

	public void disablePanel(int num) {
		switch (num) {
		case 1:

			break;
		case 2:
			table.disableTable();
			break;
		case 3:
			tdocs.disablePanelDocs();
			break;
		}
	}

	public void setBtnTdocs(String name) {
		tdocs.setBtn(name);
	}

	public void writeTdocs(int rowId) {
		tdocs.writeTdocs(rowId);
	}

	public String getT_doc_id() {
		return t_doc_id;
	}

	public void setTdocsUpdate() {
		tdocs.enterUpdateState();
	}

	public void setIdToUpdate(String id) {
		tdocs.setIdToUpdate(id);
	}

	public void clearTdocs() {
		tdocs.clearComponents();
	}

	public void turnOffNewBtn() {
		tdocs.setNewBtnEnabled(false);
	}

	private void populateTowar() throws SQLException {
		String cfg = tdoc.getSelectedCfg();
		ResultSet rs = model.executeQuerry("select doc_view_lista_tow from t_cfg_doc where doc_nazwa ='" + cfg + "';");
		String view = Utils.getFirstRecordFromRS(rs);
		rs = model.executeQuerry("select Towar from " + view);
		List<String> list = Utils.getNthColumnRecordsFrom(rs, 1);
		tdocs.addToTowar(list);

	}

	private void populateMagazyn() throws SQLException {
		String cfg = tdoc.getSelectedCfg();
		ResultSet rs = model.executeQuerry("select doc_view_lista_magazynow from t_cfg_doc where doc_nazwa ='" + cfg + "';");
		String view = Utils.getFirstRecordFromRS(rs);
		rs = model.executeQuerry("select magazyn from " + view);
		List<String> list = Utils.getNthColumnRecordsFrom(rs, 1);
		tdocs.addToMagazyn(list);
	}

	public void refreshTable() throws SQLException {
		ResultSet rs = model.executeQuerry("select * from v_doc_s_view where doc_id ='" + t_doc_id + "';");
		System.out.println("print from refreshtable::::::::::::::::");
		Utils.printResultSet(rs);
		tableModel = Utils.getTableModelFromRS(rs);
		idList = Utils.getColumnRecordsFrom(rs, "id");
		table.setTableIdMap(idList);
		table.refreshTableModel(tableModel);
		for (int x = 0; x < idList.size(); x++) {
			if (idList.get(x).equals(this.getTdocsId()))
				table.setCursorPos(x);
		}
	}

	public void updateDoc() throws SQLException {

		StringBuilder sb = new StringBuilder("");
		sb.append("update t_doc set ");
		// String id = Utils.getFirstRecordFromRS(model.executeQuerry("SELECT
		// id_doc FROM t_doc WHERE id_doc = (SELECT MAX(id_doc) FROM t_doc);"));

		Component[] components = tdoc.getComponents();
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

	public void clearTable() {
		table.clear();
	}

	public void setTdocsNew() {
		tdocs.enterSaveState();
	}

	public void setTdocsSaveUpdate() {
		tdocs.stateUpdateSave();
	}

	public void setTdocsNewState() {
		tdocs.stateNew();
	}

	public void manage(String action) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		switch (command) {
		case "TDOC_NEW":
			tdocNew();
			break;
		case "TDOC_SAVE":
			tdocSave();
			break;
		case "TDOC_UPDATE":
			tdocUpdate();
			break;
		case "TDOC_CFG":
			populateContrahents();
			break;
		case "TDOC_SAVEDOC":
			tdocSaveDoc();
			break;
		case "TDOCS_NEW":
			tdocsNew();
			break;
		case "TDOCS_SAVE":
			tdocsSave();
			break;
		case "TDOCS_UPDATE":
			tdocsUpdate();
			break;
		case "TABLE_EDIT":
			editTable();
			break;
		}
	}

	private void populateContrahents() {
		try {
			tdoc.clearContrahents();

			String cfg = tdoc.getSelectedCfg();
			if(cfg.equals(""))
				return;
			ResultSet rs = model.executeQuerry("select doc_producent from t_cfg_doc where doc_nazwa ='" + cfg + "';");
			String view = Utils.getFirstRecordFromRS(rs);
			rs = model.executeQuerry("select  id_kon,kon_nazwa from " + view);
			Map<String, Integer> mapProd = Utils.getIdNameMapFrom(rs);
			List<String> producent = Utils.getNthColumnRecordsFrom(rs, 2);
			rs = model.executeQuerry("select doc_dostawca from t_cfg_doc where doc_nazwa ='" + cfg + "';");
			view = Utils.getFirstRecordFromRS(rs);
			rs = model.executeQuerry("select  id_kon,kon_nazwa from " + view);
			Map<String, Integer> mapDost = Utils.getIdNameMapFrom(rs);
			List<String> dostawca = Utils.getNthColumnRecordsFrom(rs, 2);
			rs = model.executeQuerry("select doc_wlasciciel from t_cfg_doc where doc_nazwa ='" + cfg + "';");
			view = Utils.getFirstRecordFromRS(rs);
			rs = model.executeQuerry("select id_kon,kon_nazwa from " + view);
			Map<String, Integer> mapWlasc = Utils.getIdNameMapFrom(rs);
			List<String> wlasciciel = Utils.getNthColumnRecordsFrom(rs, 2);

			tdoc.populateContrahents(producent, dostawca, wlasciciel);
			tdoc.setContrahMappings(mapProd, mapDost, mapWlasc);

		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), "populate contrahents failed " + e.getMessage());
		}
	}

	private void tdocUpdate() {
		try {
			if (tdoc.inputValidated()) {
				this.updateDoc();
			}
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), "update document failed " + e.getMessage());
		}
	}

	private void tdocSaveDoc() {
		try {
			if (tdocSavedState == true)
				model.executeUpdate("update t_doc set nr_doc ='" + tdoc.generateNrdoc() + "' where id_doc='" + t_doc_id + "';");
		} catch (SQLException e) {
			Logger.e(Logger.getMethodName(), "save document failed " + e.getMessage());
		}
	}

	private void tdocSave() {
		try {
			if (tdoc.inputValidated()) {
				tdoc.saveDoc();
				tdoc.enterUpdateState();
				tdocSavedState = true;
				t_doc_id = this.getDocID();
				tdocs.stateNew();
				tdocs.clearComponents();
				this.populateMagazyn();
				this.populateTowar();
				this.enablePanel(2);
			}

		} catch (SQLException e1) {
			Logger.e(Logger.getMethodName(), "save failed " + e1.getMessage());
		}

	}

	private void tdocNew() {
		try {
			tdoc.newDoc();
			tdoc.clearContrahents();
			table.clear();
			tdocs.enterSaveState();
			tdocs.clearComponents();
			tdocs.clearTowarMagazyn();
			this.disablePanel(2);
			this.disablePanel(3);
			tdocSavedState = false;
		} catch (Exception e) {
			Logger.e(Logger.getMethodName(), "new doc failed " + e.getMessage());
		}
	}

	private void editTable() {
		try {
			String id = idList.get(table.getSelectedRow());
			tdocs.writeTdocs(Integer.valueOf(id));
			tdocs.setIdToUpdate(id);
			tdocs.enterUpdateState();
			tdocs.stateUpdateSave();
			this.disablePanel(2);
			this.enablePanel(3);
		} catch (Exception e) {
			Logger.e(Logger.getMethodName(), "edit failed " + e.getMessage());
		}
	}

	private void tdocsUpdate() {
		try {
			if (tdocs.inputValidated()) {
				tdocs.updateDocs(tdocs.getIdToUpdate());
				tdocs.stateNew();
				tdocs.enterSaveState();
				this.refreshTable();
				this.enablePanel(2);
			}
		} catch (Exception e) {
			Logger.e(Logger.getMethodName(), "update failed " + e.getMessage());
		}
	}

	private void tdocsNew() {
		try {
			tdocs.enterSaveState();
			tdocs.clearComponents();
			tdocs.setBtn("SAVE");
			tdocs.stateUpdateSave();
			this.disablePanel(2);
		} catch (Exception e) {
			Logger.e(Logger.getMethodName(), "new failed " + e.getMessage());
		}
	}

	private void tdocsSave() {
		try {
			if (tdocs.inputValidated()) {
				tdocs.saveDocs(this.t_doc_id);
				tdocs.enterUpdateState();
				tdocs.stateNew();
				this.refreshTable();
				this.enablePanel(2);
			}
		} catch (Exception e) {
			Logger.e(Logger.getMethodName(), "save failed " + e.getMessage());
		}
	}

	public String getTdocsId() {
		String id = tdocs.getIdToUpdate();
		return id;
	}

	public String getDocID() throws SQLException {
		return Utils.getFirstRecordFromRS(model.executeQuerry("SELECT id_doc FROM t_doc WHERE id_doc = (SELECT MAX(id_doc) FROM t_doc);"));
	}
}

enum State {
	canSaveTdocs, canUpdateTdocs, canNewTdocs, newTdoc,
}

class JmTextField extends JTextField implements Access {

	/**
	 * 
	 */
	private static final long serialVersionUID = -364423020863064232L;
	private boolean isAccessable = false;

	public boolean isAccessable() {
		return isAccessable;
	}

	public JmTextField() {

	}

	public JmTextField(JPanel panel, String name, int x, int y, FocusListener fl, boolean isAccessable) {

		this(panel, name, x, y, 1, 1, 1, fl, isAccessable);
	}

	public JmTextField(JPanel panel, String name, int x, int y, int w, int h, int weight, FocusListener fl, boolean isAccessable) {

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

	@Override
	public void clear() {
		this.setText("");
	}

}

class JmComboBox extends JComboBox implements Access {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7594717168239972528L;
	private boolean isQuerry = false;
	private boolean isAccessable = false;
	String querry;
	private Model model;
	private Map<String, Integer> map;

	public boolean isAccessable() {
		return isAccessable;
	}

	public JmComboBox() {
		this(false, null, null);
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	public JmComboBox(boolean isQuerry, Map<String, Integer> map, Model model) {
		super();
		this.isQuerry = isQuerry;
		this.isAccessable = isQuerry;
		this.model = model;
		this.map = map;
	}

	@Override
	public String getOutput() {
		String selected = super.getSelectedItem().toString();

		if (isQuerry && !selected.equals("")) {
			return map.get(selected).toString();
		} else {
			return selected;
		}
	}

	@Override
	public void clear() {
		if (this.getItemCount() > 0)
			Utils.setComboPosition(this,0);
	}

}

class JmDatePickerImpl extends JDatePickerImpl implements Access {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2335619846411523670L;
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

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}

class JmLabel extends JLabel implements Access {

	/**
	 * 
	 */
	private static final long serialVersionUID = 31538692719368829L;
	private boolean isAccessable = false;

	public boolean isAccessable() {
		return isAccessable;
	}

	public JmLabel(JPanel panel, String text, int x, int y) {

		this(panel, text, x, y, 1, 1, false);
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

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
