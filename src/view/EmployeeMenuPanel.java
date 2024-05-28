package view;

import javax.swing.JPanel;

import common.Logout;
import controller.EmployeeMenuPanelController;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;

public class EmployeeMenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel empMenuBtns;
	private JButton cashRegBtn;
	private JButton cashRegBalanceBtn;
	private JButton ordRegBtn;
	private JButton aggOrderBtn;
	private JButton cashReqBtn;
	private JButton cashDepoBtn;
	
	private EmployeeMenuPanel() {
		initialize();
	}
	
	private static Map<String, EmployeeMenuPanel> cache = new ConcurrentHashMap<>();

    public static EmployeeMenuPanel getEmployeeMenuPanel(String sessionId) {
        if (!cache.containsKey(sessionId)) {
        	EmployeeMenuPanel empMenuPanel = new EmployeeMenuPanel();
            cache.put(sessionId, empMenuPanel);
        }
        return cache.get(sessionId);
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
		
		cashRegBalanceBtn = new JButton("<html><center>Cash</center></br><center> Balance</center></html>");
		cashRegBalanceBtn.setBounds(64, 160, 85, 47);
		empMenuBtns.add(cashRegBalanceBtn);
		
		ordRegBtn = new JButton("<html><center>Order</center></br><center> register</center></html>");
		ordRegBtn.setBounds(253, 59, 85, 47);
		ordRegBtn.setEnabled(false);
		empMenuBtns.add(ordRegBtn);
		
		aggOrderBtn = new JButton("<html><center>Aggregate</center></br><center> orders</center></html>");
		aggOrderBtn.setBounds(457, 59, 85, 47);
		empMenuBtns.add(aggOrderBtn);
		
		cashReqBtn = new JButton("<html><center>Cash</center></br><center> Requisition</center></html>");
		cashReqBtn.setBounds(253, 160, 85, 47);
		empMenuBtns.add(cashReqBtn);
		
		cashDepoBtn = new JButton("<html><center>Cash</center></br><center> Deposition</center></html>");
		cashDepoBtn.setBounds(457, 160, 85, 47);
		empMenuBtns.add(cashDepoBtn);
		
		
		new EmployeeMenuPanelController(this);

	}
	public void addActionListenerOnCashRegBtn(ActionListener listener) 
	{
		cashRegBtn.addActionListener(listener);
	}
	public void addActionListenerOnAggOrderBtn(ActionListener listener) 
	{
		aggOrderBtn.addActionListener(listener);
	}
	public void addActionListenerOnCashReqBtn(ActionListener listener) 
	{
		cashReqBtn.addActionListener(listener);
	}
	public void addActionListenerOnCashDepoBtn(ActionListener listener) 
	{
		cashDepoBtn.addActionListener(listener);
	}
	public void addActionListenerOnCashBalBtn(ActionListener listener) 
	{
		cashRegBalanceBtn.addActionListener(listener);
	}
}
