package view;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

import common.MyDocFilter;
import common.TxtFieldListener;

import javax.swing.JLabel;
import javax.swing.JButton;

public class ExchangePanel extends JPanel implements TxtFieldListener{

	private static final long serialVersionUID = 1L;
	private JTextField rcvdTxt;
	private JTextField F1fiveThTxt;
	private JTextField F2twoTh;
	private JTextField F3oneThTxt;
	private JTextField F4fiveHTxt;
	private JTextField F5twoHTxt;
	private JTextField F6oneHTxt;
	private JTextField F7fifthTxt;
	private JTextField F8twenTxt;
	private JTextField F9tenTxt;
	private JTextField FTfiveTxt;
	private JTextField FEtwoTxt;
	private JTextField FWoneTxt;
	private JTextField empChangeTxt;
	private JTextField totalTxt;
	private JPanel changeInputPanel;
	private JLabel changeLbl;
	private JLabel totalLbl;
	private JLabel F1fiveThLbl;
	private JLabel F2twoThLbl;
	private JLabel F3oneThLbl;
	private JLabel F4fiveHLbl;
	private JLabel F5twoHLbl;
	private JLabel F6oneHLbl;
	private JLabel F7fifthLbl;
	private JLabel F8twenLbl;
	private JLabel F9tenLbl;
	private JLabel FTfiveLbl;
	private JLabel FEtwoLbl;
	private JLabel FWoneLbl;
	private JLabel rcvdAmtLbl;
	private JButton finBttn;

	private ExchangePanel() 
	{
		initialize();
	}
	
	private static Map<String, ExchangePanel> cache = new HashMap<>();

    public static ExchangePanel getExchangePanel(String sessionId) {
        if (!cache.containsKey(sessionId)) {
        	ExchangePanel cashRegPanelContr = new ExchangePanel();
            cache.put(sessionId, cashRegPanelContr);
        }
        return cache.get(sessionId);
    }

