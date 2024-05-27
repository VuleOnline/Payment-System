package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

import common.DenomManipulationMethods;
import common.SessionManager;
import dao.DBOrderManipulationDao;
import view.CashRegPanel;
import view.ExchangePanel;
import view.Frame;
import view.ReceivedMoneyPanel;

public class ReceivedMoneyPanelController {
	private ReceivedMoneyPanel rcvMon;
	
	private ReceivedMoneyPanelController(ReceivedMoneyPanel rcvMon) 
	{
		this.rcvMon = rcvMon;
		initialize();
	}

	private static Map<String, ReceivedMoneyPanelController> cache = new ConcurrentHashMap<>();
	public static ReceivedMoneyPanelController getReceivedMoneyPanelController(String sessionId) {
		 if (!cache.containsKey(sessionId)) {
			 ReceivedMoneyPanelController recMoneyContrPanel = new ReceivedMoneyPanelController(ReceivedMoneyPanel.getRecivedMoneyPanel(sessionId));
	        cache.put(sessionId, recMoneyContrPanel);
		 }
	        return cache.get(sessionId);
   }
	public void initialize() 
	{
		this.rcvMon.addFocusListeneOnTxtFields(new AddFocusListeneOnTxtFields());
		this.rcvMon.setTotal((DBOrderManipulationDao.getTotal(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now())));
		this.rcvMon.addActionLIstenerOnNextBtn(new AddActionLIstenerOnNextBtn());
	}
	class AddFocusListeneOnTxtFields implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			double result = DenomManipulationMethods.addCalculator(rcvMon.getDenomPanelInstance());
			rcvMon.setRcvd(result);
		}
		
	}
	
	class AddActionLIstenerOnNextBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			String sessionId = SessionManager.getCurrSess(); 
			int empId = SessionManager.getEmpIdBySession(sessionId);
			if(rcvMon.getRcvd()> rcvMon.getTotal()) 
			{
			DenomManipulationMethods.addToDenom(rcvMon.getDenomPanelInstance(), empId);
			ExchangePanelController epc = ExchangePanelController.getExchangePanelController(sessionId);
			ExchangePanel ep = ExchangePanel.getExchangePanel(sessionId);
	    	ep.removeAll();
	    	ep.initialize();
	    	epc.initialize();
	    	ep.revalidate();
	    	ep.repaint();
	        Frame.getInstance().showCard("exchangePanel");
			}
			else if(rcvMon.getRcvd() == rcvMon.getTotal()) 
			{
				ExchangePanelController exchPanel = ExchangePanelController.getExchangePanelController(sessionId);
				exchPanel.AggOrdersInsertation(DBOrderManipulationDao.getUnpaidOrForwOrCanc(0, empId, LocalDate.now()));
				DenomManipulationMethods.addToDenom(rcvMon.getDenomPanelInstance(), empId);
				int paidList = DBOrderManipulationDao.setPaidOrForwarded(1, 0, empId, LocalDate.now());
		    	if(paidList>0) 
		    	{
		    	CashRegPanel cr = CashRegPanel.getCashRegPanel(sessionId);
		    	CashRegPanelController crc = CashRegPanelController.getCashRegPanelController(sessionId);
		    	cr.removeAll();
		    	cr.initialize();
		    	crc.initialize();
		    	cr.revalidate();
		    	cr.repaint();
		        Frame.getInstance().showCard("cashRegPanel");
		        }
		    }else JOptionPane.showMessageDialog(null, "There is a money deficiency. Check input fields.");
		    
			
		}
	}
		
		
	
	
	
		
		
		
	
	

	}
