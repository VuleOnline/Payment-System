package controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import common.SessionManager;
import model.OrdersModel;

import service.DBOrderManipulationServ;
import view.CashRegPanel;
import view.Frame;
import view.PaymentPanel;

public class CashRegPanelController {
	private CashRegPanel cashReg;
	private static CashRegPanelController instance;

	  
	 private CashRegPanelController(CashRegPanel cashReg)
		{
			this.cashReg = cashReg;
			initialize();
			
		}
	 public void initialize() {
		 	this.cashReg.addFocusListenerOnAmt(new AddFocusListenerOnAmt());
			this.cashReg.addActionListenerOnCheck(new AddActionListenerOnCheck());
			this.cashReg.addActionListenerOnFinish(new AddActionListenerOnFinish());
			this.cashReg.addFocusListenerOnTxtFields(new AddFocusListenerOnTxtFields());
			this.cashReg.addActionListenerOnBillBtn(new AddActionListenerOnBillBtn());
			this.cashReg.addActionListenerOnSendBtn(new AddActionListenerOnSendBtn());
			this.cashReg.addMouseListenerOnTable(new AddMouseListenerOnTable());
			this.cashReg.clearTable();
			clearFields(cashReg.getInfoPanel());
			showOrdersInTable();
	    }

	  public static CashRegPanelController getInstance() {
		  if (instance == null) {
			  System.out.println("Kreiranje novog kontrolera u if");
	            instance = new CashRegPanelController(CashRegPanel.getInstance());
	        }
	        return instance;
	    }
	  
	class AddFocusListenerOnAmt implements FocusListener{


