package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import common.SessionManager;
import view.AdminAggOrdersPanel;
import view.AdminMenuPanel;
import view.EmployeeListPanel;
import view.Frame;
import view.OrderListPanel;

public class AdminMenuPanelController {
	
	AdminMenuPanel adminMenu;
	
	public AdminMenuPanelController(AdminMenuPanel adminMenu) 
	{
		this.adminMenu = adminMenu;
		this.adminMenu.addActionListenerOnEmpList(new AddActionListenerOnEmpList());
		this.adminMenu.addActionListenerOnOrderList(new AddActionListenerOnOrderList());
		this.adminMenu.addActionListenerOnAggOrdersBtn(new AddActionListenerOnAggOrdersBtn());
	}
	private static Map<String, AdminMenuPanelController> cache = new ConcurrentHashMap <>();
	 public static AdminMenuPanelController getAdminMenuPanelContr(String sessionId) 
	    {
		 if(!cache.containsKey(sessionId)) 
			{
			 AdminMenuPanelController adminMenuPanelContr  = new AdminMenuPanelController(AdminMenuPanel.getAdminMenuPanel(sessionId));
				cache.put(sessionId, adminMenuPanelContr);
			}
			return cache.get(sessionId);
	    }
	
	class AddActionListenerOnEmpList implements ActionListener
	{
		String sessionId = SessionManager.getCurrSess();
		@Override
		public void actionPerformed(ActionEvent e) {
			EmployeeListPanel elp = EmployeeListPanel.getEmpListPanel(sessionId);
			EmployeeListPanelController elpc = EmployeeListPanelController.getEmpListPanelContr(sessionId);
			elp.removeAll();
			elp.initialize();
			elpc.initialize();
			elp.revalidate();
			elp.repaint();
			Frame.getInstance().showCard("empListPanel");
		}
		
	}
	class AddActionListenerOnOrderList implements ActionListener
	{
		String sessionId = SessionManager.getCurrSess();
		@Override
		public void actionPerformed(ActionEvent e) {
			OrderListPanel olp = OrderListPanel.getOrderListPanel(sessionId);
			OrderListPanelController olpc = OrderListPanelController.getOrderListPanelContr(sessionId);
			olp.removeAll();
			olp.initialize();
			olpc.initialize();
			olp.revalidate();
			olp.repaint();
			Frame.getInstance().showCard("orderListPanel");
			
		}
		
	}
	
	class  AddActionListenerOnAggOrdersBtn implements ActionListener
	{
			String sessionId = SessionManager.getCurrSess();

			@Override
			public void actionPerformed(ActionEvent e) {
				AdminAggOrdersPanel aop = AdminAggOrdersPanel.getAdminAggOrdersPanel(sessionId);
				AdminAggOrdersPanelController aopc = AdminAggOrdersPanelController.getAdminAggOrdersPanelContr(sessionId);
				aop.removeAll();
				aop.initialize();
				aopc.initialize();
				aop.revalidate();
				aop.repaint();
				Frame.getInstance().showCard("adminAggOrderListPanel");
				
			}
			
			
		}
		
	

}
