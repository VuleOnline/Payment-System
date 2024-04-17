package controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import dao.DBEmpManipulationDao;
import dao.DBOrderManipulationDao;
import model.OrdersModel;
import model.UserModel;
import view.OrderListPanel;

public class OrderListPanelController {

	private OrderListPanel orderList;
	
	private OrderListPanelController(OrderListPanel orderList) 
	{
		this.orderList = orderList;
		initialize();
	}
	private static Map<String, OrderListPanelController> cache = new HashMap<>();
	 public static OrderListPanelController getOrderListPanelContr(String sessionId) 
	    {
		 if(!cache.containsKey(sessionId)) 
			{
			 OrderListPanelController orderListPanelContr  = new OrderListPanelController(OrderListPanel.getOrderListPanel(sessionId));
				cache.put(sessionId, orderListPanelContr);
			}
			return cache.get(sessionId);
	    }

	public void initialize() {
		
		this.orderList.addActionListenerOnReset(new AddActionListenerOnReset());
		this.orderList.addFocusListenerOnTxtFields(new AddFocusListenerOnTxtFields());
		this.orderList.addItemListenerOnBtn(new AddItemListenerOnBtn());
		this.orderList.addMouseListenerOnTable(new AddMouseListenerOnTable());
	}
	class AddActionListenerOnReset implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			clearInputFields(orderList.getInputFieldPanelInstance());
			
			
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
			focusLostEvent();
	}
	}
	
	class AddItemListenerOnBtn implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.SELECTED) 
			{
				focusLostEvent();
			}
			
		}
		
	}
	class AddMouseListenerOnTable implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			int rowSelected = orderList.getInstanceOfTable().getSelectedRow();
			Object Id = orderList.getInstanceOfTable().getValueAt(rowSelected, 12);
			UserModel user = DBEmpManipulationDao.selectUserByID((int)Id);
			orderList.setEmpIdInf(user.getId());
			orderList.setEmpFnameInf(user.getFname());
			orderList.setEmpLnameInf(user.getLname());
			orderList.setEmpUnameInf(user.getUname());
			orderList.setEmpPassInf(user.getPassword());
			
			
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
	
	public void clearInputFields(Container container) 
	{
		Component[] compo = container.getComponents();
		for(Component comp : compo ) 
		{
			if(comp instanceof JTextField) 
			{
				((JTextField) comp).setText("");
			}
			else if(comp instanceof Container) 
			{
				clearInputFields((Container)comp);
			}
		}
		 ButtonGroup buttonGroup = orderList.getButtonGroup();
		    if(buttonGroup != null) {
		        buttonGroup.clearSelection();
		    }
	}
	
	
	public void focusLostEvent() 
	{
		orderList.clearTable();
		LocalDate date = orderList.getDateTxt();
		int sNo = orderList.getSnoTxt();
		System.out.println("Sno vr je : "+sNo);
		int id = orderList.getIdTxt();
		System.out.println("ID vr je: "+id);
		boolean all = orderList.getAllCheck();
		boolean rev = orderList.getRevCheck();
		boolean forw = orderList.getForwardCheck();
		boolean canc = orderList.getCancelCheck();
		ArrayList<OrdersModel> orders= DBOrderManipulationDao.getAdminQueryOrders(date, sNo, id, all, rev, forw, canc);
		System.out.println("nalozi: " +orders.size());
		for(OrdersModel ord: orders) 
		{
			orderList.showOrderInTable(ord.getsNo(), ord.getFullName(), ord.getAddress(), ord.getCity(),
					ord.getRefNo(), ord.getRecAcc(), ord.getAmount(), ord.getComm(), ord.getInvoiceAmt(), ord.getOrderDate(),
					ord.getState(), ord.isReversed(), ord.getEmpid());
		}
			
		
	}
	
	
}
