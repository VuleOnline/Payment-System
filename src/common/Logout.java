package common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.LoginPanelController;
import view.Frame;
import view.LoginPanel;

public class Logout {

	private static Logout instance = new Logout();
	
	private Logout() 
	{
		
	}
	public static Logout getInstance() 
	{
		return instance;
		
	}
	
	public JButton logout() {
		JButton btn = new JButton("Logout");
		 btn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	 String currSess = SessionManager.getCurrSess();
	            	 if (currSess != null && SessionManager.removeCurrSession()) 
	            	 {
	            		LoginPanel lp = LoginPanel.getInstance();
	            		LoginPanelController lpc = LoginPanelController.getInstance();
	            		lp.removeAll();
	            		lp.initialize();
	            		lpc.initialize();
	            		lp.revalidate();
	            		lp.repaint();
	            	Frame.getInstance().showCard("loginPanel");
	            	}
	            }
	        });
		 btn.setSize(75,20);
		return btn;

		
	}
}
