package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;


import common.SessionManager;
import model.Change;
import model.OrdersModel;
import service.DBChangeManipulationServ;
import service.DBOrderManipulationServ;
import view.ChangeRegPanel;

public class ChangeRegPanelController {
	private static ChangeRegPanelController instance;
	private ChangeRegPanel changeReg;
	
	private ChangeRegPanelController(ChangeRegPanel changeReg)
	{
		this.changeReg = changeReg;;
		initialize();
	}
	public static ChangeRegPanelController getInstance() 
	{
		if(instance==null) 
		{
			instance = new ChangeRegPanelController(ChangeRegPanel.getInstance());
		}
		return instance;
	}
	public void initialize() 
	{
		this.changeReg.addItemListenerMouseListenerOnComboBox(new AddItemListenerMouseListenerOnComboBox());
	}
	class AddItemListenerMouseListenerOnComboBox implements ItemListener
	{


			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) 
				{
					changeReg.clearTable();
					String combValues = changeReg.getComboBoxValue();
					Change change = DBChangeManipulationServ.getOrder(combValues, SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
					changeReg.setRcvdTxt(change.getRcvd());
					changeReg.setTotalTxt(change.getTotal());
					changeReg.setCommTxt(change.getComm());
					changeReg.setChangeTxt(change.getChange());
					String[] comboValues = changeReg.getComboBoxValue().split("-");
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
				changeReg.addOrderInTable(ord.getsNo(), ord.getFullName(), ord.getAddress(),ord.getRefNo(), 
						ord.getRecAcc(), ord.getAmount(), ord.getComm(), ord.getInvoiceAmt(),
						ord.getOrderDate());
			}
	

		
	}


}
