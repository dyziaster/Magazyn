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
	private JButton saveBtn;

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

		//tableModel = Utils.getTableModelFromRS(model.executeQuerry("select * from v_doc_view where doc_id ='"+xx+"';"));
		//List<String> ids = Utils.getNthColumnRecordsFrom(model.executeQuerry("select id,Towar from v_doc_s"), 1);
		//Utils.printResultSet(model.executeQuerry("select id,Towar from v_doc_s"));
		table = new Ttable(this, null, null);
		tdoc = new Tdoc(this, model);
		tdocs = new Tdocs(this, model);
		contentPane.add(tdoc, BorderLayout.NORTH);
		contentPane.add(table, BorderLayout.CENTER);
		contentPane.add(tdocs, BorderLayout.SOUTH);
		
		saveBtn = new JButton("save");
		saveBtn.addActionListener(this);
	//	contentPane.add(saveBtn, BorderLayout.AFTER_LINE_ENDS);
		
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

	public String getDocId() {
		return tdoc.getT_doc_id();
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

	public void refreshTable() {
		tableModel = Utils.getTableModelFromRS(model.executeQuerry("select * from v_doc_s where doc_id ='"+getDocId()+"';"));
		table.refreshTableModel(tableModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.executeUpdate("update t_doc set nr_doc ='"+tdoc.generateNrdoc()+"' where id_doc='"+getDocId()+"';");
	}

	public void clearTable() {
		table.clear();		
	}

	public void setTdocsNew() {
tdocs.enterSaveState();		
	}

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
		this.setSelectedIndex(0);
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
