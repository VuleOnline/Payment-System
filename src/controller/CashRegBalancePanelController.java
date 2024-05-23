package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JTextField;

import common.DenomManipulationMethods;
import common.SessionManager;
import dao.DBMoneyManipulationDao;
import dao.DBOrderManipulationDao;
import model.DenomEntryModel;
import model.OrdersModel;
import view.CashRegBalancePanel;
import view.Frame;

public class CashRegBalancePanelController {
	CashRegBalancePanel cashRegBalance;
	private CashRegBalancePanelController(CashRegBalancePanel cashRegBalance) 
	{
		this.cashRegBalance = cashRegBalance;
		initialize();
	}
	
private static Map<String, CashRegBalancePanelController> cache = new ConcurrentHashMap<>();
	
	public static CashRegBalancePanelController getCashRegBalancePanelController(String sessionId)
	{
		if(!cache.containsKey(sessionId)) 
		{
			CashRegBalancePanelController cashRegBalancePanel = new CashRegBalancePanelController(CashRegBalancePanel.getCashRegBalancePanel(sessionId));
			cache.put(sessionId, cashRegBalancePanel);
		}
		return cache.get(sessionId);
	}
	
	public void initialize() 
	{
		int empId = SessionManager.getEmpIdBySession(SessionManager.getCurrSess());
		OrdersModel order = DBOrderManipulationDao.selectOrderForReverse(empId, LocalDate.now());
		if(order==null) 
		{
			cashRegBalance.setSuficiency(0.0);
		}else this.cashRegBalance.setSuficiency(order.getInvoiceAmt());
		this.cashRegBalance.addActionLIstenerOnCloseBtn(new AddActionLIstenerOnCloseBtn());
		txtFieldSet();
		this.cashRegBalance.setTotal(DBMoneyManipulationDao.getCashRegSum(empId, LocalDate.now()));
	}
	
	class AddActionLIstenerOnCloseBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			Frame.getInstance().showCard("cashRegPanel");
			
		}
		
	}
	public void txtFieldSet() 
	{
		int empId = SessionManager.getEmpIdBySession(SessionManager.getCurrSess());
		HashMap<String, String> list = DenomManipulationMethods.fieldMap(cashRegBalance.getDenomPanelInstance());
		HashMap<String, String> labelList =DenomManipulationMethods.labelValue();
		for(Map.Entry<String, String> entry : list.entrySet()) 
		{
			String filedName = entry.getKey();
			JTextField field = DenomManipulationMethods.getJTextField(cashRegBalance.getDenomPanelInstance(), filedName);
			String value = list.get(field.getName());
			String label = labelList.get(value);
			int denom = Integer.parseInt(label);
			DenomEntryModel denom_obj = new DenomEntryModel(denom, empId, LocalDate.now());
			int denom_quant = DBMoneyManipulationDao.geDenomQuant(denom_obj);
			field.setText(String.valueOf(denom_quant));
		}
					
	}


}
