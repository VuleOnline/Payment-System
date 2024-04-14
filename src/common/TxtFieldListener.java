package common;

import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

public interface TxtFieldListener {
	
	default void addFocusListeneOnTxtFields(FocusListener listener) 
	{
		ArrayList<JTextField> compList =  DenomManipulationMethods.fieldsToList(getDenomPanelInstance());
		for(JTextField list : compList) 
		{
			list.addFocusListener(listener);
		}
	}
	 JPanel getDenomPanelInstance();
}
