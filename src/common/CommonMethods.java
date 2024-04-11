package common;

import java.awt.Component;
import java.awt.Container;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.DenomEntryModel;
import service.DBMoneyManipulationServ;


public class CommonMethods {
 
	public static ArrayList<JTextField> fieldsToList(Container field) 
	{
		ArrayList<JTextField> list = new ArrayList<>();
		Component[] component = field.getComponents();
		for(Component comp : component) 
		{
			if(comp instanceof JTextField) 
			{
				list.add((JTextField) comp);
			}else if(comp instanceof Container) 
			{
				fieldsToList((Container) comp);
			}
		}
		
		return list;
	}
	public static HashMap<String, String> fieldMap(Container container)
	{
		HashMap<String, String> mapList = new HashMap<>();
		
		for(Component comp :container.getComponents())
		{
			if( comp instanceof JTextField) 
			{
				JTextField text = (JTextField)comp;
				JLabel label = getJLabel(container, text);
				if (text != null && label != null) {
					mapList.put(text.getName(), label.getName());
				}else {
		                System.out.println("Nije pronađena odgovarajuća Labela za JTextField: " + text.getName());
				}
			}
		}
		return mapList;
	}
	public static JLabel getJLabel(Container container, JTextField text) 
	{
		 for (Component component : container.getComponents()) {
			 
	            if (component instanceof JLabel && component.getName() != null && component.getName().substring(0, 2).equals(text.getName().substring(0, 2))) 
	            {
	            	return (JLabel) component;
	            }
	        }
	        return null;
	}
	public static JTextField getJTextField(Container container, String text) 
	{
		 for (Component component : container.getComponents()) {
			 
	            if (component instanceof JTextField && component.getName() != null && component.getName().equals(text)) 
	            {
	            	return (JTextField) component;
	            }
	        }
	        return null;
	}
	public static HashMap<String, String> labelValue()
	{
		HashMap<String, String> labels = new HashMap<>();
		labels.put("F1fiveThLbl", "5000");
		labels.put("F2twoThLbl", "2000");
		labels.put("F3oneThLbl", "1000");
		labels.put("F4fiveHLbl", "500");
		labels.put("F5twoHLbl", "200");
		labels.put("F6oneHLbl", "100");
		labels.put("F7fifthLbl", "50");
		labels.put("F8twenLbl", "20");
		labels.put("F9tenLbl", "10");
		labels.put("FTfiveLbl", "5");
		labels.put("FEtwoLbl", "2");
		labels.put("FWoneLbl", "1");

		return labels;
	} 
	public static void subFromDenom(JPanel panel, int empId) 
	{
		HashMap<String, String> list = CommonMethods.fieldMap(panel);
		HashMap<String, String> labelList =CommonMethods.labelValue();
		for(Map.Entry<String, String> entry : list.entrySet()) 
		{
			String filedName = entry.getKey();
			JTextField field = CommonMethods.getJTextField(panel, filedName);
			String value = list.get(field.getName());
			String label = labelList.get(value);
			double fieldTxt = field.getText().isEmpty() ? 0.0 : Double.parseDouble(field.getText());
			int denom = Integer.parseInt(label);
			int quantity = (int) fieldTxt;
			DenomEntryModel denomEntry = new DenomEntryModel(denom, quantity, empId, LocalDate.now());
			DBMoneyManipulationServ.subFromDenom(denomEntry);
		}
	}
	public static void addToDenom(JPanel panel, int empId) 
	{
		HashMap<String, String> list = CommonMethods.fieldMap(panel);
		HashMap<String, String> labelList =CommonMethods.labelValue();
		for(Map.Entry<String, String> entry : list.entrySet()) 
		{
			String filedName = entry.getKey();
			JTextField field = CommonMethods.getJTextField(panel, filedName);
			String value = list.get(field.getName());
			String label = labelList.get(value);
			double fieldTxt = field.getText().isEmpty() ? 0.0 : Double.parseDouble(field.getText());
			int denom = Integer.parseInt(label);
			int quantity = (int) fieldTxt;
			DenomEntryModel denomEntry = new DenomEntryModel(denom, quantity,empId, LocalDate.now());
			DBMoneyManipulationServ.insertDenomEntry(denomEntry);
		}
	
	}
	
	public static double addCalculator(JPanel panel) 
	{
		double result = 0.0;
		HashMap<String, String> list = fieldMap(panel);
		HashMap<String, String> labelList = labelValue();
		for(Map.Entry<String, String> entry : list.entrySet()) 
		{
			String filedName = entry.getKey();
			JTextField field = getJTextField(panel, filedName);
			String value = list.get(field.getName());
			String label = labelList.get(value);
			double fieldTxt = field.getText().isEmpty() ? 0.0 : Double.parseDouble(field.getText());
			try {
				double labelTxt = Double.parseDouble(label);
				double res = labelTxt * fieldTxt;
				result=result+res;
				}catch(Exception ex) 
				{
					System.out.println("Exception occurred. Field: " + filedName + ", Label: " + label + ", Field Text: " + fieldTxt);
				}
		}
		return result;
	}
	public static double subCalculator(JPanel panel, int empId) 
	{
		double result = 0.0;
		HashMap<String, String> list = fieldMap(panel);
		HashMap<String, String> labelList = labelValue();
		
		for(Map.Entry<String, String> entry : list.entrySet()) 
		{
			String filedName = entry.getKey();
			JTextField field = getJTextField(panel, filedName);
			String value = list.get(field.getName());
			String label = labelList.get(value);
			double fieldTxt = field.getText().isEmpty() ? 0.0 : Double.parseDouble(field.getText());
			try {
				double labelTxt = Double.parseDouble(label);
				double res = labelTxt * fieldTxt;
				double sum = DBMoneyManipulationServ.getDenomSum(labelTxt, empId, LocalDate.now());
				if(sum < res) 
				{
					JOptionPane.showMessageDialog(null, "You dont have enough "+labelTxt+"'s in your cash register");
				}
				else result=result+res;
				}catch(Exception ex) 
				{
					System.out.println("Exception occurred. Field: " + filedName + ", Label: " + label + ", Field Text: " + fieldTxt);
					ex.printStackTrace();
				}
		}
		return result;
	}
	
	
	
	


}
