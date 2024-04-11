package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import common.SessionManager;
import view.CashRegPanel;
import view.CashRequisitionPanel;
import view.AggOrdersPanel;
import view.CashDepositionPanel;
import view.CashRegBalancePanel;
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
		this.empMenuPanel.addActionListenerOnAggOrderBtn(new AddActionListenerOnAggOrderBtn());
		this.empMenuPanel.addActionListenerOnCashReqBtn(new AddActionListenerOnCashReqBtn());
		this.empMenuPanel.addActionListenerOnCashDepoBtn(new AddActionListenerOnCashDepoBtn());
		this.empMenuPanel.addActionListenerOnCashBalBtn(new AddActionListenerOnCashBalBtn());
	}
	class AddActionListenerOnCashRegBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			String session = SessionManager.getCurrSess();
			CashRegPanel cr = CashRegPanel.getCashRegPanel(session);
			CashRegPanelController crpc = CashRegPanelController.getCashRegPanelController(session);
			cr.removeAll();
			cr.initialize();
			crpc.initialize();
			cr.revalidate();
			cr.repaint();
			Frame.getInstance().showCard("cashRegPanel");
			
		}
		
	}
	class AddActionListenerOnAggOrderBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			String sessionId = SessionManager.getCurrSess();
			AggOrdersPanel crp = AggOrdersPanel.getAggOrdersPanel(sessionId);
			AggOrdersPanelController crc = AggOrdersPanelController.getAggOrdersPanelController(sessionId);
			crp.removeAll();
			crp.initialize();
			crc.initialize();
			crp.revalidate();
			crp.repaint();
			Frame.getInstance().showCard("aggOrdersPanel");
		}
		
	}
	class AddActionListenerOnCashReqBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			CashRequisitionPanel crp = CashRequisitionPanel.getCashRequisitionPanel(SessionManager.getCurrSess());
			CashRequisitionPanelController crc = CashRequisitionPanelController.getCashRequisitionPanelController(SessionManager.getCurrSess());
			crp.removeAll();
			crp.initialize();
			crc.initialize();
			crp.revalidate();
			crp.repaint();
			Frame.getInstance().showCard("cashReqPanel");
			
		}
		
	}
	class AddActionListenerOnCashDepoBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			CashDepositionPanel crp =CashDepositionPanel.getCashDepositionPanel(SessionManager.getCurrSess());
			CashDepositionPanelController crc =CashDepositionPanelController.getCashDepositionPanelController(SessionManager.getCurrSess());
			crp.removeAll();
			crp.initialize();
			crc.initialize();
			crp.revalidate();
			crp.repaint();
			Frame.getInstance().showCard("cashDepoPanel");
			
		}
		
	}
	
	class AddActionListenerOnCashBalBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			CashRegBalancePanel crb = CashRegBalancePanel.getCashRegBalancePanel(SessionManager.getCurrSess());
			CashRegBalancePanelController crbc = CashRegBalancePanelController.getCashRegBalancePanelController(SessionManager.getCurrSess());
			crb.removeAll();
			crb.initialize();
			crbc.initialize();
			crb.revalidate();
			crb.repaint();
			Frame.getInstance().showCard("cashRegBalancePanel");
			
		}
		
	}

}
