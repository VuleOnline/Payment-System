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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import common.SessionManager;
import dao.DBOrderManipulationDao;
import model.OrdersModel;
import view.CashRegPanel;
import view.Frame;
import view.ReceivedMoneyPanel;
import view.ReverseDenomPanel;

public class CashRegPanelController {
	private CashRegPanel cashReg;

	  
	 public CashRegPanelController(CashRegPanel cashReg)
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

	 private static Map<String, CashRegPanelController> cache = new ConcurrentHashMap <>();

	    public static CashRegPanelController getCashRegPanelController(String sessionId) {
	        if (!cache.containsKey(sessionId)) {
	        	CashRegPanelController cashRegPanelContr = new CashRegPanelController(CashRegPanel.getCashRegPanel(sessionId));
	            cache.put(sessionId, cashRegPanelContr);
	        }
	        return cache.get(sessionId);
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
		String sessionId = SessionManager.getCurrSess();
		int empId = SessionManager.getEmpIdBySession(sessionId);
		@Override
		public void actionPerformed(ActionEvent e) {
			int Sno = DBOrderManipulationDao.countOrders(empId, LocalDate.now());
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
			OrdersModel order = new OrdersModel(Sno, fullname, address, city, refNo, recAcc, amt, comm, invoiceAmt, 0, empId);
			if(DBOrderManipulationDao.insertOrder(order)) 
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
		String sessionId = SessionManager.getCurrSess();
		int empId = SessionManager.getEmpIdBySession(sessionId);

		@Override
		public void actionPerformed(ActionEvent e) {
			if(DBOrderManipulationDao.selectOrderForReverse(empId, LocalDate.now())== null)
			{
			ReceivedMoneyPanel pp = ReceivedMoneyPanel.getRecivedMoneyPanel(sessionId);
			ReceivedMoneyPanelController ppc = ReceivedMoneyPanelController.getReceivedMoneyPanelController(sessionId);
			pp.removeAll();
			pp.initialize();
			ppc.initialize();
			pp.revalidate();
			pp.repaint();
			Frame.getInstance().showCard("rcvdMoneyPanel");
			}
			else
			{
				Frame.getInstance().showCard("revDenomPanel");
			}
		}
		
	}
	class AddActionListenerOnSendBtn implements ActionListener
	{
		String sessionId = SessionManager.getCurrSess();
		int empId = SessionManager.getEmpIdBySession(sessionId);
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<OrdersModel> paid = DBOrderManipulationDao.getPaid(empId, LocalDate.now());
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
			DBOrderManipulationDao.setPaidOrForwarded(2, 1, empId, LocalDate.now());
			CashRegPanel cashReg = CashRegPanel.getCashRegPanel(sessionId);
		    	cashReg.removeAll();
				cashReg.initialize();
				initialize();
				cashReg.revalidate();
				cashReg.repaint();
	        }
	        );
		    return null;
	            }
	        };

	        worker.execute();
		
		}
	
		
	}
	

	class AddMouseListenerOnTable implements MouseListener {
	    String sessionId = SessionManager.getCurrSess();
	    int empId = SessionManager.getEmpIdBySession(sessionId);

	    @Override
	    public void mouseClicked(MouseEvent e) {
	        int selectedRow = cashReg.getInstanceOfTable().getSelectedRow();
	        Object SNo = cashReg.getInstanceOfTable().getValueAt(selectedRow, 0);
	        OrdersModel toBeReversedList = DBOrderManipulationDao.selectOrderForReverse(empId, LocalDate.now());
	        OrdersModel order = DBOrderManipulationDao.getOrderBySno((int) SNo, empId, LocalDate.now());

	        if (toBeReversedList != null) {
	            JOptionPane.showMessageDialog(null, "Finish your actions first.");
	            return; 
	        }

	        switch (order.getState()) {
	            case 0:
	                int res = JOptionPane.showConfirmDialog(cashReg, "Do you want to cancel this order?", "Cancel Order", JOptionPane.YES_NO_OPTION);
	                if (res == JOptionPane.YES_OPTION) {
	                    if (DBOrderManipulationDao.setCancelled((int) SNo, empId, LocalDate.now()) > 0) {
	                        cashReg.removeAll();
	                        cashReg.initialize();
	                        initialize();
	                        cashReg.revalidate();
	                        cashReg.repaint();
	                    } else {
	                    	 JOptionPane.showMessageDialog(null, "Cancel failed.");
	                    }
	                }
	                break;
	            case 3:
	                JOptionPane.showMessageDialog(null, "Order already cancelled.");
	                break;
	            case 1:
	                if (order.isReversed()) {
	                    JOptionPane.showMessageDialog(null, "Order can't be cancelled.");
	                }
	                break;
	            case 2:
	                if (!order.isReversed()) {
	                    int ans = JOptionPane.showConfirmDialog(cashReg, "Do you want to REVERSE this order?", "Reverse Order", JOptionPane.YES_NO_OPTION);
	                    if (ans == JOptionPane.YES_OPTION) {
	                        DBOrderManipulationDao.setToBeReverse((int) SNo, empId, LocalDate.now());
	                        ReverseDenomPanel rdp = ReverseDenomPanel.getReverseDenomPanel(sessionId);
	                        ReverseDenomPanelController rdpc = ReverseDenomPanelController.getReverseDenomPanelController(sessionId);
	                        rdp.removeAll();
	                        rdp.initialize();
	                        rdpc.initialize();
	                        rdp.revalidate();
	                        rdp.repaint();
	                        Frame.getInstance().showCard("revDenomPanel");
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Order already reversed.");
	                }
	                break;
	            default:
	                
	                break;
	        }
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
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
		String sessionId = SessionManager.getCurrSess();
		int empId = SessionManager.getEmpIdBySession(sessionId);
		List<OrdersModel> order = DBOrderManipulationDao.selectOrders(empId, LocalDate.now());
		
		for(OrdersModel ord : order) 
		{
			cashReg.showOrderInTable(ord.getsNo(), ord.getFullName(), ord.getAddress(),ord.getRefNo(), 
					ord.getRecAcc(), ord.getAmount(), ord.getComm(), ord.getInvoiceAmt(),
					ord.getOrderDate());
		}
		
	}
	
}
