package view;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JTextField;


import javax.swing.JPasswordField;
import javax.swing.JButton;


public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static LoginPanel instance;
	
	private JTextField unameTxt;
	private JPasswordField passTxt;
	private JLabel unameLabel;
	private JLabel passLabel;
	private JButton logBtn;
	
	private LoginPanel() 
	{
		initialize();
	}
	
	 public static LoginPanel getInstance() 
	    {
		 if(instance == null) 
			{
			 instance = new LoginPanel();
			}
			return instance;
	    }

	
	public void initialize() {
		setLayout(null);
		this.setPreferredSize(new Dimension(768, 524));
		unameLabel = new JLabel("Username:");
		unameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		unameLabel.setBounds(193, 127, 70, 23);
		add(unameLabel);
		
		passLabel = new JLabel("Password:");
		passLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passLabel.setBounds(193, 213, 70, 23);
		add(passLabel);
		
		unameTxt = new JTextField();
		unameTxt.setColumns(10);
		unameTxt.setBounds(306, 129, 192, 23);
		add(unameTxt);
		
		passTxt = new JPasswordField();
		passTxt.setBounds(306, 215, 192, 23);
		add(passTxt);
		
		logBtn = new JButton("Login");
		logBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logBtn.setBounds(329, 348, 111, 25);
		add(logBtn);


	}
	
	public void addActionListenerOnLogin(ActionListener listener) 
	{
		
		 logBtn.addActionListener(listener);
	
		
	}
	
	public String getUName() {
       return unameTxt.getText();}
	public void setUName(String uname) {
        unameTxt.setText(uname);}
	
	public String getPass() {
      char[] passChar = passTxt.getPassword();
      String pass = String.valueOf(passChar);
	return pass; }
	public void setPass(String pass) {
       passTxt.setText(pass);}
	
	
}
