package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import common.BackButton;
import common.MyDocFilter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;



public class EmployeeListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField idTxt;
	private JTextField fnameTxt;
	private JTextField lnameTxt;
	private JTextField unameTxt;
	private JTextField passTxt;
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private JCheckBox adminCheck;
	private JLabel byIdLbl;
	private JButton addBtn;
	private JButton searchBtn;
	private JButton saveBtn;
	private JButton deleteBtn;
	private JButton resetBtn;
	private JLabel passLbl;
	private JLabel usernameLbl;
	private JPanel panel;
	private JLabel lnameLbl;
	private JLabel fnameLbl;
	private JLabel userIdLbl;
	private BackButton backBtn;
	
	private EmployeeListPanel() {
		initialize();
	}
	private static Map<String, EmployeeListPanel> cache = new ConcurrentHashMap<>();
	 public static EmployeeListPanel getEmpListPanel(String sessionId) 
	    {
		 if(!cache.containsKey(sessionId)) 
			{
			 EmployeeListPanel empListPanel  = new EmployeeListPanel();
				cache.put(sessionId, empListPanel);
			}
			return cache.get(sessionId);
	    }
	
	public void initialize() {
		setLayout(null);
		this.setPreferredSize(new Dimension(768, 524));
		
		backBtn = new BackButton();
		backBtn.setBounds(0, 0, 25, 25);
		add(backBtn);
		
		String[] columns = {"ID", "First Name", "Last Name", "Username", "Password", "Admin"};
		model = new DefaultTableModel(columns,0); 
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 256, 718, 218);
		add(scrollPane);
		
		panel = new JPanel();
		panel.setBounds(226, 10, 234, 236);
		add(panel);
		panel.setLayout(null);
		
		idTxt = new JTextField();
		idTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		idTxt.setBounds(174, 20, 50, 19);
		((AbstractDocument)idTxt.getDocument()).setDocumentFilter(new MyDocFilter("\\d{0,9}"));
		panel.add(idTxt);
		idTxt.setColumns(10);
		
		fnameTxt = new JTextField();
		fnameTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		fnameTxt.setBounds(94, 55, 130, 19);
		panel.add(fnameTxt);
		fnameTxt.setColumns(10);
		
		lnameTxt = new JTextField();
		lnameTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		lnameTxt.setBounds(94, 95, 130, 19);
		panel.add(lnameTxt);
		lnameTxt.setColumns(10);
		
		unameTxt = new JTextField();
		unameTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		unameTxt.setBounds(94, 132, 130, 19);
		panel.add(unameTxt);
		unameTxt.setColumns(10);
		
		passTxt = new JTextField();
		passTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		passTxt.setBounds(94, 171, 130, 19);
		panel.add(passTxt);
		passTxt.setColumns(10);
		
		addBtn = new JButton("ADD");
		addBtn.setBounds(139, 205, 85, 21);
		panel.add(addBtn);
		
		adminCheck = new JCheckBox("Admin");
		adminCheck.setBounds(6, 205, 55, 21);
		panel.add(adminCheck);
		
		passLbl = new JLabel("Password");
		passLbl.setBounds(4, 174, 80, 13);
		panel.add(passLbl);
		
		usernameLbl = new JLabel("Username");
		usernameLbl.setBounds(4, 135, 80, 13);
		panel.add(usernameLbl);
		
		lnameLbl = new JLabel("Lasta Name");
		lnameLbl.setBounds(4, 98, 80, 13);
		panel.add(lnameLbl);
		
			
		fnameLbl = new JLabel("First Name");
		fnameLbl.setBounds(4, 58, 80, 13);
		panel.add(fnameLbl);
			
		userIdLbl = new JLabel("User ID");
		userIdLbl.setBounds(4, 23, 80, 13);
		panel.add(userIdLbl);
		
		searchBtn = new JButton("SEARCH");
		searchBtn.setBounds(591, 61, 85, 21);
		add(searchBtn);
		
		saveBtn = new JButton("SAVE");
		saveBtn.setBounds(591, 123, 85, 21);
		add(saveBtn);
		
		deleteBtn = new JButton("DELETE");
		deleteBtn.setBounds(591, 155, 85, 21);
		add(deleteBtn);
		
		byIdLbl = new JLabel("(by ID)");
		byIdLbl.setFont(new Font("Tahoma", Font.PLAIN, 8));
		byIdLbl.setBounds(686, 66, 45, 13);
		add(byIdLbl);
		
		resetBtn = new JButton("RESET");
		resetBtn.setBounds(591, 92, 85, 21);
		add(resetBtn);
		
		

	}

	public void addActionListenerOnAdd(ActionListener llistener) 
	{
		addBtn.addActionListener(llistener);
	}
	public void addActionListenerOnDelete(ActionListener listener) 
	{
		deleteBtn.addActionListener(listener);
	}
	public void addActionListenerOnSearch(ActionListener listener) 
	{
		searchBtn.addActionListener(listener);
	}
	public void addActionListenerOnReset(ActionListener listener) 
	{
		resetBtn.addActionListener(listener);
	}
	public void addAdtionListenerOnSave(ActionListener listener) 
	{
		saveBtn.addActionListener(listener);
	}
	public void addListSelectionOnTable(ListSelectionListener listener) 
	{
		table.getSelectionModel().addListSelectionListener(listener);
	}
	public void clearFields() 
	{
		idTxt.setText(""); fnameTxt.setText(""); lnameTxt.setText(""); unameTxt.setText("");
		passTxt.setText(""); adminCheck.setSelected(false);
	}
	public void showEmpInTable(int id, String fname, String lname, String uname, String pass, boolean isAdmin)
	{
		model.addRow(new Object[] {id, fname, lname, uname, pass, isAdmin});
	}
	public void clearTable() 
	{
		model.setRowCount(0);
	}

	public int getIdTxt() {
		try {
			String idText = idTxt.getText().trim();
			if(idText.isEmpty()) 
			{
				return 0;
			}
	        return Integer.parseInt(idTxt.getText());
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
	public void setIdTxt(int id) {
		idTxt.setText(String.valueOf(id));
	}
	public void setIdEdit() 
	{
		if(idTxt.isEditable()) 
		{
			return;
		}else idTxt.setEditable(true);
	}
	public void setIdDisabled() 
	{
		if(idTxt.isEditable()) {
		idTxt.setEditable(false);
		}
	}

	public String getFnameTxt() {
		return fnameTxt.getText();
	}
	public void setFnameTxt(String fname) {
		fnameTxt.setText(fname);
	}

	public String getLnameTxt() {
		return lnameTxt.getText();
	}
	public void setLnameTxt(String lname) {
		lnameTxt.setText(lname);
	}
	
	public String getUnameTxt() 
	{
		return unameTxt.getText();
	}
	public void setUname(String uname) 
	{
		unameTxt.setText(uname);
	}

	public String getPassTxt() {
		return passTxt.getText();
	}
	public void setPassTxt(String pass) {
		passTxt.setText(pass);
	}

	public boolean getAdminCheck() {
		return adminCheck.isSelected();
	}
	public void setAdminCheck(boolean admin) {
		adminCheck.setSelected(admin);
	}
	public JTable getInstanceOfTable() 
	{
		return table;
	}
	
}
