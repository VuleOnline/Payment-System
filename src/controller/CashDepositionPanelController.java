package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import common.CommonMethods;
import common.SessionManager;
import model.SafeTransfers;
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
			double result = CommonMethods.subCalculator(cashDepo.getDenomPanelInstance(), empId);
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
				boolean isWithdrawal = false;
				HashMap<String, String> list = CommonMethods.fieldMap(cashDepo.getDenomPanelInstance());
				HashMap<String, String> labelList =CommonMethods.labelValue();
				for(Map.Entry<String, String> entry : list.entrySet()) 
				{
					String filedName = entry.getKey();
					JTextField field = CommonMethods.getJTextField(cashDepo.getDenomPanelInstance(), filedName);
					String value = list.get(field.getName());
					System.out.println("labela za "+field.getName()+"je: "+value);
					String label = labelList.get(value);
					double fieldTxt = field.getText().isEmpty() ? 0.0 : Double.parseDouble(field.getText());
					int denom = Integer.parseInt(label);
					int quantity = (int) fieldTxt;
					SafeTransfers st = new SafeTransfers(denom, quantity, empId, LocalDate.now(), isWithdrawal);
					DBMoneyManipulationServ.insertCashReqi(st);
					cashDepo.setConfirmBtnOnFalse();
				}
			}
		}
		
	}
	

}
