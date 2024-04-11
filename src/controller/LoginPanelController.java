package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.SessionManager;
import model.UserModel;
import service.DBEmpManipulationServ;
import view.AdminAggOrdersPanel;
import view.AdminMenuPanel;
import view.CashRegPanel;
import view.CashRequisitionPanel;
import view.AggOrdersPanel;
import view.CashDepositionPanel;
import view.CashRegBalancePanel;
import view.EmployeeListPanel;
import view.EmployeeMenuPanel;
import view.Frame;
import view.LoginPanel;
import view.OrderListPanel;
import view.ReceivedMoneyPanel;
import view.ReverseDenomPanel;
import view.ExchangePanel;


public class LoginPanelController {
	private static LoginPanelController instance;
	LoginPanel loginPanel;
	UserModel model;

	
	private LoginPanelController(LoginPanel loginPanel, UserModel model) 
	{
		this.loginPanel = loginPanel;
		this.model = model;
		initialize();
	}
	
	 public static LoginPanelController getInstance() 
	    {
		 if(instance == null) 
			{
			 instance = new LoginPanelController(LoginPanel.getInstance(), new UserModel());
			}
			return instance;
	    }


	public void initialize() 
	{
		
		this.loginPanel.addActionListenerOnLogin(new AddActionListenerOnLogin());

	}
	
	class AddActionListenerOnLogin implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			String uname = loginPanel.getUName();
			String pass= loginPanel.getPass();
			
			UserModel userInput = new UserModel();
			userInput.setUname(uname);
			userInput.setPassword(pass);
			
			UserModel loggedUser = DBEmpManipulationServ.loginCheck(userInput);
			if(loggedUser!=null) 
			{
				String session = SessionManager.startSession(loggedUser.getId());
				SessionManager.setCurrSession(session);
				JPanel cardPanel = Frame.getCardPanel();
		
		
				if(loggedUser.isAdmin()) 
				{
				
					AdminMenuPanel adminMenuPanel = AdminMenuPanel.getAdminMenuPanel(session);
					cardPanel.add(adminMenuPanel, "adminMenuPanel");
					
					EmployeeListPanel empListPanel = EmployeeListPanel.getEmpListPanel(session);
					cardPanel.add(empListPanel, "empListPanel");
					
					OrderListPanel orderListPanel = OrderListPanel.getOrderListPanel(session);
					cardPanel.add(orderListPanel, "orderListPanel");
					
					AdminAggOrdersPanel aggOrdersPanel = AdminAggOrdersPanel.getAdminAggOrdersPanel(session);
					cardPanel.add(aggOrdersPanel,"adminAggOrderListPanel");
					
					Frame.getInstance().showCard("adminMenuPanel");
		
				}else 
				{
					EmployeeMenuPanel empMenuPanel = EmployeeMenuPanel.getEmployeeMenuPanel(session);
					cardPanel.add(empMenuPanel, "empMenuPanel");
					
					CashRegPanel cashRegPanel = CashRegPanel.getCashRegPanel(session);
					cardPanel.add(cashRegPanel,"cashRegPanel");
					
					ExchangePanel exchangePanel = ExchangePanel.getExchangePanel(session);
					cardPanel.add(exchangePanel, "exchangePanel");
					
					ReceivedMoneyPanel rcvdMoneyPanel = ReceivedMoneyPanel.getRecivedMoneyPanel(session);
					cardPanel.add(rcvdMoneyPanel, "rcvdMoneyPanel");
					
					AggOrdersPanel aggOrdersPanel = AggOrdersPanel.getAggOrdersPanel(session);
					cardPanel.add(aggOrdersPanel,"aggOrdersPanel");
					
					CashRequisitionPanel cashReqPanel = CashRequisitionPanel.getCashRequisitionPanel(session);
					cardPanel.add(cashReqPanel,"cashReqPanel");
					
					CashDepositionPanel cashDepoPanel = CashDepositionPanel.getCashDepositionPanel(session);
					cardPanel.add(cashDepoPanel,"cashDepoPanel");
					
					ReverseDenomPanel revDenomPanel = ReverseDenomPanel.getReverseDenomPanel(session);
					cardPanel.add(revDenomPanel, "revDenomPanel");
					
					CashRegBalancePanel cashRegBalancePanel = CashRegBalancePanel.getCashRegBalancePanel(session);
					cardPanel.add(cashRegBalancePanel, "cashRegBalancePanel");
					
					//EmpOrderListPanel empOrderListPanel = new EmpOrderListPanel();
					//cardPanel.add(empOrderListPanel,"empOrderListPanel");
					
					Frame.getInstance().showCard("empMenuPanel");
					
				}
				
			}else JOptionPane.showMessageDialog(null, "User doesn't exist");
			
	
		}		
		
		}
	


}
