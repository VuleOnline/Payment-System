package view;

import javax.swing.JPanel;

import common.Logout;
import controller.EmployeeMenuPanelController;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class EmployeeMenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel empMenuBtns;
	private JButton cashRegBtn;
	private JButton btncanceledAccounts;
	private JButton accRegBtn;
	private JButton changeRegBtn;
	private JButton freeBtn1;
	private JButton freeBtn2;
	private static EmployeeMenuPanel instance;
	
	private EmployeeMenuPanel() {
		initialize();
	}
	
	 public static EmployeeMenuPanel getInstance() {
	    	if (instance == null) {
	            instance = new EmployeeMenuPanel();
	    	}
	        return instance;
	    }
	
	
	public void initialize() 
	{
		this.setPreferredSize(new Dimension(768, 524));
		setLayout(null);
		
		Logout logout = Logout.getInstance();
		JButton btn = logout.logout();
		add(btn);
		
		empMenuBtns = new JPanel();
		empMenuBtns.setBounds(86, 91, 599, 268);
		add(empMenuBtns);
		empMenuBtns.setLayout(null);
		
		cashRegBtn = new JButton("<html><center>Cash</center></br><center> register</center></html>");
		cashRegBtn.setBounds(64, 59, 85, 47);
		empMenuBtns.add(cashRegBtn);
		
		btncanceledAccounts = new JButton("<html><center>Canceled</center></br><center> accounts</center></html>");
		btncanceledAccounts.setBounds(64, 160, 85, 47);
		empMenuBtns.add(btncanceledAccounts);
		
		accRegBtn = new JButton("<html><center>Accounts</center></br><center> register</center></html>");
		accRegBtn.setBounds(253, 59, 85, 47);
		empMenuBtns.add(accRegBtn);
		
		changeRegBtn = new JButton("<html><center>Change</center></br><center> register</center></html>");
		changeRegBtn.setBounds(457, 59, 85, 47);
		empMenuBtns.add(changeRegBtn);
		
		freeBtn1 = new JButton("New button");
		freeBtn1.setBounds(253, 160, 85, 47);
		empMenuBtns.add(freeBtn1);
		
		freeBtn2 = new JButton("New button");
		freeBtn2.setBounds(457, 160, 85, 47);
		empMenuBtns.add(freeBtn2);
		
		
		new EmployeeMenuPanelController(this);

	}
	public void addActionListenerOnCashRegBtn(ActionListener listener) 
	{
		cashRegBtn.addActionListener(listener);
	}
	public void addActionListenerOnChangeRegBtn(ActionListener listener) 
	{
		changeRegBtn.addActionListener(listener);
	}
}
