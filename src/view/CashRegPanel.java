package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import common.BackButton;
import common.MyDocFilter;
import common.OrderTable;
import common.SessionManager;
import dao.DBOrderManipulationDao;
import model.OrdersModel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class CashRegPanel extends JPanel implements OrderTable<Object>{

	private static final long serialVersionUID = 1L;
	private JLabel invoiceLbl;
	private JLabel payerCityLbl;
	private JLabel payerAddLbl;
	private JLabel payerNameLBL;
	private JLabel recAccTxtField;
	private JLabel refNoLbl;
	private JLabel amtLbl;
	private JPanel infoPanel;
	private JTextField invoiceAmtTxt;
	private JTextField amtTxt;
	private JTextField fullnameTxt;
	private JTextField addressTxt;
	private JTextField cityTxt;
	private JTextField refNoTxt;
	private JTextField modelTxt;
	private JTextField recAccTxt1;
	private JTextField recAccTxt2;
	private JTextField recAccTxt3;
	private JTextField commTxt;
	private JTextField totalTxt;
	private DefaultTableModel model;
	private JTable table;
	private JCheckBox commChck;
	private JButton finBtn;
	private JLabel totalLbl;
	private JButton billBtn;
	private JButton sendBtn;
	private BackButton backBtn;
	
	public CashRegPanel() {
	        initialize();
	       
	}
	 private static Map<String, CashRegPanel> cache = new ConcurrentHashMap<>();

	    public static CashRegPanel getCashRegPanel(String sessionId) {
	        if (!cache.containsKey(sessionId)) {
	        	CashRegPanel cashRegPanel = new CashRegPanel();
	            cache.put(sessionId, cashRegPanel);
	        }
	        return cache.get(sessionId);
	    }


	public void initialize() {
		this.setPreferredSize(new Dimension(768, 524));
		setLayout(null);
		
		
		
		infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBounds(241, 20, 280, 313);
		add(infoPanel);
	
		backBtn = new BackButton();
		backBtn.setBounds(0, 0, 25, 25);
		add(backBtn);
		
		amtLbl = new JLabel("Amount:");
		amtLbl.setBounds(10, 20, 105, 17);
		infoPanel.add(amtLbl);
		
		refNoLbl = new JLabel("Reference No");
		refNoLbl.setBounds(10, 54, 105, 17);
		infoPanel.add(refNoLbl);
		
		recAccTxtField = new JLabel("Recipient's acc");
		recAccTxtField.setBounds(10, 88, 105, 17);
		infoPanel.add(recAccTxtField);
		
		payerNameLBL = new JLabel("Payer's full name:");
		payerNameLBL.setBounds(10, 122, 105, 17);
		infoPanel.add(payerNameLBL);
		
		payerAddLbl = new JLabel("Payer's address");
		payerAddLbl.setBounds(10, 156, 105, 17);
		infoPanel.add(payerAddLbl);
		
		payerCityLbl = new JLabel("Payer's city:");
		payerCityLbl.setBounds(10, 190, 105, 17);
		infoPanel.add(payerCityLbl);
		
		invoiceLbl = new JLabel("Invoice amt:");
		invoiceLbl.setBounds(115, 245, 83, 17);
		infoPanel.add(invoiceLbl);
		
		invoiceAmtTxt = new JTextField();
		invoiceAmtTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		invoiceAmtTxt.setEditable(false);
		invoiceAmtTxt.setColumns(10);
		invoiceAmtTxt.setBounds(201, 243, 62, 22);
		infoPanel.add(invoiceAmtTxt);
		
		amtTxt = new JTextField();
		amtTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		((AbstractDocument)amtTxt.getDocument()).setDocumentFilter(new MyDocFilter("[\\d.]*"));
		amtTxt.setColumns(10);
		amtTxt.setBounds(167, 18, 96, 22);
		amtTxt.requestFocusInWindow();
		infoPanel.add(amtTxt);
		
		fullnameTxt = new JTextField();
		fullnameTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		fullnameTxt.setColumns(10);
		fullnameTxt.setBounds(115, 120, 150, 22);
		infoPanel.add(fullnameTxt);
		
		addressTxt = new JTextField();
		addressTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		addressTxt.setColumns(10);
		addressTxt.setBounds(115, 154, 150, 22);
		infoPanel.add(addressTxt);
		
		cityTxt = new JTextField();
		cityTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		cityTxt.setColumns(10);
		
		cityTxt.setBounds(115, 188, 150, 22);
		infoPanel.add(cityTxt);
		
		refNoTxt = new JTextField();
		refNoTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		refNoTxt.setColumns(10);
		refNoTxt.setBounds(147, 52, 116, 22);
		infoPanel.add(refNoTxt);
		
		modelTxt = new JTextField(10);
		modelTxt.setHorizontalAlignment(SwingConstants.CENTER);
		((AbstractDocument) modelTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,2}(?!\\d)"));
		modelTxt.setBounds(115, 52, 30, 22);
		infoPanel.add(modelTxt);
		
		recAccTxt1 = new JTextField(10);
		recAccTxt1.setHorizontalAlignment(SwingConstants.CENTER);
		((AbstractDocument)recAccTxt1.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,3}"));
		recAccTxt1.setBounds(115, 86, 30, 22);
		infoPanel.add(recAccTxt1);
		
		recAccTxt2 = new JTextField();
		recAccTxt2.setHorizontalAlignment(SwingConstants.RIGHT);
		((AbstractDocument)recAccTxt2.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		recAccTxt2.setColumns(10);
		recAccTxt2.setBounds(147, 86, 84, 22);
		infoPanel.add(recAccTxt2);
		
		recAccTxt3 = new JTextField(10);
		recAccTxt3.setHorizontalAlignment(SwingConstants.CENTER);
		((AbstractDocument)recAccTxt3.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,3}"));
		recAccTxt3.setBounds(233, 86, 30, 22);
		infoPanel.add(recAccTxt3);
		
		commTxt = new JTextField();
		commTxt.setEditable(false);
		commTxt.setColumns(10);
		commTxt.setBounds(147, 216, 33, 20);
		infoPanel.add(commTxt);
		
		commChck = new JCheckBox("Commision");
		commChck.setBounds(188, 216, 75, 21);
		infoPanel.add(commChck);
		
		
		String[] columns = {"S.No", "Name", "Address", "Ref. No", "Rec. Acc.", "Amount", "Commision", "Invoice amt", "Date"};
		model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(23, 343, 711, 132);
		add(scrollPane);
	
		
		sendBtn = new JButton("Send");
		sendBtn.setBounds(675, 296, 59, 37);
		setSendBtn();
		add(sendBtn);
		
		
		billBtn = new JButton("Billing");
		billBtn.setBounds(531, 296, 59, 37);
		add(billBtn);
		
		finBtn = new JButton("Finish");
		finBtn.setBounds(204, 276, 59, 37);
		setFinBtn();
		infoPanel.add(finBtn);
		
		totalTxt = new JTextField();
		totalTxt.setBounds(635, 261, 99, 25);
		totalTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		add(totalTxt);
		setTotalTxt();
		setBillBtn();
		totalTxt.setEditable(false);
		totalTxt.setColumns(10);
		
		
		totalLbl = new JLabel("Total:");
		totalLbl.setBounds(531, 263, 84, 17);
		add(totalLbl);
		totalLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
	
	}


	@Override
	public void showOrderInTable(Object... param) {
		model.addRow(param);
		
	}
	@Override
	public void clearTable() 
	{
		model.setRowCount(0);
	}
	public void addMouseListenerOnTable(MouseListener listener) 
	{
		table.addMouseListener(listener);
	}
	public JTable getInstanceOfTable() 
	{
		return table;
	}
	public void selectRows(int sNo) {
		
		
		 SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
		 for (int i = 0; i < model.getRowCount(); i++) {
	            int rowsNo = (int) model.getValueAt(i, model.findColumn("S.No"));
	            if (rowsNo == sNo) 
	            {
	                table.addRowSelectionInterval(i, i);
	                
	                break;
	            }
	        }
		        }
		        });
	    }
	public void deselectRow() {
		 SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
		        	table.clearSelection();
		        }
		        
	});
	    }
	
	public JPanel getInfoPanel() 
	{
		return infoPanel;
	}
	/*public JPanel getCashRegPanel() 
	{
		return this;
	}*/
	
	
	public void setAmtTxt(double amtText) 
	{
		amtTxt.setText(Double.toString(amtText));
	}
	public double getAmtTxt() 
	{
	        try {
	            return Double.parseDouble(amtTxt.getText());
	        } catch (NumberFormatException ex) {
	        	return 0.0;
	        }
	}
	
	
	public void setInvoiceAmtTxt(double invoiceAmtText) 
	{
		invoiceAmtTxt.setText(Double.toString(Math.ceil(invoiceAmtText)));
	}
	public double getInvoiceAmtTxt() 
	{ 
	        try {
	        	return Double.parseDouble(invoiceAmtTxt.getText());
	        } catch (NumberFormatException ex) {
	            return 0.0;
	        }
	}
	
	public void setRefNo(String refNo) 
	{
		
		String model = refNo.substring(0,2);
		modelTxt.setText(model);
		String refN = refNo.substring(3);
		refNoTxt.setText(refN);
	}
	public String getRefNo() 
	{
		StringBuilder sb = new StringBuilder(modelTxt.getText());
	    sb.append("-");
	    sb.append(refNoTxt.getText());
	    return sb.toString();
	}
	public void setRecAcc(String recAcc) 
	{
		String recAcc1 = recAcc.substring(0,3);
		recAccTxt1.setText(recAcc1);
		String recAcc2 = recAcc.substring(4, Math.min(recAcc.length(), 10));
		recAccTxt2.setText(recAcc2);
		String recAcc3 = recAcc.substring(recAcc2.length()+1, recAcc.length());
		recAccTxt3.setText(recAcc3);	
	}
	public String getRecAcc() 
	{
		StringBuilder sb = new StringBuilder(recAccTxt1.getText());
		sb.append("-").append(recAccTxt2.getText()).append("-").append(recAccTxt3.getText());
		return sb.toString();
	}
	public void setFullName(String fullName) 
	{
		fullnameTxt.setText(fullName);
	}
	public String getFullName() 
	{
		return fullnameTxt.getText();
	}
	public void setAddress(String address) 
	{
		addressTxt.setText(address);
	}
	public String getAddress() 
	{
		return addressTxt.getText();
	}
	public void setCity(String city) 
	{
		cityTxt.setText(city);
	}
	public String getCity() 
	{
		return cityTxt.getText();
	}
	public double getCommTxt() 
	{
	        try {
	        	return Double.parseDouble(commTxt.getText());
	        } catch (NumberFormatException ex) {
	            return 0.0;
	        }
	}
	public void setCommTxt(double commText) 
	{
		
		commTxt.setText(String.valueOf(Math.ceil(commText)));
	}
	
	public boolean getCommChck() 
	{
		
		return commChck.isSelected();
		
	}
	public double getTotalTxt() 
	{
		try 
		{
			return Double.parseDouble(totalTxt.getText());
		}
		catch(NumberFormatException ex) 
		{
			return 0.0;
		}
		
	}
	public void setTotalTxt() 
	{
		totalTxt.setText(String.valueOf(DBOrderManipulationDao.getTotal(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now())));
	}
	public void setFinBtn() 
	{
		if(sendBtn.isEnabled()) 
		{
			finBtn.setEnabled(false);
		}else finBtn.setEnabled(true);
	}
	
	public void setBillBtn() 
	{
		if(totalTxt == null || getTotalTxt() == 0.0 || sendBtn.isEnabled()) 
		{
			billBtn.setEnabled(false);
		}else {
			billBtn.setEnabled(true);
		}
		
	}
	public void setSendBtn() 
	{
		int empId = SessionManager.getEmpIdBySession(SessionManager.getCurrSess());
		OrdersModel toBeReversedList = DBOrderManipulationDao.selectOrderForReverse(empId, LocalDate.now());
		ArrayList<OrdersModel> paidList = DBOrderManipulationDao.getPaid(empId, LocalDate.now());
		if(!(paidList.isEmpty()) || toBeReversedList!=null) 
		{
			sendBtn.setEnabled(true);
		}else sendBtn.setEnabled(false);
	}

	public void addFocusListenerOnAmt(FocusListener focus) 
	{
		amtTxt.addFocusListener(focus);
	}
	public void addFocusListenerOnTxtFields(FocusListener focus) 
	{
		refNoTxt.addFocusListener(focus);
		fullnameTxt.addFocusListener(focus);
		addressTxt.addFocusListener(focus);
		cityTxt.addFocusListener(focus);
	}
	

	public void addActionListenerOnCheck(ActionListener listener) 
	{
		commChck.addActionListener(listener);
	}
	public void addActionListenerOnFinish(ActionListener listener) 
	{
		finBtn.addActionListener(listener);
	}
	public void addActionListenerOnBillBtn(ActionListener listener) 
	{
		billBtn.addActionListener(listener);
	}
	public void addActionListenerOnSendBtn(ActionListener listener) 
	{
		sendBtn.addActionListener(listener);
	}

	
}
