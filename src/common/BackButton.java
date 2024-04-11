package common;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import view.Frame;

public class BackButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	 private static final ImageIcon icon = new ImageIcon("backBtn25.png");
	
	public BackButton() 
	{
		setIcon(icon);
		setBorderPainted(false);
		setContentAreaFilled(false);
		addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				 Frame.getInstance().showPreviousCard();
				
			}
			
		});
		}
		@Override
		protected void paintComponent(Graphics g) 
		{
			g.setColor(getBackground());
			g.fillOval(0, 0, getSize().width-1, getSize().height-1);
			super.paintComponent(g);
		}
	}


