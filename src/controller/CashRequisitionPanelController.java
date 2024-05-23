package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import common.DenomManipulationMethods;
import common.SafeTransfer;
import common.SessionManager;
import view.CashRequisitionPanel;

public class CashRequisitionPanelController {

	private CashRequisitionPanel cashRequiPanel;
	
	public CashRequisitionPanelController(CashRequisitionPanel cashRequiPanel)
	{
		this.cashRequiPanel = cashRequiPanel;
		initialize();
		
	}
	private static Map<String, CashRequisitionPanelController> cache = new ConcurrentHashMap<>();
	public static CashRequisitionPanelController getCashRequisitionPanelController(String sessionId) 
	{
		if(!cache.containsKey(sessionId)) 
		{
			CashRequisitionPanelController cashReqPanelContr = new CashRequisitionPanelController(CashRequisitionPanel.getCashRequisitionPanel(sessionId));
			cache.put(sessionId, cashReqPanelContr);
		}
		return cache.get(sessionId);
		
	}
	public void initialize() 
	{
		this.cashRequiPanel.addFocusListeneOnTxtFields(new AddFocusListenerOnTxtFields());
		this.cashRequiPanel.addActionListenerOnConfirmBtn(new AddActionListenerOnConfirmBtn());
	}
	class AddFocusListenerOnTxtFields implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			double result = DenomManipulationMethods.addCalculator(cashRequiPanel.getDenomPanelInstance());
			cashRequiPanel.setTotal(result);
			
		}
	}
	class AddActionListenerOnConfirmBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			int empId = SessionManager.getEmpIdBySession(SessionManager.getCurrSess());
			boolean isWhat = true;
			 SafeTransfer.SafeTransferMethod(cashRequiPanel.getDenomPanelInstance(), empId, isWhat);
			 cashRequiPanel.setConfirmBtnOnFalse();
			
		}
		
	}
	
			
		
		
	
}
