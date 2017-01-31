package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TableDemo {
	private static JList list;
	private static TableColumn column;

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String sampleTab³es[] = { "tab1", "tab2", "tab3" };
		list = new JList(sampleTab³es);
		JScrollPane scrollList = new JScrollPane(list);
		JPanel listPanel = new JPanel();
		listPanel.add(scrollList);

		Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row1-Column1", "Row1-Column2", "Row1-Column3" }, { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row1-Column1", "Row1-Column2", "Row1-Column3" }, { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row1-Column1", "Row1-Column2", "Row1-Column3" }, { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row1-Column1", "Row1-Column2", "Row1-Column3" }, { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row1-Column1", "Row1-Column2", "Row1-Column3" }, { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row1-Column1", "Row1-Column2", "Row1-Column3" }, { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row1-Column1", "Row1-Column2", "Row1-Column3" }, { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
		Object columnNames[] = { "Column One", "Column Two", "Column Three" };
		//JTable table = new JTable(rowData, columnNames);
		// table.setPreferredScrollableViewportSize(new Dimension(500, 70));

		DefaultTableModel dtm = new DefaultTableModel(rowData, columnNames);
		JTable table = new JTable(dtm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < 3; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 2) {
				column.setPreferredWidth(250); // third column is bigger
			} else {
				column.setPreferredWidth(150);
			}
		}
		JScrollPane scrollPane = new JScrollPane(table);

		JPanel mainPanel = new JPanel();
		BorderLayout mgr = new BorderLayout();
		mgr.setVgap(15);
		mainPanel.setLayout(mgr);
		mainPanel.add(listPanel, BorderLayout.WEST);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		JTextArea jta = new JTextArea();
		jta.setPreferredSize(new Dimension(0, 60));
		JScrollPane scrollText = new JScrollPane(jta);
		mainPanel.add(scrollText, BorderLayout.PAGE_END);

		frame.setContentPane(mainPanel);

		frame.setSize(300, 150);
		frame.setVisible(true);

	}
}