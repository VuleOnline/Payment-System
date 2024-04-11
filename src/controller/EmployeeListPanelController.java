package controller;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.UserModel;
import service.DBEmpManipulationServ;
import view.EmployeeListPanel;


public class EmployeeListPanelController {
	
	EmployeeListPanel empListPanel;
	
	private EmployeeListPanelController(EmployeeListPanel empListPanel) 
	{
		this.empListPanel = empListPanel;
		initialize();
	
	}
	private static Map<String, EmployeeListPanelController> cache = new HashMap<>();
	 public static EmployeeListPanelController getEmpListPanelContr(String sessionId) 
	    {
		 if(!cache.containsKey(sessionId)) 
			{
			 EmployeeListPanelController empListPanelContr  = new EmployeeListPanelController(EmployeeListPanel.getEmpListPanel(sessionId));
				cache.put(sessionId, empListPanelContr);
			}
			return cache.get(sessionId);
	    }
	public void initialize() 
	{
		this.empListPanel.addActionListenerOnAdd(new AddActionListenerOnAdd());
		this.empListPanel.addActionListenerOnDelete(new AddActionListenerOnDelete());
		this.empListPanel.addActionListenerOnSearch(new AddActionListenerOnSearch());
		this.empListPanel.addActionListenerOnReset(new AddActionListenerOnReset());
		this.empListPanel.addAdtionListenerOnSave(new AddAdtionListenerOnSave());
		this.empListPanel.addListSelectionOnTable(new AddListSelectionOnTable());
		showAllEmps();
	}
	class  AddActionListenerOnAdd implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			int id = empListPanel.getIdTxt();
			if(id==0) {
			String fname = empListPanel.getFnameTxt();
			String lname = empListPanel.getLnameTxt();
			String uname = empListPanel.getUnameTxt();
			String pass = empListPanel.getPassTxt();
			boolean isAdmin = empListPanel.getAdminCheck();
			if(fname.isEmpty() || lname.isEmpty() || uname.isEmpty() || pass.isEmpty()) 
			{
				JOptionPane.showMessageDialog(null, "Fill required fileds.");
				return;
			}
			
			UserModel newEmp  = new UserModel(fname, lname, uname, pass, isAdmin);
			if(DBEmpManipulationServ.insertUser(newEmp)) 
			{
				empListPanel.clearFields();
				empListPanel.clearTable();
				showAllEmps();
			}
			}else 
			{
				JOptionPane.showMessageDialog(null, "ID polje se dodaje sistemski.");
				return;
			}
		
		}
		
	}
	class AddActionListenerOnDelete implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			int id = empListPanel.getIdTxt();
			if(id==0) 
			{
				empListPanel.clearFields();
				return;
			}
			if(DBEmpManipulationServ.deleteUser(id)) 
			{
				JOptionPane.showMessageDialog(null, "Employee deleted.");
				empListPanel.clearTable();
				empListPanel.clearFields();
				empListPanel.setIdEdit();
				showAllEmps();
			}
			else 
			{
				empListPanel.clearFields();
				JOptionPane.showMessageDialog(null, "Deletion failed.");
			}
			
		}
		
	}
	class AddActionListenerOnSearch implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			int id = empListPanel.getIdTxt();
			if(id==0) 
			{
				empListPanel.clearFields();
				return;
			}
			UserModel emp = DBEmpManipulationServ.selectUserByID(id);
			if(emp != null) 
			{
			empListPanel.setIdTxt(emp.getId());
			empListPanel.setIdDisabled();
			empListPanel.setFnameTxt(emp.getFname());
			empListPanel.setLnameTxt(emp.getLname());
			empListPanel.setUname(emp.getUname());
			empListPanel.setPassTxt(emp.getPassword());
			empListPanel.setAdminCheck(emp.isAdmin());
			}else 
			{
				empListPanel.clearFields();
				JOptionPane.showMessageDialog(null, "User not found.");
			}
			
		}
		
	}
	class AddActionListenerOnReset implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			empListPanel.setIdEdit();
			empListPanel.clearFields();
			empListPanel.getInstanceOfTable().clearSelection();
		}
		
	}
	class  AddAdtionListenerOnSave implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			int id = empListPanel.getIdTxt();
			String fname = empListPanel.getFnameTxt();
			String lname = empListPanel.getLnameTxt();
			String uname = empListPanel.getUnameTxt();
			String pass  = empListPanel.getPassTxt();
			boolean isAdmin = empListPanel.getAdminCheck();
			if(id==0 || fname.isEmpty() || lname.isEmpty() || uname.isEmpty() || pass.isEmpty()) 
			{
				return;
			}
			UserModel newEmpData = new UserModel(id, fname, lname, uname, pass, isAdmin);
			if(DBEmpManipulationServ.updateUser(newEmpData)) 
			{
				JOptionPane.showMessageDialog(null, "Updated successfully");
				empListPanel.setIdEdit();
				empListPanel.clearFields();
				empListPanel.clearTable();
				showAllEmps();
			}else 
			{
				JOptionPane.showMessageDialog(null, "Updated was not successful.");
				empListPanel.setIdEdit();
				empListPanel.clearFields();
				empListPanel.clearTable();
				showAllEmps();
			}
		}
		
	}
	class AddListSelectionOnTable implements ListSelectionListener
	{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting() && empListPanel.getInstanceOfTable().getSelectedRow() != -1) {
			int selectedRow = empListPanel.getInstanceOfTable().getSelectedRow();
			Object id = empListPanel.getInstanceOfTable().getValueAt(selectedRow, 0);
			UserModel emp = DBEmpManipulationServ.selectUserByID((int)id);
			empListPanel.setIdTxt(emp.getId());
			empListPanel.setFnameTxt(emp.getFname());
			empListPanel.setLnameTxt(emp.getLname());
			empListPanel.setUname(emp.getUname());
			empListPanel.setPassTxt(emp.getPassword());
			empListPanel.setAdminCheck(emp.isAdmin());
			
			empListPanel.setIdDisabled();
			}
			
		}
		
	}
	public void showAllEmps() 
	{
		List<UserModel> emps = DBEmpManipulationServ.selectUsers();
		for(UserModel emp : emps) 
		{
			empListPanel.showEmpInTable(emp.getId(), emp.getFname(), emp.getLname(), emp.getUname(), emp.getPassword(), emp.isAdmin());
		}
	}

}
