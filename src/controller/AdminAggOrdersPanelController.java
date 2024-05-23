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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JTextField;

import dao.DBEmpManipulationDao;
import dao.DBOrderManipulationDao;
import model.AggTransactions;
import model.UserModel;
import view.AdminAggOrdersPanel;

public class AdminAggOrdersPanelController {
	
private AdminAggOrdersPanel aggOrderList;
	
	private AdminAggOrdersPanelController(AdminAggOrdersPanel aggOrderList) 
	{
		this.aggOrderList = aggOrderList;
		initialize();
	}
	private static Map<String, AdminAggOrdersPanelController> cache = new ConcurrentHashMap <>();
	 public static AdminAggOrdersPanelController getAdminAggOrdersPanelContr(String sessionId) 
	    {
		 if(!cache.containsKey(sessionId)) 
			{
			 AdminAggOrdersPanelController aggOrderListPanelContr  = new AdminAggOrdersPanelController(AdminAggOrdersPanel.getAdminAggOrdersPanel(sessionId));
				cache.put(sessionId, aggOrderListPanelContr);
			}
			return cache.get(sessionId);
	    }

	public void initialize() {
		this.aggOrderList.addActionListenerOnReset(new AddActionListenerOnReset());
		this.aggOrderList.addFocusListenerOnTxtFields(new AddFocusListenerOnTxtFields());
		this.aggOrderList.addMouseListenerOnTable(new AddMouseListenerOnTable());
	}
	
	class AddActionListenerOnReset implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			clearInputFields(aggOrderList.getInputFieldPanelInstance());
			
			
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
		
		public void focusLostEvent() 
		{
			aggOrderList.clearTable();
			LocalDate date = aggOrderList.getDateTxt();
			int id = aggOrderList.getIdTxt();
			ArrayList<AggTransactions> ch= DBOrderManipulationDao.getAggAdminQuery(date, id);
			System.out.println("agg ord: " +ch.size());
			for(AggTransactions change: ch) 
			{
				aggOrderList.showOrderInTable(change.getSNo(), change.getComm(), change.getTotal(), change.getRcvd(), change.getChange(), change.getReversed(), change.getEmpid(), change.getDate());
			}
				
			
		}
		class AddMouseListenerOnTable implements MouseListener
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				int rowSelected = aggOrderList.getInstanceOfTable().getSelectedRow();
				Object Id = aggOrderList.getInstanceOfTable().getValueAt(rowSelected, 6);
				UserModel user = DBEmpManipulationDao.selectUserByID((int)Id);
				aggOrderList.setEmpIdInf(user.getId());
				aggOrderList.setEmpFnameInf(user.getFname());
				aggOrderList.setEmpLnameInf(user.getLname());
				aggOrderList.setEmpUnameInf(user.getUname());
				aggOrderList.setEmpPassInf(user.getPassword());
				
				
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

}
