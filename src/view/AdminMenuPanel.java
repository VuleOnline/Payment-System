package view;

import javax.swing.JPanel;

import common.Logout;
import controller.AdminMenuPanelController;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;




public class AdminMenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton empListBtn;
	private JButton orderListBtn;
	public static AdminMenuPanel instance;
	
	private AdminMenuPanel() {
		initialize();
	}
	
	 public static AdminMenuPanel getInstance() {
	    	if (instance == null) {
	            instance = new AdminMenuPanel();
	    	}
	        return instance;
	    }
	

	
	 public void initialize() {
		setLayout(null);
		this.setPreferredSize(new Dimension(768, 524));
		Logout logout = Logout.getInstance();
		JButton btn = logout.logout();
		add(btn);
		
		
		empListBtn = new JButton("<html><center>Employee</center></br><center>list</center><html>");
		empListBtn.setBounds(325, 143, 130, 35);
		add(empListBtn);
		
		orderListBtn = new JButton("<html><center>Order</center></br><center>list</center><html>");
		orderListBtn.setBounds(325, 220, 130, 35);
		add(orderListBtn);
		
		new AdminMenuPanelController(this);
		
		
	

	}
	

        public void addActionListenerOnEmpList(ActionListener listener) 
        {
        	empListBtn.addActionListener(listener);
        }
        public void addActionListenerOnOrderList(ActionListener listener) 
        {
        	orderListBtn.addActionListener(listener);
        }
    
}
