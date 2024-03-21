package view;

import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import common.BackLblMouseListener;
import common.SessionManager;
import service.DBChangeManipulationServ;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ChangeRegPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static ChangeRegPanel instance;
	private JTable table;
	private JComboBox<String> comboBox;
	private JLabel sNoListLbl;
	private JLabel rcvdLbl;
	private JTextField rcvdTxt;
	private JTextField totalTxt;
	private JTextField commTxt;
	private JTextField changeTxt;
	private JLabel totalLbl;
	private JLabel lblCommision;
	private JLabel changeLbl;
	private DefaultTableModel model;
	private JScrollPane scroll;

	
	private ChangeRegPanel() {
		initialize();
	}
	
	  public static ChangeRegPanel getInstance() {
	    	if (instance == null) {
	            instance = new ChangeRegPanel();
	    	}
	        return instance;
	    }
	
	public void initialize() 
	{
		this.setPreferredSize(new Dimension(768, 524));
		setLayout(null);
		
		BackLblMouseListener bckLbl = BackLblMouseListener.getInstance();
		JLabel backLabel = bckLbl.backLblMouseListener();
		add(backLabel);
		
		sNoListLbl = new JLabel("Choose Order(s)");
		sNoListLbl.setHorizontalAlignment(SwingConstants.CENTER);
		sNoListLbl.setBounds(258, 26, 78, 22);
		add(sNoListLbl);
		
		String[] options =  DBChangeManipulationServ.getSnos
		(SessionManager.getEmpIdBySession(SessionManager.getCurrSess()), LocalDate.now()).toArray(new String[0]);
		comboBox = new JComboBox<>(options);
		comboBox.setBounds(358, 27, 107, 21);
		comboBox.addAncestorListener(new AncestorListener() {
		    @Override
		    public void ancestorAdded(AncestorEvent event) {
		        comboBox.setSelectedItem(null);
		    }

		    @Override
		    public void ancestorRemoved(AncestorEvent event) {}

		    @Override
		    public void ancestorMoved(AncestorEvent event) {}
		});
		add(comboBox);
		
		String[] columns = {"S.No", "Name", "Address", "Ref. No", "Rec. Acc.", "Amount", "Commision", "Invoice amt", "Date"};
		model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(10, 68, 723, 163);
		add(scroll);
		
		rcvdLbl = new JLabel("Recived Money");
		rcvdLbl.setHorizontalAlignment(SwingConstants.CENTER);
		rcvdLbl.setBounds(539, 264, 78, 22);
		add(rcvdLbl);
		
		rcvdTxt = new JTextField();
		rcvdTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		rcvdTxt.setBounds(627, 265, 107, 21);
		rcvdTxt.setEditable(false);
		add(rcvdTxt);
		rcvdTxt.setColumns(10);
		
		totalLbl = new JLabel("Total:");
		totalLbl.setHorizontalAlignment(SwingConstants.CENTER);
		totalLbl.setBounds(539, 307, 78, 22);
		add(totalLbl);
		
		totalTxt = new JTextField();
		totalTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		totalTxt.setColumns(10);
		totalTxt.setEditable(false);
		totalTxt.setBounds(626, 308, 107, 21);
		add(totalTxt);
		
		lblCommision = new JLabel("Commision");
		lblCommision.setHorizontalAlignment(SwingConstants.CENTER);
		lblCommision.setBounds(539, 352, 78, 22);
		add(lblCommision);
		
		changeLbl = new JLabel("Change:");
		changeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		changeLbl.setBounds(539, 399, 78, 22);
		add(changeLbl);
		
		commTxt = new JTextField();
		commTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		commTxt.setColumns(10);
		commTxt.setEditable(false);
		commTxt.setBounds(627, 353, 107, 21);
		add(commTxt);
		
		changeTxt = new JTextField();
		changeTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		changeTxt.setColumns(10);
		changeTxt.setEditable(false);
		changeTxt.setBounds(627, 400, 107, 21);
		add(changeTxt);
		
	}
	public void addOrderInTable(int sNo, String fullName, String address, String refNo, String recAcc,
			double amount, double comm, double invoiceAmt, LocalDateTime orderDate) 
	{
		model.addRow(new Object[] {sNo, fullName, address,refNo, recAcc, amount, comm, invoiceAmt, orderDate});
	}
	public void clearTable() 
	{
		model.setRowCount(0);
	}
	public void setRcvdTxt(double rcvd) 
	{
		rcvdTxt.setText(String.valueOf(rcvd));
	}
	public void setCommTxt(double comm) 
	{
		commTxt.setText(String.valueOf(comm));
	}
	public void setTotalTxt(double total) 
	{
		totalTxt.setText(String.valueOf(total));
	}
	public void setChangeTxt(double change) 
	{
		changeTxt.setText(String.valueOf(change));
	}
	
	public String getComboBoxValue() {
		return (String) comboBox.getSelectedItem();
	}
	public void addItemListenerMouseListenerOnComboBox(ItemListener item) 
	{
		comboBox.addItemListener(item);
	}
}
