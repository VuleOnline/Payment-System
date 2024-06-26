package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import common.SessionManager;
import dao.DBMoneyManipulationDao;
import dao.DBOrderManipulationDao;
import model.AggTransactions;
import model.OrdersModel;
import view.AggOrdersPanel;

public class AggOrdersPanelController {
	private AggOrdersPanel aggOrd;
	
	private AggOrdersPanelController(AggOrdersPanel aggOrd)
	{
		this.aggOrd = aggOrd;;
		initialize();
	}
	private static Map<String, AggOrdersPanelController> cache = new ConcurrentHashMap<>();
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
					AggTransactions change = DBMoneyManipulationDao.getOrder(combValues, SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
					aggOrd.setRcvdTxt(change.getRcvd());
					aggOrd.setTotalTxt(change.getTotal());
					aggOrd.setCommTxt(change.getComm());
					aggOrd.setChangeTxt(change.getChange());
					String[] comboValues = aggOrd.getComboBoxValue().split("-");
					int[] intValues = new int[comboValues.length];
					for (int i = 0; i < comboValues.length; i++) {
					    intValues[i] = Integer.parseInt(comboValues[i]);
					    
					    OrdersModel ord = DBOrderManipulationDao.getOrderBySno
					    		(intValues[i], SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
					    showConsignationInTable(ord);
					}
				}
			}
			
			public void showConsignationInTable(OrdersModel ord) 
			{
				aggOrd.showOrderInTable(ord.getsNo(), ord.getFullName(), ord.getAddress(),ord.getRefNo(), 
						ord.getRecAcc(), ord.getAmount(), ord.getComm(), ord.getInvoiceAmt(),
						ord.getOrderDate(), ord.getState(), ord.isReversed());
			}
	

		
	}


}
