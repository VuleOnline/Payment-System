package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

import common.CommonMethods;
import common.SessionManager;
import model.SafeTransfers;
import service.DBMoneyManipulationServ;
import view.CashRequisitionPanel;

public class CashRequisitionPanelController {

	private CashRequisitionPanel cashRequiPanel;
	
	public CashRequisitionPanelController(CashRequisitionPanel cashRequiPanel)
	{
		this.cashRequiPanel = cashRequiPanel;
		initialize();
		
	}
	private static Map<String, CashRequisitionPanelController> cache = new HashMap<>();
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
		this.cashRequiPanel.addFocusListenerOnTxtFields(new AddFocusListenerOnTxtFields());
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
			double result = CommonMethods.addCalculator(cashRequiPanel.getDenomPanelInstance());
			cashRequiPanel.setTotal(result);
			
		}
	}
	class AddActionListenerOnConfirmBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			 depositDenom();
			 cashRequiPanel.setConfirmBtnOnFalse();
			
		}
		
	}
	
	public void depositDenom() 
	{
		int empId = SessionManager.getEmpIdBySession(SessionManager.getCurrSess());
		boolean isWithdrawal = true;
		HashMap<String, String> list = CommonMethods.fieldMap(cashRequiPanel.getDenomPanelInstance());
		HashMap<String, String> labelList =CommonMethods.labelValue();
		for(Map.Entry<String, String> entry : list.entrySet()) 
		{
			String filedName = entry.getKey();
			JTextField field = CommonMethods.getJTextField(cashRequiPanel.getDenomPanelInstance(), filedName);
			String value = list.get(field.getName());
			System.out.println("labela za "+field.getName()+"je: "+value);
			String label = labelList.get(value);
			double fieldTxt = field.getText().isEmpty() ? 0.0 : Double.parseDouble(field.getText());
			int denom = Integer.parseInt(label);
			int quantity = (int) fieldTxt;
			SafeTransfers st = new SafeTransfers(denom, quantity, empId, LocalDate.now(), isWithdrawal);
			DBMoneyManipulationServ.insertCashReqi(st);
		}
	}
			
		
		
	
}
