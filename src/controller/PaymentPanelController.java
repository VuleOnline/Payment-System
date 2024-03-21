package controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import common.SessionManager;
import model.Change;
import model.OrdersModel;
import service.DBChangeManipulationServ;
import service.DBOrderManipulationServ;
import view.CashRegPanel;
import view.Frame;
import view.PaymentPanel;

public class PaymentPanelController {
	private CashRegPanelController cashRegCont;
	private CashRegPanel cashReg;
	private PaymentPanel payPanel;
	private static  PaymentPanelController instance;
	
	private PaymentPanelController(CashRegPanelController cashRegCont, CashRegPanel cashReg, PaymentPanel payPanel) 
	{
		this.cashRegCont = cashRegCont;
		this.cashReg = cashReg;
		this.payPanel = payPanel;
		initialize();
		
	}
	public void initialize() 
	{
		this.cashRegCont.clearFields(PaymentPanel.getInstance());
		this.payPanel.setTotal(DBOrderManipulationServ.getTotal(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now()));
		this.payPanel.setRcvd(payPanel.getRcvd());
		countChange();
		this.payPanel.addFocusListeneOnRcvdTxt(new AddFocusListeneOnRcvdTxt());
		this.payPanel.addFocusListeneOnTxtFields(new AddFocusListeneOnTxtFields());
		this.payPanel.addActionListenerOnFin(new AddActionListenerOnFin());
	}
	public static PaymentPanelController getInstance() {
		 if (instance == null) {
	            instance = new PaymentPanelController(CashRegPanelController.getInstance(), CashRegPanel.getInstance(), PaymentPanel.getInstance());
	        }
	        return instance;
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
			
			double result = 0.0;
			HashMap<String, String> list = fieldMap(payPanel.getChangeInputPanelInstance());
			HashMap<String, String> labelList =labelValue();
			for(Map.Entry<String, String> entry : list.entrySet()) 
			{
				String filedName = entry.getKey();
				JTextField field = getJTextField(payPanel.getChangeInputPanelInstance(), filedName);
				String value = list.get(field.getName());
				System.out.println("labela za "+field.getName()+"je: "+value);
				String label = labelList.get(value);
				double fieldTxt = field.getText().isEmpty() ? 0.0 : Double.parseDouble(field.getText());
				try {
					double labelTxt = Double.parseDouble(label);
					double res = labelTxt * fieldTxt;
					result=result+res;
					}catch(Exception ex) 
					{
						System.out.println("Exception occurred. Field: " + filedName + ", Label: " + label + ", Field Text: " + fieldTxt);
					}
			}
			double oldChange = payPanel.getRcvd()-payPanel.getTotal();
			double newChange = oldChange - result;
			payPanel.setChange(newChange);
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
		   if(payPanel.getChange()<0.0)
		   {
			   JOptionPane.showMessageDialog(null, "There is a money deficiency. Check input fields.");
		   }
		   
		   else if (payPanel.getChange() == 0.0) {
		    	dbChangeInsertion(DBOrderManipulationServ.getUnpaidOrders(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now()));
		    	int paidList = DBOrderManipulationServ.setPaid(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
		    	if(paidList>0) {
		        
		    	CashRegPanelController crc = CashRegPanelController.getInstance();
		    	CashRegPanel cr = CashRegPanel.getInstance();
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
			
			
		private void dbChangeInsertion(ArrayList<OrdersModel> paid) 
		{
			double rcvd = payPanel.getRcvd();
			double change = Math.ceil(payPanel.getRcvd() - payPanel.getTotal());
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
		Change exchange = new Change(SNoBuilder, comm, total, rcvd, change, empId);
		if(DBChangeManipulationServ.insertBilling(exchange)) 
		{
			System.out.println("Unet kusur!");
		}else System.out.println("Kusur nije unet");
		
			
		
			
	}
	public void countChange() 
	{
			payPanel.setRcvd(payPanel.getRcvd());
			double change = payPanel.getRcvd()- payPanel.getTotal();
			payPanel.setChange(change);
	}
		
	public static ArrayList<JTextField> fieldsToList(Container field) 
	{
		ArrayList<JTextField> list = new ArrayList<>();
		Component[] component = field.getComponents();
		for(Component comp : component) 
		{
			if(comp instanceof JTextField) 
			{
				list.add((JTextField) comp);
			}else if(comp instanceof Container) 
			{
				fieldsToList((Container) comp);
			}
		}
		
		return list;
	}
	public HashMap<String, String> fieldMap(Container container)
	{
		HashMap<String, String> mapList = new HashMap<>();
		
		for(Component comp :container.getComponents())
		{
			if( comp instanceof JTextField) 
			{
				JTextField text = (JTextField)comp;
				JLabel label = getJLabel(container, text);
				if (text != null && label != null) {
					mapList.put(text.getName(), label.getName());
				}else {
		                System.out.println("Nije pronađena odgovarajuća Labela za JTextField: " + text.getName());
				}
			}
		}
		return mapList;
	}
	public JLabel getJLabel(Container container, JTextField text) 
	{
		 for (Component component : container.getComponents()) {
			 
	            if (component instanceof JLabel && component.getName() != null && component.getName().substring(0, 2).equals(text.getName().substring(0, 2))) 
	            {
	            	return (JLabel) component;
	            }
	        }
	        return null;
	}
	public JTextField getJTextField(Container container, String text) 
	{
		 for (Component component : container.getComponents()) {
			 
	            if (component instanceof JTextField && component.getName() != null && component.getName().equals(text)) 
	            {
	            	return (JTextField) component;
	            }
	        }
	        return null;
	}
	public static HashMap<String, String> labelValue()
	{
		HashMap<String, String> labels = new HashMap<>();
		labels.put("F1fiveThLbl", "5000");
		labels.put("F2twoThLbl", "2000");
		labels.put("F3oneThLbl", "1000");
		labels.put("F4fiveHLbl", "500");
		labels.put("F5twoHLbl", "200");
		labels.put("F6oneHLbl", "100");
		labels.put("F7fifthLbl", "50");
		labels.put("F8twenLbl", "20");
		labels.put("F9tenLbl", "10");
		labels.put("FTfiveLbl", "5");
		labels.put("FEtwoLbl", "2");
		labels.put("FWoneLbl", "1");

		return labels;
	} 

}
