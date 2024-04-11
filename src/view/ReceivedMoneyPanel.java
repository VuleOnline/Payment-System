package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import common.BackButton;
import common.CommonMethods;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class ReceivedMoneyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel denominationPanel;
	private JPanel backButtonPanel;
	private BackButton backBtn;
	private JPanel contentPanel;
	private JTextField F1fiveThTxt;
	private JTextField F2twoTh;
	private JTextField F3oneThTxt;
	private JTextField F4fiveHTxt;
	private JTextField FWoneTxt;
	private JTextField FEtwoTxt;
	private JTextField F5twoHTxt;
	private JTextField F6oneHTxt;
	private JTextField F7fifthTxt;
	private JTextField F8twenTxt;
	private JTextField F9tenTxt;
	private JTextField FTfiveTxt;
	private JTextField totalTxt;
	private JTextField rcvdTxt;
	private JTextPane textPane;
	private JLabel rcvdAmtLbl;
	private JLabel totalLbl;
	private JPanel sumPanel;
	private JLabel FEtwoLbl;
	private JLabel F5twoHLbl;
	private JLabel FWoneLbl;
	private JLabel F8twenLbl;
	private JLabel FTfiveLbl;
	private JLabel F6oneHLbl;
	private JLabel F9tenLbl;
	private JLabel F2twoThLbl;
	private JLabel F4fiveHLbl;
	private JLabel F3oneThLbl;
	private JLabel F7fifthLbl;
	private JLabel F1fiveThLbl;
	private JButton nextBtn;

	private ReceivedMoneyPanel() {
		
		initialize();
	}
	private static Map<String, ReceivedMoneyPanel> cache = new HashMap<>();
	public static ReceivedMoneyPanel getRecivedMoneyPanel(String sessionId) 
	{
		if(!cache.containsKey(sessionId)) 
		{
			ReceivedMoneyPanel recMoneyPanel  = new ReceivedMoneyPanel();
			cache.put(sessionId, recMoneyPanel);
		}
		return cache.get(sessionId);
	}
	public void initialize() 
	{
		this.setPreferredSize(new Dimension(768, 524));
		setLayout(new BorderLayout());
		
		backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    backBtn = new BackButton();
	    backButtonPanel.add(backBtn);

	    add(backButtonPanel, BorderLayout.WEST);
	    
	    contentPanel = new JPanel();
		add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		denominationPanel = new JPanel();
		denominationPanel.setBounds(279, 46, 199, 347);
		denominationPanel.setPreferredSize(new Dimension((int)(this.getPreferredSize().width*0.3), (int)(this.getPreferredSize().height*0.8)));
		denominationPanel.setLayout(null);
		contentPanel.add(denominationPanel);
		
		F1fiveThLbl = new JLabel("5000");
		F1fiveThLbl.setBounds(24, 8, 39, 19);
		F1fiveThLbl.setName("F1fiveThLbl");
		F1fiveThLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(F1fiveThLbl);
		
		F1fiveThTxt = new JTextField();
		F1fiveThTxt.setBounds(97, 5, 96, 19);
		F1fiveThTxt.setName("F1fiveThTxt");
		F1fiveThTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F1fiveThTxt.setColumns(10);
		denominationPanel.add(F1fiveThTxt);
		
		F2twoTh = new JTextField();
		F2twoTh.setBounds(97, 35, 96, 19);
		F2twoTh.setName("F2twoTh");
		F2twoTh.setHorizontalAlignment(SwingConstants.CENTER);
		F2twoTh.setColumns(10);
		denominationPanel.add(F2twoTh);
		
		F3oneThTxt = new JTextField();
		F3oneThTxt.setBounds(97, 64, 96, 19);
		F3oneThTxt.setName("F3oneThTxt");
		F3oneThTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F3oneThTxt.setColumns(10);
		denominationPanel.add(F3oneThTxt);
		
		F7fifthLbl = new JLabel("50");
		F7fifthLbl.setBounds(24, 178, 39, 19);
		F7fifthLbl.setName("F7fifthLbl");
		F7fifthLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(F7fifthLbl);
		
		F4fiveHTxt = new JTextField();
		F4fiveHTxt.setBounds(97, 93, 96, 19);
		F4fiveHTxt.setName("F4fiveHTxt");
		F4fiveHTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F4fiveHTxt.setColumns(10);
		denominationPanel.add(F4fiveHTxt);
		
		F3oneThLbl = new JLabel("1000");
		F3oneThLbl.setBounds(24, 64, 39, 19);
		F3oneThLbl.setName("F3oneThLbl");
		F3oneThLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(F3oneThLbl);
		
		F2twoThLbl = new JLabel("2000");
		F2twoThLbl.setBounds(24, 37, 39, 19);
		F2twoThLbl.setName("F2twoThLbl");
		F2twoThLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(F2twoThLbl);
		
		F4fiveHLbl = new JLabel("500");
		F4fiveHLbl.setBounds(24, 93, 39, 19);
		F4fiveHLbl.setName("F4fiveHLbl");
		F4fiveHLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(F4fiveHLbl);
		
		
		F9tenLbl = new JLabel("10");
		F9tenLbl.setBounds(24, 236, 39, 19);
		F9tenLbl.setName("F9tenLbl");
		F9tenLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(F9tenLbl);
		
		F6oneHLbl = new JLabel("100");
		F6oneHLbl.setBounds(24, 149, 39, 19);
		F6oneHLbl.setName("F6oneHLbl");
		F6oneHLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(F6oneHLbl);
		
		FTfiveLbl = new JLabel("5");
		FTfiveLbl.setBounds(24, 265, 39, 19);
		FTfiveLbl.setName("FTfiveLbl");
		FTfiveLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(FTfiveLbl);
		
		F8twenLbl = new JLabel("20");
		F8twenLbl.setBounds(24, 207, 39, 19);
		F8twenLbl.setName("F8twenLbl");
		F8twenLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(F8twenLbl);
		
		FWoneLbl = new JLabel("1");
		FWoneLbl.setBounds(24, 321, 39, 19);
		FWoneLbl.setName("FWoneLbl");
		FWoneLbl.setHorizontalAlignment(SwingConstants.CENTER);
		denominationPanel.add(FWoneLbl);
		
		FEtwoTxt = new JTextField();
		FEtwoTxt.setBounds(97, 294, 96, 19);
		FEtwoTxt.setName("FEtwoTxt");
		FEtwoTxt.setHorizontalAlignment(SwingConstants.CENTER);
		FEtwoTxt.setColumns(10);
		denominationPanel.add(FEtwoTxt);
		
		F5twoHTxt = new JTextField();
		F5twoHTxt.setBounds(97, 122, 96, 19);
		F5twoHTxt.setName("F5twoHTxt");
		F5twoHTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F5twoHTxt.setColumns(10);
		denominationPanel.add(F5twoHTxt);
		
		F6oneHTxt = new JTextField();
		F6oneHTxt.setBounds(97, 149, 96, 19);
		F6oneHTxt.setName("F6oneHTxt");
		F6oneHTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F6oneHTxt.setColumns(10);
		denominationPanel.add(F6oneHTxt);
		
		F7fifthTxt = new JTextField();
		F7fifthTxt.setBounds(97, 178, 96, 19);
		F7fifthTxt.setName("F7fifthTxt");
		F7fifthTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F7fifthTxt.setColumns(10);
		denominationPanel.add(F7fifthTxt);
		
		F8twenTxt = new JTextField();
		F8twenTxt.setBounds(97, 207, 96, 19);
		F8twenTxt.setName("F8twenTxt");
		F8twenTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F8twenTxt.setColumns(10);
		denominationPanel.add(F8twenTxt);
		
		F9tenTxt = new JTextField();
		F9tenTxt.setBounds(97, 236, 96, 19);
		F9tenTxt.setName("F9tenTxt");
		F9tenTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F9tenTxt.setColumns(10);
		denominationPanel.add(F9tenTxt);
		
		FTfiveTxt = new JTextField();
		FTfiveTxt.setBounds(97, 265, 96, 19);
		FTfiveTxt.setName("FTfiveTxt");
		FTfiveTxt.setHorizontalAlignment(SwingConstants.CENTER);
		FTfiveTxt.setColumns(10);
		denominationPanel.add(FTfiveTxt);
		
		F5twoHLbl = new JLabel("200");
		F5twoHLbl.setName("F5twoHLbl");
		F5twoHLbl.setHorizontalAlignment(SwingConstants.CENTER);
		F5twoHLbl.setBounds(24, 122, 39, 19);
		denominationPanel.add(F5twoHLbl);
		
		FWoneTxt = new JTextField();
		FWoneTxt.setBounds(97, 321, 96, 19);
		FWoneTxt.setName("FWoneTxt");
		FWoneTxt.setHorizontalAlignment(SwingConstants.CENTER);
		FWoneTxt.setColumns(10);
		denominationPanel.add(FWoneTxt);
		
		FEtwoLbl = new JLabel("2");
		FEtwoLbl.setName("FEtwoLbl");
		FEtwoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		FEtwoLbl.setBounds(24, 294, 39, 19);
		denominationPanel.add(FEtwoLbl);
		
		sumPanel = new JPanel();
		sumPanel.setBounds(279, 403, 199, 54);
		sumPanel.setLayout(null);
		contentPanel.add(sumPanel);
		
		totalLbl = new JLabel("Total");
		totalLbl.setBounds(25, 27, 39, 19);
		sumPanel.add(totalLbl);
		
		totalTxt = new JTextField();
		totalTxt.setBounds(93, 27, 96, 19);
		totalTxt.setEditable(false);
		totalTxt.setColumns(10);
		sumPanel.add(totalTxt);
		
		rcvdAmtLbl = new JLabel("Rcvd");
		rcvdAmtLbl.setBounds(25, 1, 39, 19);
		sumPanel.add(rcvdAmtLbl);
		
		rcvdTxt = new JTextField();
		rcvdTxt.setColumns(10);
		rcvdTxt.setEditable(false);
		rcvdTxt.setBounds(93, 1, 96, 19);
		sumPanel.add(rcvdTxt);
		
		textPane = new JTextPane();
		textPane.setText("Type the amount of received money: ");
		textPane.setBounds(279, 18, 199, 19);
		contentPanel.add(textPane);
		
		nextBtn = new JButton("Next");
		nextBtn.setBounds(584, 430, 85, 21);
		contentPanel.add(nextBtn);
		 
		
	}
	
	public JPanel getDenomPanelInstance() 
	{
		return denominationPanel;
	}
	
	public JPanel getSumPanelInstance() 
	{
		return sumPanel;
	}
	
	public void setTotal(double totalText )
	{
		try {
		String total = String.valueOf(totalText);
		totalTxt.setText(total);
		}catch(NumberFormatException e) 
		{
			System.out.println("setTotal catch block");
			e.printStackTrace();
		}
	}
	public double getTotal() 
	{
		try {
		return Double.parseDouble(totalTxt.getText()); 
		}catch(NumberFormatException e) 
		{
			return 0.0;
		}
	}
	public void setRcvd(double rcvd) 
	{	
		try 
		{
		String rcvdText = String.valueOf(rcvd);
		rcvdTxt.setText(rcvdText);
		}catch (NumberFormatException e) 
		{
			System.out.println("setRcvd catch block");
			e.printStackTrace();
		}
	}
	public double getRcvd() 
	{
		try
		{
			return Double.parseDouble(rcvdTxt.getText());
		}catch(NumberFormatException e) 
		{
			return 0.0;
		}
	}
	public void addFocusListeneOnTxtFields(FocusListener listener) 
	{
		ArrayList<JTextField> compList =  CommonMethods.fieldsToList(getDenomPanelInstance());
		for(JTextField list : compList) 
		{
			list.addFocusListener(listener);
		}
	}
	public void addActionLIstenerOnNextBtn(ActionListener listener) 
	{
		nextBtn.addActionListener(listener);
	}
}
