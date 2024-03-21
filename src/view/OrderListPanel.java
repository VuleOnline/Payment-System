package view;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import common.BackLblMouseListener;

public class OrderListPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Frame frame;
	
	public OrderListPanel() {
			this.frame = Frame.getInstance();
			initialize();
		}

	private void initialize() {
		setLayout(null);
		this.setPreferredSize(new Dimension(768, 524));
		BackLblMouseListener bckLbl = BackLblMouseListener.getInstance();
		bckLbl.setFrame(frame);
		JLabel backLabel = bckLbl.backLblMouseListener();
		add(backLabel);
		
	}
	

}
