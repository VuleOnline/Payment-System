package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import common.SessionManager;
import model.AggTransactions;
import model.OrdersModel;
import service.DBMoneyManipulationServ;
import service.DBOrderManipulationServ;
import view.AggOrdersPanel;

public class AggOrdersPanelController {
	private AggOrdersPanel aggOrd;
	
	private AggOrdersPanelController(AggOrdersPanel aggOrd)
	{
		this.aggOrd = aggOrd;;
		initialize();
	}
	private static Map<String, AggOrdersPanelController> cache = new HashMap<>();
	public static AggOrdersPanelController getAggOrdersPanelController(String sessionId) 
	{
		if(!cache.containsKey(sessionId)) 
		{
			AggOrdersPanelController aggOrdersPanelContr = new AggOrdersPanelController(AggOrdersPanel.getAggOrdersPanel(sessionId));
			cache.put(sessionId, aggOrdersPanelContr);
		}
		return cache.get(sessionId);
	}
	public void initialize() 
	{
		this.aggOrd.addItemListenerMouseListenerOnComboBox(new AddItemListenerMouseListenerOnComboBox());
	}
	class AddItemListenerMouseListenerOnComboBox implements ItemListener
	{


			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) 
				{
					aggOrd.clearTable();
					String combValues = aggOrd.getComboBoxValue();
					AggTransactions change = DBMoneyManipulationServ.getOrder(combValues, SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
					aggOrd.setRcvdTxt(change.getRcvd());
					aggOrd.setTotalTxt(change.getTotal());
					aggOrd.setCommTxt(change.getComm());
					aggOrd.setChangeTxt(change.getChange());
					String[] comboValues = aggOrd.getComboBoxValue().split("-");
					int[] intValues = new int[comboValues.length];
					for (int i = 0; i < comboValues.length; i++) {
					    intValues[i] = Integer.parseInt(comboValues[i]);
					    
					    OrdersModel ord = DBOrderManipulationServ.getOrderBySno
					    		(intValues[i], SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
					    showConsignationInTable(ord);
					}
				}
			}
			
			public void showConsignationInTable(OrdersModel ord) 
			{
				aggOrd.addOrderInTable(ord.getsNo(), ord.getFullName(), ord.getAddress(),ord.getRefNo(), 
						ord.getRecAcc(), ord.getAmount(), ord.getComm(), ord.getInvoiceAmt(),
						ord.getOrderDate(), ord.getState(), ord.isReversed());
			}
	

		
	}


}
