package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import common.DenomManipulationMethods;
import common.SessionManager;
import dao.DBMoneyManipulationDao;
import dao.DBOrderManipulationDao;
import model.AggTransactions;
import model.OrdersModel;
import view.CashRegPanel;
import view.Frame;
import view.ReceivedMoneyPanel;
import view.ExchangePanel;

public class ExchangePanelController {
	private ReceivedMoneyPanel rcvdMon;
	private ExchangePanel exchPanel;
	
	private ExchangePanelController(ReceivedMoneyPanel rcvdMon, ExchangePanel exchPanel) 
	{
		this.rcvdMon = rcvdMon;
		this.exchPanel = exchPanel;
		initialize();
		
	}
	public void initialize() 
	{
		this.exchPanel.setTotal(DBOrderManipulationDao.getTotal(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now()));
		this.exchPanel.setRcvd(rcvdMon.getRcvd());
		countChange();
		this.exchPanel.addFocusListeneOnRcvdTxt(new AddFocusListeneOnRcvdTxt());
		this.exchPanel.addFocusListeneOnTxtFields(new AddFocusListeneOnTxtFields());
		this.exchPanel.addActionListenerOnFin(new AddActionListenerOnFin());
	}
	private static Map<String, ExchangePanelController> cache = new ConcurrentHashMap<>();
	
	public static ExchangePanelController getExchangePanelController(String sessionId) {
		  if (!cache.containsKey(sessionId)) {
			  ExchangePanelController exchPanelContr = new ExchangePanelController(ReceivedMoneyPanel.getRecivedMoneyPanel(sessionId), ExchangePanel.getExchangePanel(sessionId));
	            cache.put(sessionId, exchPanelContr);
	        }
	        return cache.get(sessionId);
    }
	
	class AddFocusListeneOnRcvdTxt implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			countChange();
			
			
		}
	
		
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
			double result = DenomManipulationMethods.subCalculator(exchPanel.getDenomPanelInstance(), empId);
			double oldChange = exchPanel.getRcvd()-exchPanel.getTotal();
			exchPanel.setChange(oldChange - result);
		}
		
	}
	class AddActionListenerOnFin implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			 if (SwingUtilities.isEventDispatchThread()) {
			        //EDT
			        performAction();
			    } else {
			        SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			                // EDT
			                performAction();
			            }
			        });
			    }
		}
		private void performAction() 
		
		{
			String sessionId = SessionManager.getCurrSess();
			int empId = SessionManager.getEmpIdBySession(sessionId);
		   if(exchPanel.getChange()<0.0)
		   {
			   JOptionPane.showMessageDialog(null, "There is a money deficiency. Check input fields.");
		   }
		   
		   else if (exchPanel.getChange() == 0.0) {
			    DenomManipulationMethods.subFromDenom(exchPanel.getDenomPanelInstance(), empId);
		    	AggOrdersInsertation(DBOrderManipulationDao.getUnpaidOrders(empId, LocalDate.now()));
		    	int paidList = DBOrderManipulationDao.setPaid(empId, LocalDate.now());
		    	if(paidList>0) {
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
		   else 
		   {
			   JOptionPane.showMessageDialog(null, "There is a money suficiency. Check input fields.");
		   }
		  
		        
		}
			
	}
		
			
		public void AggOrdersInsertation(ArrayList<OrdersModel> paid) 
		{
			double rcvd = exchPanel.getRcvd();
			double change = Math.ceil(exchPanel.getRcvd() - exchPanel.getTotal());
			StringBuilder SNoBuilder = new StringBuilder();
			double comm = 0.0;
			double total = 0.0;
			int empId = 0;
			boolean firstIteration = true;
			for(OrdersModel pay : paid) 
			{
				String SNo= String.valueOf(pay.getsNo());
				double com = pay.getComm();
				double ttl = pay.getInvoiceAmt();
				empId = pay.getEmpid();
				 if (!firstIteration) {
				        SNoBuilder.append("-");
				    } else {
				        firstIteration = false;
				    }
				SNoBuilder.append(SNo);
				comm+=com;
				comm = Math.ceil(comm);
				total+=ttl;
				Math.ceil(total);
			}
		AggTransactions exchange = new AggTransactions(SNoBuilder.toString(), comm, total, rcvd, change, empId);
		if(DBMoneyManipulationDao.insertBilling(exchange)) 
		{
			System.out.println("Unet kusur!");
		}else System.out.println("Kusur nije unet");
		
			
		
			
	}
	public void countChange() 
	{
		exchPanel.setRcvd(exchPanel.getRcvd());
			double change = exchPanel.getRcvd()- exchPanel.getTotal();
			exchPanel.setChange(change);
	}
		 

}
