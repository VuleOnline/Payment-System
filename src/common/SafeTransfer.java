package common;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DBMoneyManipulationDao;
import model.SafeTransfers;

public class SafeTransfer {
	
	public static void SafeTransferMethod (JPanel panel, int empId, boolean isWithdrawal) 
	{
		HashMap<String, String> list = DenomManipulationMethods.fieldMap(panel);
		HashMap<String, String> labelList =DenomManipulationMethods.labelValue();
		for(Map.Entry<String, String> entry : list.entrySet()) 
		{
			String filedName = entry.getKey();
			JTextField field = DenomManipulationMethods.getJTextField(panel, filedName);
			String value = list.get(field.getName());
			String label = labelList.get(value);
			double fieldTxt = field.getText().isEmpty() ? 0.0 : Double.parseDouble(field.getText());
			int denom = Integer.parseInt(label);
			int quantity = (int) fieldTxt;
			SafeTransfers st = new SafeTransfers(denom, quantity, empId, LocalDate.now(), isWithdrawal);
			DBMoneyManipulationDao.insertSafeTransfer(st);
		}
	}

}