		@Override
		public void focusLost(FocusEvent e) {
			double amt = cashReg.getAmtTxt();
			double comm = cashReg.getCommTxt();
			cashReg.setAmtTxt(Math.ceil(amt));
			if(cashReg.getCommChck()) {
			comm = countComm(amt);
			cashReg.setCommTxt(comm);
			}
			cashReg.setInvoiceAmtTxt(countInvoice(comm, amt));
		}

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	class AddFocusListenerOnTxtFields implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			JTextField txtField = (JTextField) e.getSource();
			String newTxt = txtField.getText().toUpperCase();
			txtField.setText(newTxt);
			
		}
		
	}
	class AddActionListenerOnCheck implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			double amt = cashReg.getAmtTxt();
			double comm = 0.0;
			double invoice;
			if(cashReg.getCommChck()) 
			{
				comm = countComm(amt); 
				
			}
			invoice = countInvoice(comm, amt);
			cashReg.setCommTxt(comm);
			cashReg.setInvoiceAmtTxt(invoice);
		}
		
	}
	
	class AddActionListenerOnFinish implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int id = SessionManager.getEmpIdBySession(SessionManager.getCurrSess());
			int Sno = DBOrderManipulationServ.countOrders(id, LocalDate.now());
			Sno = Sno+1;
			if(emptyFieldCheck(cashReg.getInfoPanel())) {
			String fullname = cashReg.getFullName();
			String address = cashReg.getAddress();
			String city = cashReg.getCity();
			String refNo = cashReg.getRefNo();
			String recAcc = cashReg.getRecAcc();
			double amt = cashReg.getAmtTxt();
			double comm = cashReg.getCommTxt();
			double invoiceAmt = cashReg.getInvoiceAmtTxt();
			OrdersModel order = new OrdersModel(fullname, address, city, refNo, recAcc, amt, comm, invoiceAmt);
			if(DBOrderManipulationServ.insertOrder(Sno, id, order)) 
			{
				cashReg.removeAll();
				cashReg.initialize();
				initialize();
				cashReg.revalidate();
				cashReg.repaint();
			}
			
			}else JOptionPane.showMessageDialog(null, "Fields can not be empty!");
			
		}
		
	}
	
	class AddActionListenerOnBillBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			PaymentPanel pp = PaymentPanel.getInstance();
			PaymentPanelController ppc = PaymentPanelController.getInstance();
			pp.removeAll();
			pp.initialize();
			ppc.initialize();
			pp.revalidate();
			pp.repaint();
			Frame.getInstance().showCard("exchangePanel");
		}
		
	}
	class AddActionListenerOnSendBtn implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<OrdersModel> paid = DBOrderManipulationServ.getPaid(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
	            @Override
	            protected Void doInBackground() throws Exception {
	                for (OrdersModel paidOrder : paid) {
	                    int sNo = paidOrder.getsNo();
	                    SwingUtilities.invokeLater(() -> cashReg.selectRows(sNo));

	                   Thread.sleep(200);
	                }
	        Thread.sleep(500);
	        SwingUtilities.invokeLater(() -> {
			cashReg.deselectRow();
			DBOrderManipulationServ.forwardOrders(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
			ArrayList<OrdersModel> forwarded = DBOrderManipulationServ.getForwarded(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
			System.out.println("Broj forwardovanih: " + forwarded );
			CashRegPanel cashReg = CashRegPanel.getInstance();
		    if (!(forwarded.isEmpty())) {
		    	cashReg.removeAll();
				cashReg.initialize();
				initialize();
				cashReg.revalidate();
				cashReg.repaint();
		    	
		    }else {
		    	JOptionPane.showMessageDialog(cashReg, "Prenos neuspeo.");
		    }
	        });
		    return null;
	            }
	        };

	        worker.execute();
		
		}
	
		
	}
	
	class  AddMouseListenerOnTable implements MouseListener
	{


		@Override
		public void mouseClicked(MouseEvent e) {
			
				int selectedRow = cashReg.getInstanceOfTable().getSelectedRow();
				Object SNo = cashReg.getInstanceOfTable().getValueAt(selectedRow, 0);
				ArrayList<OrdersModel> order = DBOrderManipulationServ.selectOrderForCancel((int)SNo, SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
				if(!order.isEmpty()) 
				{
					int res = JOptionPane.showConfirmDialog(cashReg, JOptionPane.WARNING_MESSAGE , "Do you want to cancel this order", JOptionPane.YES_NO_OPTION);
					if(res == JOptionPane.YES_OPTION) 
					{
						if(DBOrderManipulationServ.setCancelled((int)SNo, SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now())>0)
						{
							cashReg.removeAll();
							cashReg.initialize();
							initialize();
							cashReg.revalidate();
							cashReg.repaint();
						
						}else {System.out.println("Neuspeo cancel naloga broj:"+ (int)SNo);}
						
					}else {}
				}else JOptionPane.showMessageDialog(cashReg, "Can't be cancelled.");
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		
		
	}
	
	public double countComm(double amt) 
	{
		return amt*0.03;
	}
	public double countInvoice(double comm, double amt) 
	{
		return comm+amt;
	}
	
	public double countTotal()
	{
		return cashReg.getTotalTxt() + cashReg.getInvoiceAmtTxt();
	}
	
	public void clearFields(Container container) 
	{
		Component[] components = container.getComponents();
		for(Component comp : components) 
		{
			if(comp instanceof JTextField) 
			{
				((JTextField)comp).setText("");
			}
			else if(comp instanceof JCheckBox) 
			{
				((JCheckBox)comp).setSelected(false);
			}
			else if (comp instanceof Container)
			{
				clearFields((Container) comp);
			}
		}
	}
	public void toUpperCase(Container container) 
	{
		Component[] components = container.getComponents();
		for(Component comp : components) 
		{
			if(comp instanceof JTextField) 
			{
				((JTextField)comp).getText().toUpperCase();
			}
			else if (comp instanceof Container)
			{
				toUpperCase((Container) comp);
			}
		}
	}
	public boolean emptyFieldCheck(Container container) 
	{
		Component[] component = container.getComponents();
		for(Component comp : component) 
		{
			if(comp instanceof JTextField)
			{
				if(((JTextField) comp).isEditable()) 
				{
					 if (((JTextField) comp).getText().trim().isEmpty())
					 {
						 return false;
					 }else if(comp instanceof Container) 
					 {
						 emptyFieldCheck((Container) comp);
					 }
				}
			}
		}
		return true;
	}

	public void showOrdersInTable() {
		List<OrdersModel> order = DBOrderManipulationServ.selectOrders(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now());
		
		for(OrdersModel ord : order) 
		{
			cashReg.addOrderInTable(ord.getsNo(), ord.getFullName(), ord.getAddress(),ord.getRefNo(), 
					ord.getRecAcc(), ord.getAmount(), ord.getComm(), ord.getInvoiceAmt(),
					ord.getOrderDate());
		}
		
	}
	
}
