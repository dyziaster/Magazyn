package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.Logger;
import Model.Model;
import Model.Utils;

public class AddRecordFrame extends JDialog {

	private static final int sizeConstant = 3;
	private List<JTextField> listTextField = new ArrayList<>();
	private Model model;
	private Map<String, Integer> map;

	public AddRecordFrame(Model model, List<String> columnNames, Map<String, String> foreignKeys) {
		this.model = model;
		this.setLayout(new GridBagLayout());
		this.setModal(true);
		this.setAlwaysOnTop(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		// this.setUndecorated(true);
		// this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		initialize(columnNames, foreignKeys);
	}

	private void initialize(List<String> columnNames, Map<String, String> foreignKeys) {

		Logger.i("...............................................INITIALIZE");

		// if any column is FK
		// search for values from that table,
		// columns from constant

		JPanel panel = new JPanel();
		if (columnNames != null) {
			Logger.i("...............................................ADDING BUTTONS");
			for (int i = 0; i < columnNames.size(); i++) {
				JLabel lbl = new JLabel(columnNames.get(i).toString());
				panel.add(lbl);
				String column = columnNames.get(i);
				if (foreignKeys.keySet().contains(column)) { // checking if	 column is	 foreignKey. if yes add combobox with records from	 primary column	 (secondcolumn)
					JTextField tf = new JTextField("", 10); // foreign key = map
															// (FKcolumn, PK table)
					panel.add(tf);
					listTextField.add(tf);

					String s = foreignKeys.get(column); // get table that key refers to
					ResultSet rs = model.executeQuerry("select * from " + s + ";"); // select
																					// all
																					// from
																					// table
					map = Utils.getIdNameMapFrom(rs); // maps ids and
														// descriptions from PK
														// table
					JComboBoxEx cBox = new JComboBoxEx(tf, map);
					for (String record : map.keySet())
						cBox.addItem(record); // adding
					cBox.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JComboBoxEx cbox = (JComboBoxEx) e.getSource();
							cbox.setTextField();
						}
					});
					panel.add(cBox);
				} else {
					JTextField tf = new JTextField("", 10);
					panel.add(tf);
					listTextField.add(tf);
				}
			}
		}

		JButton button = new JButton("add record");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();

			}
		});
		panel.add(button);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension(screenSize.width / sizeConstant, screenSize.height / sizeConstant));
		this.setContentPane(panel);

		setVisible(true);
	}

	public List<String> getResponse() {
		List<String> list = new ArrayList<>();

		for (JTextField s : listTextField) {
			String text = s.getText();
			if (text.equals("") || text == null)
				return null;
			list.add(text);
		}
		return list;
	}

}

@SuppressWarnings("serial")
class JComboBoxEx<E> extends JComboBox<E> {

	private JTextField textField;
	private Map<String, Integer> map;

	public JComboBoxEx() {
		this(null, null);
	}

	public JComboBoxEx(JTextField textField, Map<String, Integer> map) {
		super();
		this.textField = textField;
		this.map = map;
	}

	public void setTextField() {
		Object o = this.getSelectedItem();
		Integer i = this.map.get(o);
		String text = i.toString();
		this.textField.setText(text);
	}

}
