package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import common.DenomManipulationMethods;
import common.SafeTransfer;
import common.SessionManager;
import service.DBMoneyManipulationServ;
import view.CashDepositionPanel;

public class CashDepositionPanelController {
	CashDepositionPanel cashDepo;
	
	public CashDepositionPanelController(CashDepositionPanel cashDepo) 
	{
		this.cashDepo = cashDepo;
		initialize();
	}
	private static Map<String, CashDepositionPanelController> cache = new HashMap<>();
	public static CashDepositionPanelController getCashDepositionPanelController(String sessionId) 
	{
		if(!cache.containsKey(sessionId)) 
		{
			CashDepositionPanelController cashDepoPanelContr = new CashDepositionPanelController(CashDepositionPanel.getCashDepositionPanel(sessionId));
			cache.put(sessionId, cashDepoPanelContr);
		}
		return cache.get(sessionId);
		
	}
	public void initialize() 
	{
		 this.cashDepo.addFocusListeneOnTxtFields(new AddFocusListenerOnTxtFields());
		this.cashDepo.addActionListenerOnConfirmBtn(new AddActionListenerOnConfirmBtn());
	}
	class AddFocusListenerOnTxtFields implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			int empId = SessionManager.getEmpIdBySession(SessionManager.getCurrSess());
			double result = DenomManipulationMethods.subCalculator(cashDepo.getDenomPanelInstance(), empId);
			cashDepo.setTotal(result);
			
		}
		
	}
	class AddActionListenerOnConfirmBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			int empId = SessionManager.getEmpIdBySession(SessionManager.getCurrSess());
			double total = DBMoneyManipulationServ.getCashRegSum(empId, LocalDate.now());
			if(cashDepo.getTotal()>total) 
			{
				JOptionPane.showMessageDialog(null, "Money sufficiency in cash register.");
			}
			else 
			{
				boolean isWhat = false;
				SafeTransfer.SafeTransferMethod(cashDepo.getDenomPanelInstance(), empId, isWhat);
				System.out.println("predaja u sef(da bude 'false'): "+isWhat);
				cashDepo.setConfirmBtnOnFalse();
				
			}
		}
		
	}
	

}