	public void initialize() {
		
		this.setPreferredSize(new Dimension(768, 524));
		setLayout(null);
		
		
		rcvdTxt = new JTextField();
		rcvdTxt.setHorizontalAlignment(SwingConstants.CENTER);
		rcvdTxt.setBounds(437, 13, 74, 19);
		add(rcvdTxt);
		((AbstractDocument)rcvdTxt.getDocument()).setDocumentFilter(new MyDocFilter("[\\d.]*"));
		rcvdTxt.setEditable(false);
		rcvdTxt.requestFocusInWindow();
		rcvdTxt.setColumns(10);
		
		rcvdAmtLbl = new JLabel("Recevied amt:");
		rcvdAmtLbl.setHorizontalAlignment(SwingConstants.CENTER);
		rcvdAmtLbl.setBounds(292, 13, 74, 19);
		add(rcvdAmtLbl);
		
		changeInputPanel = new JPanel();
		changeInputPanel.setBounds(173, 57, 373, 347);
		add(changeInputPanel);
		changeInputPanel.setLayout(null);
		
		F1fiveThTxt = new JTextField();
		F1fiveThTxt.setBounds(265, 0, 74, 19);
		changeInputPanel.add(F1fiveThTxt);
		F1fiveThTxt.setName("F1fiveThTxt");
		((AbstractDocument)F1fiveThTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		F1fiveThTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F1fiveThTxt.setColumns(10);
		
		F2twoTh = new JTextField();
		F2twoTh.setBounds(265, 29, 74, 19);
		changeInputPanel.add(F2twoTh);
		F2twoTh.setName("F2twoTh");
		((AbstractDocument)F2twoTh.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		F2twoTh.setHorizontalAlignment(SwingConstants.CENTER);
		F2twoTh.setColumns(10);
		
		F3oneThTxt = new JTextField();
		F3oneThTxt.setBounds(265, 58, 74, 19);
		changeInputPanel.add(F3oneThTxt);
		F3oneThTxt.setName("F3oneThTxt");
		((AbstractDocument)F3oneThTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		F3oneThTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F3oneThTxt.setColumns(10);
		
		F4fiveHTxt = new JTextField();
		F4fiveHTxt.setBounds(265, 86, 74, 19);
		changeInputPanel.add(F4fiveHTxt);
		F4fiveHTxt.setName("F4fiveHTxt");
		((AbstractDocument)F4fiveHTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		F4fiveHTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F4fiveHTxt.setColumns(10);
		
		F5twoHTxt = new JTextField();
		F5twoHTxt.setBounds(265, 115, 74, 19);
		changeInputPanel.add(F5twoHTxt);
		F5twoHTxt.setName("F5twoHTxt");
		((AbstractDocument)F5twoHTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		F5twoHTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F5twoHTxt.setColumns(10);
		
		F6oneHTxt = new JTextField();
		F6oneHTxt.setBounds(265, 146, 74, 19);
		changeInputPanel.add(F6oneHTxt);
		F6oneHTxt.setName("F6oneHTxt");
		((AbstractDocument)F6oneHTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		F6oneHTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F6oneHTxt.setColumns(10);
		
		F7fifthTxt = new JTextField();
		F7fifthTxt.setBounds(265, 175, 74, 19);
		changeInputPanel.add(F7fifthTxt);
		F7fifthTxt.setName("F7fifthTxt");
		((AbstractDocument)F7fifthTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		F7fifthTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F7fifthTxt.setColumns(10);
		
		F8twenTxt = new JTextField();
		F8twenTxt.setBounds(265, 205, 74, 19);
		changeInputPanel.add(F8twenTxt);
		F8twenTxt.setName("F8twenTxt");
		((AbstractDocument)F8twenTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		F8twenTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F8twenTxt.setColumns(10);
		
		F9tenTxt = new JTextField();
		F9tenTxt.setBounds(265, 234, 74, 19);
		changeInputPanel.add(F9tenTxt);
		F9tenTxt.setName("F9tenTxt");
		((AbstractDocument)F9tenTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		F9tenTxt.setHorizontalAlignment(SwingConstants.CENTER);
		F9tenTxt.setColumns(10);
		
		FTfiveTxt = new JTextField();
		FTfiveTxt.setBounds(265, 264, 74, 19);
		changeInputPanel.add(FTfiveTxt);
		FTfiveTxt.setName("FTfiveTxt");
		((AbstractDocument)FTfiveTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		FTfiveTxt.setHorizontalAlignment(SwingConstants.CENTER);
		FTfiveTxt.setColumns(10);
		
		FEtwoTxt = new JTextField();
		FEtwoTxt.setBounds(265, 294, 74, 19);
		changeInputPanel.add(FEtwoTxt);
		FEtwoTxt.setName("FEtwoTxt");
		((AbstractDocument)FEtwoTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		FEtwoTxt.setHorizontalAlignment(SwingConstants.CENTER);
		FEtwoTxt.setColumns(10);
		
		FWoneTxt = new JTextField();
		FWoneTxt.setBounds(265, 323, 74, 19);
		changeInputPanel.add(FWoneTxt);
		FWoneTxt.setName("FWoneTxt");
		((AbstractDocument)FWoneTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		FWoneTxt.setHorizontalAlignment(SwingConstants.CENTER);
		FWoneTxt.setColumns(10);
		
		F1fiveThLbl = new JLabel("5000");
		F1fiveThLbl.setBounds(122, 0, 74, 19);
		changeInputPanel.add(F1fiveThLbl);
		F1fiveThLbl.setName("F1fiveThLbl");
		F1fiveThLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		F2twoThLbl = new JLabel("2000");
		F2twoThLbl.setBounds(122, 29, 74, 19);
		changeInputPanel.add(F2twoThLbl);
		F2twoThLbl.setName("F2twoThLbl");
		F2twoThLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		F3oneThLbl = new JLabel("1000");
		F3oneThLbl.setBounds(122, 58, 74, 19);
		changeInputPanel.add(F3oneThLbl);
		F3oneThLbl.setName("F3oneThLbl");
		F3oneThLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		F4fiveHLbl = new JLabel("500");
		F4fiveHLbl.setBounds(122, 86, 74, 19);
		changeInputPanel.add(F4fiveHLbl);
		F4fiveHLbl.setName("F4fiveHLbl");
		F4fiveHLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		F5twoHLbl = new JLabel("200");
		F5twoHLbl.setBounds(122, 115, 74, 19);
		changeInputPanel.add(F5twoHLbl);
		F5twoHLbl.setName("F5twoHLbl");
		F5twoHLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		F6oneHLbl = new JLabel("100");
		F6oneHLbl.setBounds(122, 146, 74, 19);
		changeInputPanel.add(F6oneHLbl);
		F6oneHLbl.setName("F6oneHLbl");
		F6oneHLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		F7fifthLbl = new JLabel("50");
		F7fifthLbl.setBounds(122, 175, 74, 19);
		changeInputPanel.add(F7fifthLbl);
		F7fifthLbl.setName("F7fifthLbl");
		F7fifthLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		F8twenLbl = new JLabel("20");
		F8twenLbl.setBounds(122, 205, 74, 19);
		changeInputPanel.add(F8twenLbl);
		F8twenLbl.setName("F8twenLbl");
		F8twenLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		F9tenLbl = new JLabel("10");
		F9tenLbl.setBounds(122, 234, 74, 19);
		changeInputPanel.add(F9tenLbl);
		F9tenLbl.setName("F9tenLbl");
		F9tenLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		FTfiveLbl = new JLabel("5");
		FTfiveLbl.setBounds(122, 264, 74, 19);
		changeInputPanel.add(FTfiveLbl);
		FTfiveLbl.setName("FTfiveLbl");
		FTfiveLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		FEtwoLbl = new JLabel("2");
		FEtwoLbl.setBounds(122, 294, 74, 19);
		changeInputPanel.add(FEtwoLbl);
		FEtwoLbl.setName("FEtwoLbl");
		FEtwoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		FWoneLbl = new JLabel("1");
		FWoneLbl.setBounds(122, 323, 74, 19);
		changeInputPanel.add(FWoneLbl);
		FWoneLbl.setName("FWoneLbl");
		FWoneLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		changeLbl = new JLabel("Change:");
		changeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		changeLbl.setBounds(292, 425, 74, 19);
		add(changeLbl);
		
		empChangeTxt = new JTextField();
		empChangeTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		empChangeTxt.setColumns(10);
		empChangeTxt.setEditable(false);
		empChangeTxt.setBounds(437, 425, 74, 19);
		add(empChangeTxt);
		
		totalLbl = new JLabel("Total:");
		totalLbl.setBounds(601, 16, 45, 19);
		add(totalLbl);
		
		totalTxt = new JTextField();
		totalTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		totalTxt.setColumns(10);
		totalTxt.setEditable(false);
		totalTxt.setBounds(656, 13, 74, 19);
		add(totalTxt);
		
		finBttn = new JButton("Finish");
		finBttn.setBounds(645, 424, 74, 19);
		add(finBttn);
		
		setFocusable(true);

	}
	@Override
	public JPanel getDenomPanelInstance() {
		return changeInputPanel;
	
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
	public void setChange(double change) 
	{
		try
		{
		String changeText  = String.valueOf(change);
		empChangeTxt.setText(changeText);
		}catch(NumberFormatException e) 
		{
			System.out.println("setChange catch block");
			e.printStackTrace();
		}
	}
	public double getChange() 
	{
		try
		{
			return Double.parseDouble(empChangeTxt.getText());
		}catch(NumberFormatException e) 
		{
			return 0.0;
		}
		
	}
	public void addFocusListeneOnRcvdTxt(FocusListener listener) 
	{
		rcvdTxt.addFocusListener(listener);
	}
	public void addActionListenerOnFin(ActionListener listener) 
	{
		finBttn.addActionListener(listener);
	}

	

}
