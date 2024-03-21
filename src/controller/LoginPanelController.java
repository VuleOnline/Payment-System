package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.SessionManager;
import model.UserModel;
import service.DBEmpManipulationServ;
import view.AdminMenuPanel;
import view.CashRegPanel;
import view.ChangeRegPanel;
import view.EmployeeListPanel;
import view.EmployeeMenuPanel;
import view.Frame;
import view.LoginPanel;
import view.PaymentPanel;


public class LoginPanelController {
	LoginPanel loginPanel;
	UserModel model;
	private static LoginPanelController instance;

	
	private LoginPanelController(LoginPanel loginPanel, UserModel model) 
	{
		this.loginPanel = loginPanel;
		this.model = model;
		initialize();
	}
	
	public static LoginPanelController getInstance() 
	{
	if(instance==null) 
	{
		instance=new  LoginPanelController(LoginPanel.getInstance(), new UserModel());
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
				System.out.println(SessionManager.getCurrSess());
				JPanel cardPanel = Frame.getCardPanel();
		
		
				if(loggedUser.isAdmin()) 
				{
				
					AdminMenuPanel adminMenuPanel = AdminMenuPanel.getInstance();
					cardPanel.add(adminMenuPanel, "adminMenuPanel");
					
					EmployeeListPanel empListPanel = EmployeeListPanel.getInstance();
					cardPanel.add(empListPanel, "empListPanel");
					
					//OrderListPanel orderListPanel = new OrderListPanel();
					//cardPanel.add(orderListPanel, "orderListPanel");
					
					Frame.getInstance().showCard("adminMenuPanel");
		
				}else 
				{
					EmployeeMenuPanel empMenuPanel = EmployeeMenuPanel.getInstance();
					cardPanel.add(empMenuPanel, "empMenuPanel");
					
					CashRegPanel cashRegPanel = CashRegPanel.getInstance();
					cardPanel.add(cashRegPanel,"cashRegPanel");
					
					PaymentPanel exchangePanel = PaymentPanel.getInstance();
					cardPanel.add(exchangePanel, "exchangePanel");
					
					ChangeRegPanel changeRegPanel = ChangeRegPanel.getInstance();
					cardPanel.add(changeRegPanel,"changeRegPanel");
					
					//EmpOrderListPanel empOrderListPanel = new EmpOrderListPanel();
					//cardPanel.add(empOrderListPanel,"empOrderListPanel");
					
					Frame.getInstance().showCard("empMenuPanel");
					
				}
				
			}else JOptionPane.showMessageDialog(null, "User doesn't exist");
			
	
		}		
		
		}
	


}
