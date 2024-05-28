package view;

import javax.swing.JPanel;

import common.Logout;
import controller.AdminMenuPanelController;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;




public class AdminMenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton empListBtn;
	private JButton orderListBtn;
	public static AdminMenuPanel instance;
	private JButton aggOrdersBtn;
	
	private AdminMenuPanel() {
		initialize();
	}
	private static Map<String, AdminMenuPanel> cache = new ConcurrentHashMap<>();
	
	 public static AdminMenuPanel getAdminMenuPanel(String sessionId) 
	    {
		 if(!cache.containsKey(sessionId)) 
			{
			 AdminMenuPanel adminMenuPanel  = new AdminMenuPanel();
				cache.put(sessionId, adminMenuPanel);
			}
			return cache.get(sessionId);
	    }
	

	
	 public void initialize() {
		setLayout(null);
		this.setPreferredSize(new Dimension(768, 524));
		Logout logout = Logout.getInstance();
		JButton btn = logout.logout();
		add(btn);
		
		
		empListBtn = new JButton("<html><center>Employee</center></br><center>list</center><html>");
		empListBtn.setBounds(325, 117, 130, 35);
		add(empListBtn);
		
		orderListBtn = new JButton("<html><center>Order</center></br><center>list</center><html>");
		orderListBtn.setBounds(325, 188, 130, 35);
		add(orderListBtn);
		
		aggOrdersBtn = new JButton("<html><center>Aggregate</center></br><center>orders</center><html>");
		aggOrdersBtn.setBounds(325, 255, 130, 35);
		add(aggOrdersBtn);
		
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
        
        public void addActionListenerOnAggOrdersBtn(ActionListener listener) 
        {
        	aggOrdersBtn.addActionListener(listener);
        }
}
