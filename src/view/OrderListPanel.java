package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import common.BackButton;
import common.OrderTable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class OrderListPanel extends JPanel implements OrderTable<Object>{

	private static final long serialVersionUID = 1L;
	private JPanel inputFieldPanel;
	private JPanel tablePanel;
	private JPanel infoPanel;
	private JLabel dateLbl;
	private JLabel empIdLbl;
	private JTextField idTxt;
	private JLabel sNoLbl;
	private JTextField snoTxt;
	private JPanel dateLblTxtPanel;
	private JPanel idTxtLblPanel;
	private JPanel snoTxtLblPanel;
	private JRadioButton allRadioBtn;
	private JPanel rbtnAllPanel;
	private JPanel rbtnForwPanel;
	private JRadioButton forwRadioBtn;
	private JPanel rbtnCancPanel;
	private JRadioButton cancRadioBtn;
	private JPanel btnResetPanel;
	private JButton resetBtn;
	private ButtonGroup btnGroup;
	private JTable table;
	private DefaultTableModel model;
	private JFormattedTextField dateTxt;
	private MaskFormatter maskForm;
	private JLabel empPassLblinfo;
	private JLabel empIdLblinfo;
	private JLabel empNameLblinfo;
	private JLabel empLnameLblinfo;
	private JLabel empUnameinfo;
	private JPanel IDpanel;
	private JPanel namePanel;
	private JPanel fnamePanel;
	private JPanel unamePanel;
	private JPanel passPanel;
	private BackButton backBtn;
	private JPanel centerContentPanel;
	private JPanel backButtonPanel;
	private JPanel rbtnRevPanel;
	private JRadioButton revRadioBtn;
	
	public OrderListPanel() {
			initialize();
		}
	private static Map<String, OrderListPanel> cache = new ConcurrentHashMap<>();
	 public static OrderListPanel getOrderListPanel(String sessionId) 
	    {
		 if(!cache.containsKey(sessionId)) 
			{
			 OrderListPanel orderListPanel  = new OrderListPanel();
				cache.put(sessionId, orderListPanel);
			}
			return cache.get(sessionId);
	    }

	public void initialize() {
		this.setPreferredSize(new Dimension(768, 524));
		//this.setPreferredSize(new Dimension((int)(Frame.getInstance().getPreferredSize().width * 0.99), (int)(Frame.getInstance().getPreferredSize().height * 0.80)));
		setLayout(new BorderLayout());
		
		
		backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    backBtn = new BackButton();
	    backButtonPanel.add(backBtn);

	    add(backButtonPanel, BorderLayout.NORTH);
		
		centerContentPanel = new JPanel();
		centerContentPanel.setPreferredSize(new Dimension((int)(this.getPreferredSize().width * 0.85), (int)(this.getPreferredSize().height * 0.85)));
		centerContentPanel.setLayout(new FlowLayout());
		add(centerContentPanel, BorderLayout.CENTER);
		
		inputFieldPanel = new JPanel();
		inputFieldPanel.setPreferredSize(new Dimension((int)(centerContentPanel.getPreferredSize().width * 0.99), (int)(centerContentPanel.getPreferredSize().height * 0.25)));
		centerContentPanel.add(inputFieldPanel);
		
		dateLblTxtPanel = new JPanel();
		dateLblTxtPanel.setPreferredSize(new Dimension((int)(inputFieldPanel.getPreferredSize().width * 0.3), (int)(inputFieldPanel.getPreferredSize().height * 0.40)));
		inputFieldPanel.add(dateLblTxtPanel);
		
		dateLbl = new JLabel("Date:");
		dateLblTxtPanel.add(dateLbl);
		dateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		try {
			maskForm = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        dateTxt = new JFormattedTextField(maskForm);
		dateLblTxtPanel.add(dateTxt);
		dateTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		dateTxt.setColumns(10);
		
		snoTxtLblPanel = new JPanel();
		snoTxtLblPanel.setPreferredSize(new Dimension((int)(inputFieldPanel.getPreferredSize().width * 0.3), (int)(inputFieldPanel.getPreferredSize().height * 0.40)));
		inputFieldPanel.add(snoTxtLblPanel);
		
		sNoLbl = new JLabel("SNo");
		snoTxtLblPanel.add(sNoLbl);
		
		snoTxt = new JTextField();
		snoTxtLblPanel.add(snoTxt);
		snoTxt.setColumns(10);
		
		idTxtLblPanel = new JPanel();
		idTxtLblPanel.setPreferredSize(new Dimension((int)(inputFieldPanel.getPreferredSize().width * 0.3), (int)(inputFieldPanel.getPreferredSize().height * 0.40)));
		inputFieldPanel.add(idTxtLblPanel);
		
		empIdLbl = new JLabel("Employee ID");
		idTxtLblPanel.add(empIdLbl);
		
		idTxt = new JTextField();
		idTxtLblPanel.add(idTxt);
		idTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		idTxt.setColumns(10);
		
		rbtnAllPanel = new JPanel();
		rbtnAllPanel.setPreferredSize(new Dimension((int)(inputFieldPanel.getPreferredSize().width * 0.16), (int)(inputFieldPanel.getPreferredSize().height * 0.40)));
		inputFieldPanel.add(rbtnAllPanel);
		
		btnGroup = new ButtonGroup();
		allRadioBtn = new JRadioButton("All");
		btnGroup.add(allRadioBtn);
		rbtnAllPanel.add(allRadioBtn);
		
		rbtnRevPanel = new JPanel();
		rbtnRevPanel.setPreferredSize(new Dimension((int)(inputFieldPanel.getPreferredSize().width * 0.16), (int)(inputFieldPanel.getPreferredSize().height * 0.40)));
		inputFieldPanel.add(rbtnRevPanel);
		
		revRadioBtn = new JRadioButton("Reversed");
		btnGroup.add(revRadioBtn);
		rbtnRevPanel.add(revRadioBtn);
		
		rbtnForwPanel = new JPanel();
		rbtnForwPanel.setPreferredSize(new Dimension((int)(inputFieldPanel.getPreferredSize().width * 0.16), (int)(inputFieldPanel.getPreferredSize().height * 0.40)));
		inputFieldPanel.add(rbtnForwPanel);
		
		forwRadioBtn = new JRadioButton("Forwarded");
		btnGroup.add(forwRadioBtn);
		rbtnForwPanel.add(forwRadioBtn);
		
		rbtnCancPanel = new JPanel();
		rbtnCancPanel.setPreferredSize(new Dimension((int)(inputFieldPanel.getPreferredSize().width * 0.16), (int)(inputFieldPanel.getPreferredSize().height * 0.40)));
		inputFieldPanel.add(rbtnCancPanel);
		
		cancRadioBtn = new JRadioButton("Cancelled");
		btnGroup.add(cancRadioBtn);
		rbtnCancPanel.add(cancRadioBtn);
		
		btnResetPanel = new JPanel();
		btnResetPanel.setPreferredSize(new Dimension((int)(inputFieldPanel.getPreferredSize().width * 0.16), (int)(inputFieldPanel.getPreferredSize().height * 0.40)));
		inputFieldPanel.add(btnResetPanel);
		
		resetBtn = new JButton("RESET");
		btnResetPanel.add(resetBtn);
		
		tablePanel = new JPanel();
		tablePanel.setPreferredSize(new Dimension((int)(centerContentPanel.getPreferredSize().width * 0.9), (int)(centerContentPanel.getPreferredSize().height * 0.5)));
		centerContentPanel.add(tablePanel);
		
		String[] columns = {"S.No", "Name", "Address", "City", "Ref. No", "Rec. Acc.", "Amount", "Commision", "Invoice amt", "Date", "State", "Reversed", "id"};
		model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new Dimension((int)(tablePanel.getPreferredSize().width*0.99), (int)(tablePanel.getPreferredSize().height*0.85)));
		tablePanel.add(scroll, BorderLayout.CENTER);
		
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension((int)(centerContentPanel.getPreferredSize().width * 0.9), (int)(centerContentPanel.getPreferredSize().height * 0.20)));
		centerContentPanel.add(infoPanel);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		empIdLblinfo = new JLabel("");
		
		IDpanel = new JPanel();
		IDpanel.setPreferredSize(new Dimension((int)(infoPanel.getPreferredSize().width * 0.8), (int)(infoPanel.getPreferredSize().height * 0.25)));
		IDpanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		IDpanel.add(empIdLblinfo);
		infoPanel.add(IDpanel);
		
		empNameLblinfo = new JLabel("");
		
		namePanel = new JPanel();
		namePanel.setPreferredSize(new Dimension((int)(infoPanel.getPreferredSize().width * 0.4), (int)(infoPanel.getPreferredSize().height * 0.25)));
		namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		namePanel.add(empNameLblinfo);
		infoPanel.add(namePanel);
		
		empLnameLblinfo = new JLabel("");
		
		fnamePanel = new JPanel();
		fnamePanel.setPreferredSize(new Dimension((int)(infoPanel.getPreferredSize().width * 0.4), (int)(infoPanel.getPreferredSize().height * 0.25)));
		fnamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		fnamePanel.add(empLnameLblinfo);
		infoPanel.add(fnamePanel);
		
		empUnameinfo = new JLabel("");
		
		unamePanel = new JPanel();
		unamePanel.setPreferredSize(new Dimension((int)(infoPanel.getPreferredSize().width * 0.4), (int)(infoPanel.getPreferredSize().height * 0.25)));
		unamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		unamePanel.add(empUnameinfo);
		infoPanel.add(unamePanel);
		
		empPassLblinfo = new JLabel("");
		
		passPanel = new JPanel();
		passPanel.setPreferredSize(new Dimension((int)(infoPanel.getPreferredSize().width * 0.4), (int)(infoPanel.getPreferredSize().height * 0.25)));
		passPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		passPanel.add(empPassLblinfo);
		infoPanel.add(passPanel);
		
		
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
	public JTable getInstanceOfTable() 
	{
		return table;
	}
	public void addMouseListenerOnTable(MouseListener listener) 
	{
		table.addMouseListener(listener);
	}
	public JPanel getInstanceOfInfoPanel() 
	{
		return infoPanel;
	}
	
	public LocalDate getDateTxt() {
		 String date = dateTxt.getText();
		    if (dateTxt.getText().isEmpty()) { 
		        return null;
		    }
		    try {
		        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		    } catch (DateTimeParseException e) {
		        try {
		            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		        } catch (DateTimeParseException ex) {
		            return null;
		        }
		    }
		}
	public void setEmpIdInf(int Id) 
	{
		String id =String.valueOf(Id); 
		empIdLblinfo.setText("ID: "+id);
	}
	public void setEmpFnameInf(String fname) 
	{
		 
		empNameLblinfo.setText("First Name: "+fname);
	}
	public void setEmpLnameInf(String lname) 
	{
		 
		empLnameLblinfo.setText("Last Name: "+lname);
	}
	public void setEmpUnameInf(String uname) 
	{
		 
		empUnameinfo.setText("Username: "+uname);
	}
	public void setEmpPassInf(String pass) 
	{
		 
		empPassLblinfo.setText("Password: "+pass);
	}
	
	public void setDateTxt(String dateText) {
		dateTxt.setText(dateText);
	}
	public int getIdTxt() {
		if(idTxt.getText().isEmpty()) 
		{
			return 0;
		}else 
		{
		return Integer.parseInt(idTxt.getText());
		}
	}
	public void setIdTxt(String idText) {
		idTxt.setText(idText);
	}
	public int getSnoTxt() {
		if(snoTxt.getText().isEmpty()) 
		{
			return 0;
		}else
			{
			return Integer.parseInt(snoTxt.getText());
			}
	}
	public void setSnoTxt(String snoText) {
		snoTxt.setText(snoText);;
	}
	
	public JPanel getInputFieldPanelInstance() 
	{
		return inputFieldPanel;
	} 
	public JPanel getInfoPanelInstance() 
	{
		return infoPanel;
	} 
	public void addActionListenerOnReset(ActionListener listener) 
	{
		resetBtn.addActionListener(listener);
	}
	public ButtonGroup getButtonGroup() {
		return btnGroup;
	}
	public boolean getForwardCheck() 
	{
		if(forwRadioBtn.isSelected()) 
		{
			return true;
		}
		else return false;
	}
	public boolean getCancelCheck() 
	{
		if(cancRadioBtn.isSelected()) 
		{
			return true;
		}
		else return false;
	}
	public boolean getRevCheck() 
	{
		if(revRadioBtn.isSelected())
		{
			return true;
		}
		else return false;
	}
	public boolean getAllCheck() 
	{
		if(allRadioBtn.isSelected())
		{
			return true;
		}
		else return false;
	}
	public void addFocusListenerOnTxtFields(FocusListener listener) 
	{
		dateTxt.addFocusListener(listener);
		snoTxt.addFocusListener(listener);
		idTxt.addFocusListener(listener);
	}
	public void addItemListenerOnBtn(ItemListener listener) 
	{
		allRadioBtn.addItemListener(listener);
		revRadioBtn.addItemListener(listener);
		forwRadioBtn.addItemListener(listener);
		cancRadioBtn.addItemListener(listener);
	}

	

	
}
