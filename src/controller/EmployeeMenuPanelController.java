package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.CashRegPanel;
import view.ChangeRegPanel;
import view.EmployeeMenuPanel;
import view.Frame;

public class EmployeeMenuPanelController {
	EmployeeMenuPanel empMenuPanel;
	
	
	public EmployeeMenuPanelController(EmployeeMenuPanel empMenuPanel) 
	{
		this.empMenuPanel = empMenuPanel;
		initialize();
	}
	
	
	public void initialize() 
	{
		this.empMenuPanel.addActionListenerOnCashRegBtn(new AddActionListenerOnCashRegBtn());
		this.empMenuPanel.addActionListenerOnChangeRegBtn(new AddActionListenerOnChangeRegBtn());
	}
	class AddActionListenerOnCashRegBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			CashRegPanelController crpc = CashRegPanelController.getInstance();
			CashRegPanel cr = CashRegPanel.getInstance();
			cr.removeAll();
			cr.initialize();
			crpc.initialize();
			cr.revalidate();
			cr.repaint();
			Frame.getInstance().showCard("cashRegPanel");
			
		}
		
	}
	class AddActionListenerOnChangeRegBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			ChangeRegPanel crp = ChangeRegPanel.getInstance();
			ChangeRegPanelController crc = ChangeRegPanelController.getInstance();
			crp.removeAll();
			crp.initialize();
			crc.initialize();
			crp.revalidate();
			crp.repaint();
			Frame.getInstance().showCard("changeRegPanel");
		}
		
	}

}
