package view;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginPanelController;


public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
	private static JPanel cardPanel;
	private LoginPanel loginPanel;
	private static Frame instance;
	private List<String> panelHistory;
	
	
    
	 public Frame() {
		 	instance=this;
	        initialize();
	       
	    }

	    public static Frame getInstance() {
	       return instance;
	    }
	    public static JPanel getCardPanel() 
	    {
	    	return cardPanel;
	    }


	
	public void initialize() {
		setLayout(new BorderLayout());
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		loginPanel = LoginPanel.getInstance();
		cardPanel.add(loginPanel, "loginPanel");
		
		
		LoginPanelController.getInstance();
		
		 
		setTitle("Cash Register");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 524);
		cardPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(cardPanel, BorderLayout.CENTER);
		
		
		panelHistory = new ArrayList<>();
		setVisible(true);
		
		
		

	
		
	}
	
	public synchronized void showCard(String cardName) {
		cardLayout.show(cardPanel, cardName);
	    panelHistory.add(cardName);
		
		
	}



	public synchronized void showPreviousCard() {
	    if (panelHistory.size() > 1) {
	        int last = panelHistory.size() - 2;
	        String previousPanel = panelHistory.get(last);

	        while (last > 0 && (previousPanel.equals("rcvdMoneyPanel") ||
	                            previousPanel.equals("revDenomPanel") ||
	                            previousPanel.equals("exchangePanel"))) {
	            panelHistory.remove(last);
	            last--;
	            previousPanel = panelHistory.get(last);
	        }

	        panelHistory.remove(panelHistory.size() - 1);

	        previousPanel = panelHistory.get(panelHistory.size() - 1);
	        cardLayout.show(cardPanel, previousPanel);
	        cardPanel.revalidate();
	        cardPanel.repaint();
	    }
	
	}
	
	
}
