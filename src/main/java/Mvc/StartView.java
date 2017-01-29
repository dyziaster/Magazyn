package Mvc;

import java.awt.EventQueue;

public class StartView {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Model m = new Model();
					Controller c = new Controller(m);
					View window = new View(m,c);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
