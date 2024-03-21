package common;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


import view.Frame;

public class BackLblMouseListener {
	private Frame frame;
	private static final BackLblMouseListener bckLbl = new BackLblMouseListener();
	
	private BackLblMouseListener() {
    }
	public static BackLblMouseListener getInstance() 
	{
		return bckLbl;
		
	}
	public void setFrame(Frame frame) {
        this.frame =  frame;
    }
	
	public JLabel backLblMouseListener() 
	{

		ImageIcon imageOrig = new ImageIcon("backBtn.png"); 
		Image image = imageOrig.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(image); 
		JLabel imageLabel = new JLabel(imageIcon);
		
		imageLabel.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {

                   Frame.getInstance().showPreviousCard();
	            
	        }
	    });
		imageLabel.setSize(20,20);
		return imageLabel;
	}
	
	

}
