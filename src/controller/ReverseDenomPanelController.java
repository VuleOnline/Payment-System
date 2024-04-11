package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import common.CommonMethods;
import common.SessionManager;
import model.OrdersModel;
import service.DBMoneyManipulationServ;
import service.DBOrderManipulationServ;
import view.CashRegPanel;
import view.Frame;
import view.ReverseDenomPanel;

public class ReverseDenomPanelController {
	private ReverseDenomPanel revDenom;
	
	private ReverseDenomPanelController(ReverseDenomPanel revDenom) 
	{
		this.revDenom = revDenom;
		initialize();
	}
	
	private static Map<String, ReverseDenomPanelController> cache = new HashMap<>();
	public static ReverseDenomPanelController getReverseDenomPanelController(String sessionId) {
		 if (!cache.containsKey(sessionId)) {
			 ReverseDenomPanelController revDenomPanelContr = new ReverseDenomPanelController(ReverseDenomPanel.getReverseDenomPanel(sessionId));
	        cache.put(sessionId, revDenomPanelContr);
		 }
	        return cache.get(sessionId);
   }
	
	public void initialize() 
	{
		OrdersModel order = DBOrderManipulationServ.selectOrderForReverse(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
		System.out.println("iznos naloga: "+order.getInvoiceAmt());
		this.revDenom.setTotal(order.getInvoiceAmt());
		this.revDenom.addFocusListeneOnTxtFields(new AddFocusListeneOnTxtFields());
		this.revDenom.addActionListenerOnFinBtn(new AddActionListenerOnFinBtn());
		
	}
	class AddFocusListeneOnTxtFields implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			int empId = SessionManager.getEmpIdBySession(SessionManager.getCurrSess());
			double result = CommonMethods.subCalculator(revDenom.getDenomPanelInstance(), empId);
			revDenom.setRev(revDenom.getTotal()-result);
		}
		
	}
	
	class AddActionListenerOnFinBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			String sessionId = SessionManager.getCurrSess();
			int empId = SessionManager.getEmpIdBySession(sessionId);
			if(revDenom.getRev()== 0.0) 
			{
				OrdersModel order = DBOrderManipulationServ.selectOrderForReverse(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
				 CommonMethods.subFromDenom(revDenom.getDenomPanelInstance(), empId);
				 DBMoneyManipulationServ.reverseOrderInAgg(order);
				 CashRegPanel cr = CashRegPanel.getCashRegPanel(sessionId);
			     CashRegPanelController crc = CashRegPanelController.getCashRegPanelController(sessionId);
			    	cr.removeAll();
			    	cr.initialize();
			    	crc.initialize();
			    	cr.revalidate();
			    	cr.repaint();
			        Frame.getInstance().showCard("cashRegPanel");
			}
			
		}
		
	}

}
