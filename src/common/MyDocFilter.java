package common;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class MyDocFilter extends DocumentFilter{
	
	String regex;
	
	public MyDocFilter(String regex) 
	{
		this.regex = regex;
	}
	
	@Override
	public void insertString(FilterBypass fb, int offset, String text, AttributeSet atts)
	throws BadLocationException{
		super.insertString(fb, offset, text, atts);
	}
	
	
	 @Override
	    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
	    	
	    	String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
	        StringBuilder newText = new StringBuilder(currentText);

	        newText.replace(offset, offset + length, text);

	        if (isValidInput(newText.toString())) {
	            super.replace(fb, 0, fb.getDocument().getLength(), newText.toString(), attrs);
	        }else { 
	        	Toolkit.getDefaultToolkit().beep();
	        	}
	        }

	    private boolean isValidInput(String input) {
	        return input.matches(regex);
	    }
	}



