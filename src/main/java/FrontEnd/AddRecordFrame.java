package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddRecordFrame extends JDialog {

	private static final int sizeConstant = 3;
	private List<JTextField> listTextField = new ArrayList<>();
	private App app;

	public AddRecordFrame() {
		this(null, null);
	}

	public AddRecordFrame(App app, Object[] columnNames) {
		this.app = app;
		this.setLayout(new GridBagLayout());
		this.setModal(true);
		this.setAlwaysOnTop(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setUndecorated(true);
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		initialize(columnNames);
	}

	private void initialize(Object[] columnNames) {

		System.out.println("...............................................INITIALIZE");
		
		JPanel panel = new JPanel();
		if (columnNames != null) {
			System.out.println("...............................................ADDING BUTTONS");
			for (int i = 0; i < columnNames.length; i++) {
				JLabel lbl = new JLabel(columnNames[i].toString());
				panel.add(lbl);
				JTextField tf  = new JTextField("",10);
				panel.add(tf);
				listTextField.add(tf);
				//tf.setColumns(10);
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
	
	public List<String> getResponse(){
		List<String> list = new ArrayList<>();
		for(JTextField s : listTextField)
		list.add(s.getText());
		return list;
	}

}
