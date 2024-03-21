package main;
import java.awt.EventQueue;




import view.Frame;


public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
                    new Frame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
