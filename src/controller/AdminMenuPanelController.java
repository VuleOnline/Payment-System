package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import view.AdminMenuPanel;
import view.EmployeeListPanel;
import view.Frame;

public class AdminMenuPanelController {
	
	AdminMenuPanel adminMenu;
	
	public AdminMenuPanelController(AdminMenuPanel adminMenu) 
	{
		this.adminMenu = adminMenu;
		this.adminMenu.addActionListenerOnEmpList(new AddActionListenerOnEmpList());
		this.adminMenu.addActionListenerOnOrderList(new AddActionListenerOnOrderList());
	}
	
	class AddActionListenerOnEmpList implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			EmployeeListPanel elp = EmployeeListPanel.getInstance();
			EmployeeListPanelController elpc = EmployeeListPanelController.getInstance();
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
		@Override
		public void actionPerformed(ActionEvent e) {
			Frame.getInstance().showCard("orderListPanel");
			
		}
		
	}

}
