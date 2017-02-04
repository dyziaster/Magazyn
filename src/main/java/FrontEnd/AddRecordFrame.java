package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AddRecordFrame extends JFrame {

	private static final int sizeConstant = 3;

	public AddRecordFrame() {
		this(null, null);
	}

	public AddRecordFrame(Object[] columnNames, List<Object> referenceList) {
		initialize(columnNames, referenceList);
	}

	private void initialize(Object[] columnNames, List<Object> referenceList) {

		System.out.println("...............................................INITIALIZE");
		
		JPanel panel = new JPanel();
	//	if (columnNames != null) {
			System.out.println("...............................................ADDING BUTTONS");
			for (int i = 0; i < 5; i++) {
				panel.add(new JButton());
			}
	//	}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setMinimumSize(new Dimension(screenSize.width / sizeConstant, screenSize.height / sizeConstant));
		this.getContentPane().add(panel);

		setVisible(true);
	}

}
